public class Main{
    public static void main(String[] args){
        int a,b;
        int[] a= {1,2,3,4};
        try{
            System.out.println(a[5]);
        }catch(ArrayIndexOutOfBoundsException e){
            System.out.println("array out of bounds");
        }catch(ArithmeticException e){
            System.out.println("cannot divide by zero");

        }

    }
}
