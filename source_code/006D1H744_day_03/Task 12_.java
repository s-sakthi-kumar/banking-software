import java.util.ArrayList;
import java.util.Scanner;

public class Main{
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        ArrayList<String> list = new ArrayList<>();
        while(true){
            System.out.println("1.Add Login\n2.Print");
            char a = scan.nextLine().charAt(0);
            switch(a){
                case '1':{
                    System.out.print("Enter Login:");
                    String s = scan.nextLine();
                    list.add(s);
                    System.out.println("added to List");
                    break;
                }
                case '2':{
                    int i = 1;
                    System.out.println("\n\nFor Each");
                    for(String s:list){
                        System.out.println(""+i+". "+s);
                        i = i+1;
                    }
                }
                
            }

        }
    }
}
