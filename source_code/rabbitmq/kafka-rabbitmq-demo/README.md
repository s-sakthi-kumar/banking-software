# Event-Driven Banking Demo

A two-service Spring Boot demo that shows **Kafka** and **RabbitMQ** side-by-side
as messaging transports for banking transaction events.

```
kafka-rabbitmq-demo/
 ├── transaction-service/   ← Spring Boot 3 · Kafka + RabbitMQ Producer  (port 8082)
 ├── account-service/       ← Spring Boot 3 · Kafka + RabbitMQ Consumer  (port 8081)
 ├── docker-compose.yml     ← Kafka + Zookeeper + RabbitMQ
 └── README.md
```

---

## Concepts Demonstrated

| Concept | Where |
|---|---|
| **Synchronous REST** | `POST /transaction` → immediate HTTP response |
| **Asynchronous Kafka** | `KafkaTemplate.send()` + `@KafkaListener` |
| **Asynchronous RabbitMQ** | `RabbitTemplate.convertAndSend()` + `@RabbitListener` |
| **Kafka partitions** | Partitioned by `userId` — all events for a user go to the same partition (ordering) |
| **Consumer groups** | `groupId = "account-service"` — scale out consumers without duplicate processing |
| **Topic exchange** | RabbitMQ `transaction-exchange` routes via `transaction.key` |
| **Notification pattern** | Consumer logs every received event |
| **Event-Carried State Transfer** | Full `Transaction` payload embedded in event — consumer needs no callback |
| **Idempotency** | `processedTxIds` set in `AccountService` — duplicate events are silently skipped |
| **Saga basics** | Extend by chaining `account-service → payment-service → notification-service` with compensating actions |

---

## Prerequisites

- Java 17+
- Maven 3.8+
- Docker or Podman with Compose

---

## Step 1 — Start Infrastructure

```bash
docker-compose up -d
# or if using Podman:
# podman-compose up -d
```

Wait ~15 seconds for Kafka and RabbitMQ to fully initialise.

| Service | Address |
|---|---|
| Kafka broker | `localhost:9092` |
| RabbitMQ AMQP | `localhost:5672` |
| RabbitMQ Management UI | http://localhost:15672  (`guest` / `guest`) |

Verify containers are up:

```bash
docker-compose ps
```

Expected: all three services (`zookeeper`, `kafka`, `rabbitmq`) show **Up**.

---

## Step 2 — Run Transaction Service (Producer)

Open **Terminal 1**:

```bash
cd transaction-service
mvn spring-boot:run
```

Wait until you see:
```
Started TransactionServiceApplication in ... seconds
```

Service is now listening on **port 8082**.

---

## Step 3 — Run Account Service (Consumer)

Open **Terminal 2**:

```bash
cd account-service
mvn spring-boot:run
```

Wait until you see:
```
Started AccountServiceApplication in ... seconds
```

Service is now listening on **port 8081**.

Seeded accounts (in-memory):

| userId | Initial Balance |
|--------|----------------|
| 101    | 10,000.00       |
| 102    |  5,000.00       |

---

## Step 4 — End-to-End Test

Open **Terminal 3** and run the commands below in order. After each `curl` call,
observe the **account-service** console (Terminal 2) for consumer output.

---

### 4.1 — DEBIT via Kafka

```bash
curl -s -X POST http://localhost:8082/transaction \
  -H "Content-Type: application/json" \
  -d '{"id":1,"userId":101,"amount":500.00,"type":"DEBIT"}' \
  && echo
```

**Expected HTTP response:**
```
Transaction submitted via Kafka: 1
```

**Expected account-service console:**
```
[Kafka] Received event: Transaction{id=1, userId=101, amount=500.0, type=DEBIT}
[Kafka] Applied Transaction{id=1, userId=101, amount=500.0, type=DEBIT} | balance: 10000.0 → 9500.0
```

---

### 4.2 — CREDIT via RabbitMQ

```bash
curl -s -X POST http://localhost:8082/transaction/rabbit \
  -H "Content-Type: application/json" \
  -d '{"id":2,"userId":101,"amount":200.00,"type":"CREDIT"}' \
  && echo
```

**Expected HTTP response:**
```
Transaction submitted via RabbitMQ: 2
```

