import java.util.Scanner;

class Test{

    public static void main(String[] args){
        int age = 20;
        Scanner scan1 = new Scanner(System.in);
        age = scan1.nextInt();
        assert age>=18:"cannot vote";
        System.out.println("U"+age);
    }

}
