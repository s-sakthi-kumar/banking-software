import java.util.Arrays;
public class Geeks{
    
  	// Method
    public static void print(String s){
        System.out.println(s);
    }

    public static void main(String[] args){
        
        String[] names = {"Geek1", "Geek2", "Geek3"};

        // Using method reference to print each name
        Arrays.stream(names).forEach(Geeks::print);
        //NOTE: this will provide refernce for the print method.
    }
}
