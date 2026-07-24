import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;



import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;



class StringUtils{
    // randome palindrom code from internet
     static boolean isPalindrome(String s){

        s = s.toLowerCase();
        int i = 0, j = s.length() - 1;

        while (i < j) {

            if (s.charAt(i) != s.charAt(j)) {
                return false;
            }
            i++;
            j--;
        }
        return true;
    }
}


public class Parameterized_Test {


   @ParameterizedTest
   @ValueSource(strings = {"amma", "mom", "nitin"})
   void testPalindromePass(String candidate) {
       assertTrue(StringUtils.isPalindrome(candidate));
   }
   //fail
   @Test
   void testPalindromeFail() {
       // "hello" is NOT a palindrome, so this will fail
       assertTrue(StringUtils.isPalindrome("hello"), "Expected true but got false");

   }


   @ParameterizedTest
   @ValueSource(strings = {"java", "spring", "bank"})
   void testNotPalindrome(String candidate) {
       assertFalse(StringUtils.isPalindrome(candidate));
   }
}
