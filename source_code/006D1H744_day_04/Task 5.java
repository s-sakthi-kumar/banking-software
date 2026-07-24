import java.util.ArrayDeque;
import java.util.Deque;

public class DequeStackExample {
    public static void main(String[] args) {
        // 1. Initialize a Deque as a Stack
        Deque<String> stack = new ArrayDeque<>();

        // 2. Push elements onto the stack (LIFO)
        stack.push("Books");
        stack.push("Laptop");
        stack.push("Coffee");

        System.out.println("Stack after pushes: " + stack); 
        // Output: [Coffee, Laptop, Books]

        // 3. Peek at the top element without removing it
        String topItem = stack.peek();
        System.out.println("Top item (peek): " + topItem); 
        // Output: Coffee

        // 4. Pop elements off the stack
        String poppedItem = stack.pop();
        System.out.println("Popped item: " + poppedItem); 
        // Output: Coffee

        System.out.println("Stack after pop: " + stack); 
        // Output: [Laptop, Books]

        while (!stack.isEmpty()) {
            System.out.println("Popping remaining: " + stack.pop());
        }
    }
}
