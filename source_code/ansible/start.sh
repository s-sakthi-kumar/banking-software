#!/usr/bin/env bash

set -e

IMAGE="ansible-alpine"

echo "Building Alpine Ansible image..."
podman build -t "$IMAGE" .

echo "Removing old containers..."
podman rm -f ansible-server1 ansible-server2 2>/dev/null || true

echo "Starting server1..."
podman run -d \
    --name ansible-server1 \
    -p 2221:22 \
    "$IMAGE"

echo "Starting server2..."
podman run -d \
    --name ansible-server2 \
    -p 2222:22 \
    "$IMAGE"

echo
echo "Containers:"
podman ps --filter name=ansible-server

echo
echo "Ansible servers:"
echo "  server1 -> localhost:2221"
echo "  server2 -> localhost:2222"
