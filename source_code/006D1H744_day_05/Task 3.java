import java.util.LinkedList;

public class Main{
    public static void main(String[] args){
        LinkedList<ThreadI> listT = new LinkedList<>();
        String[] threadNames = {"Morning","AfterNoon","Evening"}
        for(int i=0;i<3;i+=1){
            listT.add(new ThreadI(i,threadNames[i%3]));
        }

        for(ThreadI i:listT){
            i.start();
        }
    }
}


class ThreadI implements Runnable{
    public Thread t;
    public int threadId;
    public String threadName;
    ThreadI(int threadId, String threadName){
        this.threadId = threadId;
        this.threadName = threadName;
    }
    public void run(){
        try {
            switch(threadId%3){
                case 0:{
                    Thread.sleep(2000);
                    System.out.println("Good morning");
                    break;
                }
                case 1:{
                    Thread.sleep(1000);
                    System.out.println("Good AfterNoon");
                    break;
                }
                case 2:{
                    System.out.println("Good Evening");
                    break;
                }
            }

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e);
        }
  
    }
    public void start(){
        if(t==null){
            try{
                t = new Thread(this,"thread"+threadId);
                t.start();
            }catch(Exception e){
                System.out.println(e);

            }
        }
    }
}
