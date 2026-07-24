import java.util.function.*;

public class  Main{
   public static void main(String[] args) {
       BiFunction<Integer,Integer,Float> percent= (a, b) -> (float)a / b * 100;
       System.out.println("percent is : " + percent(37,56));

   }
}
