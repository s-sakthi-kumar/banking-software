public class Main{
    public static void main(String[] args) {
        Print obj = (int a ) -> System.out.println(a);
        obj.printNumber(199);

   }
}

@FunctionalInterface
interface Print {

    void printNumber(int a);
    default void  printHelloWorld(){
        System.out.println("Hello world");
    }
    default int  regularMethod(int a ){
        System.out.println(a);
        return a;
    }
}
