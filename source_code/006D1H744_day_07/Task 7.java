import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import java.time.Duration;



class Demo06_TimeoutTest_PerformanceCheck {
   @Test
   void testQuickExecutionPass() {
       Demo06_PerformanceService service = new Demo06_PerformanceService();
       assertTimeout(Duration.ofMillis(500), () -> {
           service.quickOperation(); // completes in 100ms
       });
   }
   //fails
   @Test
   void testSlowExecutionFail() {
       Demo06_PerformanceService service = new Demo06_PerformanceService();
       assertTimeout(Duration.ofMillis(500), () -> {
           service.slowOperation(); // takes 1000ms, exceeds timeout
       });
   }


  //fails
   @Test
   void testUnrealisticTimeoutFail() {
       Demo06_PerformanceService service = new Demo06_PerformanceService();
       assertTimeout(Duration.ofMillis(50), () -> {
           service.quickOperation(); // takes ~100ms, exceeds 50ms
       });
   }
}
