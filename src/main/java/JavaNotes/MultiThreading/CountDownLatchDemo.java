package JavaNotes.MultiThreading;


import java.util.concurrent.CountDownLatch;

class Worker3 implements Runnable{

    private final CountDownLatch start;
    private final CountDownLatch end;
    private final int id;
    public Worker3(CountDownLatch start,CountDownLatch end,int id){
        this.start = start;
        this.end = end;
        this.id = id;
    }
    @Override
    public void run() {

        try{
            System.out.println(Thread.currentThread().getName()+" Worker is ready to start"+id);
            start.await();
            System.out.println(Thread.currentThread().getName()+" Worker started working: "+id);
            Thread.sleep(id*500L);
            System.out.println(Thread.currentThread().getName()+" Worker completed the work: "+id);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }finally {
            end.countDown();  // single completion
        }
    }
}
public class CountDownLatchDemo {

    public static void main(String[] args) throws InterruptedException {

        int totalWorker = 3;
        CountDownLatch  start = new CountDownLatch(1);
        CountDownLatch end = new CountDownLatch(totalWorker);

        for(int i=1;i<=3;i++){
            new Thread(new Worker3(start,end,i)).start();
        }

        System.out.println("Main thread starting the all worker");
        start.countDown();
        end.await();
        System.out.println("Main thread: All workers finished. Proceeding...");


    }
}
