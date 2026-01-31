package JavaNotes.MultiThreading;

import org.apache.tomcat.util.net.jsse.JSSEUtil;

import java.math.RoundingMode;
import java.sql.SQLOutput;

class Resource{

    private int value;

    public Resource(int value){
        this.value = value;
    }



    public int getValue(){
        return value;
    }
}

class Worker implements Runnable{

    private Resource r1;
    private Resource r2;

    public Worker(Resource r1, Resource r2){
        this.r1 = r1;
        this.r2 = r2;
    }
    @Override
    public void run() {
        System.out.println("Begin the execution");
        Resource first = r1.getValue() < r2.getValue()?r1:r2;
        Resource second = r2.getValue() <r1.getValue()?r2:r1;
        if(r1.getValue() == r2.getValue()){
            System.out.println("Alert values collide");
        }

        synchronized (first){

            System.out.println(Thread.currentThread().getName()+" first locked  resource "+first.getValue());
            try{
                Thread.sleep(50);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        synchronized (second){
            System.out.println(Thread.currentThread().getName()+" second locked resource "+second.getValue());
            try {
                Thread.sleep(50);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        System.out.println("Completed the execution");
    }
}
public class NResourceWithNThreads {

    public static void main(String[] args) {

        int n=5;
        Resource[] resources = new Resource[5];
        for(int i=0;i<n;i++){
            resources[i] = new Resource(i);
        }
        Thread[]  threads = new Thread[n];
        for(int i=0;i<n;i++){

            Resource r1 = resources[i];
            Resource r2 = resources[(i+1)%n];
            threads[i] = new Thread(new Worker(r1,r2));
            threads[i].start();
        }
    }

}
