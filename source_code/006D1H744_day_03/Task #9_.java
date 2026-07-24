import java.util.Scanner;

public class Login{
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        String login;
        String pswd;
        do{
            System.out.print("Enter Login ID:");
            login = scanner.nextLine();
            System.out.print("Enter Password:");
            pswd = scanner.nextLine();
            System.out.println("Welcome "+login+"!");
            
        }while(true);
    }

}
