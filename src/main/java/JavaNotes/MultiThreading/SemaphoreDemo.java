package JavaNotes.MultiThreading;


// n threads wanted to access the resource restricted only two a time

import java.util.concurrent.Semaphore;

class SemaphoreSharedResource{
    private final Semaphore semaphore = new Semaphore(2);

    public void accessResource(int thread){

        try {
            System.out.println(Thread.currentThread().getName()+"  want to access the resource"+thread);
            semaphore.acquire();
            System.out.println(Thread.currentThread().getName()+" granted the access "+thread);
            Thread.sleep(2000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            System.out.println(thread+" releasing the permit");
            semaphore.release();
        }
    }
}
public class SemaphoreDemo {

    public static void main(String[] args) {

        SemaphoreSharedResource s1 = new SemaphoreSharedResource();
        for(int i=1;i<5;i++){

            int finalI = i;
            new Thread(()->{
                 s1.accessResource(finalI);
             }).start();
        }
    }
}
