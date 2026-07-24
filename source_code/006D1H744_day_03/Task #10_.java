import java.util.Scanner;

public class Conditional{
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        int a = scan.nextInt();
        if(a%2==0){
            System.out.println("a is even number");
        }
        else{
            System.out.println("a is odd");
        }
        if(a>0){
            System.out.println("a is a natural number");
        }else  if(a<0){
            System.out.println("a is an integer");
        }
        if(a>0){
            if(a%2==0){
                System.out.println("ä is a natural number and an even number");
            }
        }
    }
}
