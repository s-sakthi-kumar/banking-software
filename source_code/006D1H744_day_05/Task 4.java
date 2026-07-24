import java.util.LinkedList;

public class Main{
    public static void main(String[] args){
        LinkedList<ThreadI> listT = new LinkedList<>();
        String[] threadNames = {"Morning","AfterNoon","Evening"}
        int[] priority = {1,5,10};
        for(int i=0;i<3;i+=1){
            listT.add(new ThreadI(i,threadNames[i%3],priority[i]));
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
    public threadPriority;
    ThreadI(int threadId, String threadName, int threadPriority){
        this.threadId = threadId;
        this.threadName = threadName;
        this.threadPriority = threadPriority;
    }
    public void run(){
        try {
            Thread.sleep(200);
            System.out.println("Good " + t.getName());

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e);
        }
  
    }
    public void start(){
        if(t==null){
            try{
                t = new Thread(this.threadName);
                t.setPriority(this.threadPriority);
                t.start();
            }catch(Exception e){
                System.out.println(e);

            }
        }
    }
}
