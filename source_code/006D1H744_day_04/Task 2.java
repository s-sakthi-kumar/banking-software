import java.util.LinkedList;

public class LLDemo{
    public static void main(String[] args){
        LinkedList<String> skills = new LinkedList<>();
        skills.add("Java");
        skills.add("C++");
        System.out.println("Initial List: " + skills);
        skills.addFirst("OOPS");
        System.out.println("1 List: " + skills);
        skills.set(1,"DSA");
        System.out.println("2 List: " + skills);

    }

}
