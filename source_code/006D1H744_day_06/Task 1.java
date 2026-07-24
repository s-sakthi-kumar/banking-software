@FunctionalInterface
interface Hello{
    void hello();
}

public class Main{
    public static void main(String[] args){
        Hello world = () => {
            System.out.println("hello world");
        }
        world.hello();
    }
}
