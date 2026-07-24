import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;



public class Demo04_LifeCycleHooks_Setup_TearDown {
   private Demo04_DatabaseService db;



   @BeforeEach
   void init() {
       db = new Demo04_DatabaseService();
       db.connect();

   }



   @AfterEach
   void cleanup() {
       db.disconnect();
   }


   @Test
   void testInsertAndFetchPass() {
       db.insert("user1", "Prasunamba");
       assertEquals("Prasunamba", db.fetch("user1"));
   }


   //fails
   @Test
   void testInsertAndFetchFailValue() {
       db.insert("user1", "Prasunamba");
       // This will fail because actual = "Prasunamba", expected = "WrongName"
       assertEquals("WrongName", db.fetch("user1"), "Expected WrongName but got " + db.fetch("user1"));
   }


   //fails
   @Test
   void testFetchNonExistentKeyFail() {
       // No insert performed, so fetch returns null
       assertEquals("SomeValue", db.fetch("missingKey"), "Expected SomeValue but got null");
   }
}
