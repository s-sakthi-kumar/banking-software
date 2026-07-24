import java.util.Scanner;

public class Login{
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        String login;
        String pswd;
        while(true){
            System.out.print("Enter Login ID:");
            login = scanner.nextLine();
            System.out.print("Enter Password:");
            pswd = scanner.nextLine();
            if(login == "Parasunamba" && pswd=="4321"){
                System.out.println("Welcome "+login+"!");
            }
        }
    }

}
