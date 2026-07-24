import java.util.LinkedList;
import java.util.Queue;

public class QueueExample {
    public static void main(String[] args) {
        // Instantiate a Queue using a LinkedList implementation
        Queue<String> line = new LinkedList<>();
        line.add("Hello");
        line.add("to");
        line.add("world");
        System.out.println(line);
    }
}
