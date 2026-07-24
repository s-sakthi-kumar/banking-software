public class StringExample{

    public static void main(String[] args){
        String s = "Hello World";
        // here we start with the last char and print one by one till the start
        for(int i=s.length-1;i>=0;i--)  
            System.out.print(s.charAt(i));
        System.out.println();
    }
}
