import java.util.LinkedList;

public class Main{
    public static void main(String[] args){
        LinkedList<ThreadI> listT = new LinkedList<>();
        for(int i=0;i<20;i+=1){
            listT.add(new ThreadI(i));
        }

        for(ThreadI i:listT){
            i.start();
        }
        while(true){
            try{
            Thread.sleep(1000);
            }catch(Exception e){
                System.out.println(e);
            }
            for(ThreadI thrd:listT){
            System.out.println(thrd.t.getName()+" is alive? "+thrd.t.isAlive());
            }   
        }
    }
    
}


class ThreadI implements Runnable{
    public Thread t;
    public int threadId;
    ThreadI(int threadId){
        this.threadId = threadId;
    }
    public void run(){
        try {
            Thread.sleep(5000);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e);
        }
        switch(threadId%3){
            case 0:{
                System.out.println("Good morning"+ " from: "+threadId);
                break;
            }
            case 1:{
                System.out.println("Good Afternoon"+ " from: "+threadId);
                break;
            }
            case 2:{
                System.out.println("Good Evening"+ " from: "+threadId);
                break;
            }
        }
        // System.out.println(" From:"+threadId);
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
