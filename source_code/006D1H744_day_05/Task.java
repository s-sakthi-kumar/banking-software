import java.util.Arrays;
import java.util.List;
import java.util.function.*;
class Main{
    public static void main(String[] args){
        Function<Integer,Float> cnvrt = (Integer a) -> (float)a;
        Float y = cnvrt.apply(189);
        System.out.println("Float"+y);
        Predicate<String> validate = (String s) -> s=="password";

        System.out.println(validate.test("my Password")?"Welcome":"not welcome");
        Supplier<List<String>> welcomeMessages = ()-> Arrays.asList("hello", "world", "this is a list");
        welcomeMessages.get().forEach(i -> System.out.println(i));
    }
}
