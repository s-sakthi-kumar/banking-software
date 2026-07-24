import java.util.ArrayList;
import java.util.Arrays;

class Main{
    public static void main(String[] args){
    ArrayList<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 4, 5, 6, 7, 8, 89, 1, 212, 54));    // list = ;
        list.forEach(ele-> System.out.println(ele+" is "+(ele%2==1?"even":"odd")));
    }
}
