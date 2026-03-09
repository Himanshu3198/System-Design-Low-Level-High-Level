package JavaNotes.MultiThreading.practice;

import java.util.concurrent.Semaphore;

class SemaphoreSharedResourceA{

    private final String  myResource;
    private final Semaphore semaphore = new Semaphore(2);
    public SemaphoreSharedResourceA(String myResource){
        this.myResource = myResource;
    }
    public void getResource(int thread){
        try{
            System.out.println("thread :"+thread+ " want to access resource");
            semaphore.acquire();
            System.out.println("thread:"+thread+" resource granted");
            Thread.sleep(500);
            System.out.println("thread: "+thread+"consume resource "+myResource);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }finally {
            semaphore.release();
            System.out.println("Releasing the permit for thread: "+thread);
        }
    }
}
public class SemaphorePractice {

    public static void main(String[] args) {

        SemaphoreSharedResourceA semaphoreSharedResourceA = new SemaphoreSharedResourceA("java course");
        for(int i=1;i<=5;i++){
            final int finalI = i;
            Thread thread = new Thread(()->{
                semaphoreSharedResourceA.getResource(finalI);
            });
            thread.start();
        }
    }
}
