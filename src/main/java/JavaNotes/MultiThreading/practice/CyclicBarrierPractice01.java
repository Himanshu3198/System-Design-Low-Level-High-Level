package JavaNotes.MultiThreading.practice;


import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

class Worker2 implements Runnable{

    private final CyclicBarrier cyclicBarrier;
    private final int threadId;

    public Worker2(CyclicBarrier cyclicBarrier, int threadId){
        this.cyclicBarrier = cyclicBarrier;
        this.threadId = threadId;
    }
    @Override
    public void run() {

        try {
            System.out.println("thread has enter in lobby: "+threadId);
            cyclicBarrier.await();
            System.out.println("All threads has reached the circle starting the processing");
            Thread.sleep(500);
            System.out.println("thread with id has completed the processing"+threadId);
        }catch (InterruptedException e){
            System.out.println(e.getMessage());
        } catch (BrokenBarrierException e) {
            throw new RuntimeException(e);
        }
    }
}
public class CyclicBarrierPractice01 {

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3,()->{
            System.out.println("All the threads has reached the barrier!");
        });

        for(int i=1;i<=6;i++){
            Thread thread = new Thread(new Worker2(cyclicBarrier,i));
            thread.start();
        }

    }
}
