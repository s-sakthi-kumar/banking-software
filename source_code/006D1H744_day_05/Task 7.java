import java.util.LinkedList;



public class Main{
    public static void main(String[] args){
        LinkedList<ThreadI> listT = new LinkedList<>();

        Testing[] obj = {new Testing()};

        obj[0].h = 0;

        for(int i=0;i<20;i+=1){
            listT.add(new ThreadI(i,obj));
        }

        for(ThreadI i:listT){
            i.start();
        }
        try{
        Thread.sleep(1000);
        }catch(Exception e){
            System.out.println(e);
        }
        System.out.println("sum:"+obj[0].h);
    }
    
}



class Testing{
    public int h;
    public synchronized void increment(int add){
        h+= add;

    }
}

class ThreadI implements Runnable{
    public Thread t;
    public int threadId;
    public Testing[] k;
    ThreadI(int threadId, Testing[] t){
        this.threadId = threadId;

        this.k = t;
    }
    public void run(){
        
        for(int i=0;i<1000;i++){
            k[0].increment(threadId);
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