**Expected account-service console:**
```
[RabbitMQ] Received event: Transaction{id=2, userId=101, amount=200.0, type=CREDIT}
[RabbitMQ] Applied Transaction{id=2, userId=101, amount=200.0, type=CREDIT} | balance: 9500.0 → 9700.0
```

---

### 4.3 — Verify Balance

```bash
curl -s http://localhost:8081/account/101 && echo
```

**Expected response:**
```json
{"userId":101,"balance":9700.0}
```

---

### 4.4 — DEBIT on Second Account via Kafka

```bash
curl -s -X POST http://localhost:8082/transaction \
  -H "Content-Type: application/json" \
  -d '{"id":3,"userId":102,"amount":1000.00,"type":"DEBIT"}' \
  && echo
```

**Expected account-service console:**
```
[Kafka] Received event: Transaction{id=3, userId=102, amount=1000.0, type=DEBIT}
[Kafka] Applied Transaction{id=3, userId=102, amount=1000.0, type=DEBIT} | balance: 5000.0 → 4000.0
```

Verify:
```bash
curl -s http://localhost:8081/account/102 && echo
```

**Expected response:**
```json
{"userId":102,"balance":4000.0}
```

---

## Step 5 — Test Idempotency

Send transaction **id=2** again (same payload as Step 4.2):

```bash
curl -s -X POST http://localhost:8082/transaction/rabbit \
  -H "Content-Type: application/json" \
  -d '{"id":2,"userId":101,"amount":200.00,"type":"CREDIT"}' \
  && echo
```

**Expected account-service console:**
```
[RabbitMQ] Received event: Transaction{id=2, userId=101, amount=200.0, type=CREDIT}
[IDEMPOTENCY] Duplicate tx 2 ignored.
```

Balance must remain unchanged:

```bash
curl -s http://localhost:8081/account/101 && echo
```

**Expected response:**
```json
{"userId":101,"balance":9700.0}
```

The balance did **not** change — exactly-once semantics enforced in-memory.

---

## Step 6 — Verify via RabbitMQ Management UI

1. Open http://localhost:15672 and login with `guest` / `guest`
2. **Exchanges** tab → find `transaction-exchange` (type: `topic`)
3. **Queues** tab → find `transaction-queue` → the **Ready** column should be `0`
   (all messages were consumed by account-service)
4. Click into `transaction-queue` → **Bindings** shows routing key `transaction.key`
5. Optionally click **Get messages** to inspect message bodies (JSON payloads)

---

## Step 7 — Teardown

```bash
docker-compose down
```

Add `-v` to also remove Kafka/ZooKeeper volumes:
```bash
docker-compose down -v
```

---

## Architecture

```
┌─────────────────────────────┐
│     transaction-service      │  :8082
│                             │
│  POST /transaction  ────────┼──► Kafka topic "transactions"
│                             │        partitioned by userId
│  POST /transaction/rabbit ──┼──► RabbitMQ exchange "transaction-exchange"
│                             │        routing key "transaction.key"
└─────────────────────────────┘
              │ Kafka                          │ RabbitMQ
              ▼                                ▼
┌─────────────────────────────────────────────────────────┐
│                    account-service  :8081               │
│                                                         │
│  KafkaTransactionConsumer  (@KafkaListener)             │
│  RabbitTransactionConsumer (@RabbitListener)            │
│          │                                              │
│          └──► AccountService.apply(tx)                  │
│                  ├── Idempotency check (processedTxIds) │
│                  └── Balance update (ECST)              │
│                                                         │
│  GET /account/{userId}  ← read current balance         │
└─────────────────────────────────────────────────────────┘
```

### Message Converter (RabbitMQ)

`Jackson2JsonMessageConverter` is registered on the producer's `RabbitTemplate`
(in `RabbitConfig`) and on the consumer's `SimpleRabbitListenerContainerFactory`
(auto-detected via the `@Bean` in account-service's `RabbitConfig`).
This means the `Transaction` POJO is serialised to JSON on send and
deserialised back to a `Transaction` object on receive — no manual parsing needed.

---

## Extending to Saga Pattern

To implement a full Saga, chain services with compensating actions:

```
Transaction Service  →(Kafka)→  Account Service  →(Kafka)→  Payment Service
                                     │                            │
                              (on failure)                 (on failure)
                              publish ROLLBACK             publish CANCEL
```

Each step publishes its outcome as an event; downstream services listen and either
proceed or trigger a compensating transaction.
