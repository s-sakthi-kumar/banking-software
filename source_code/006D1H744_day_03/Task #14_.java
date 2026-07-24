public class Main{
    public static void main(String[] args){
        int i = 0;
        while(true){
            i+=1;
            if(i%7==0){
                //this skips the execution of the current iteration of the loop and continues with the next
                continue;
            }
            if(i>=30){
                //this exits the loop and resumes execution after the loop statements 
                break;
            }
            //prints No.s which are not a multiple of 7 and less than 30
            System.out.print(i+" ");
        }

    }
}
