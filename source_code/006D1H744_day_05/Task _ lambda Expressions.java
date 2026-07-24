import java.util.*;

public class LambdaDemo {
    public static void main(String[]args) {

        // Lambda Expression
        Runnable r2 = () ->
        System.out.println("After: Hello!");
        // Lambda with parameters

        List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
        names.forEach(name ->
        System.out.println("Name: " + name));

        r2.run();
    }
}
