package JavaNotes.MultiThreading;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

class Worker1 implements Runnable{

    private final CyclicBarrier cyclicBarrier;
    private final int id;
    public Worker1(CyclicBarrier cyclicBarrier,int id){
        this.cyclicBarrier =cyclicBarrier;
        this.id =id;
    }

    @Override
    public void run(){
        try {
            System.out.println(Thread.currentThread().getName()+"="+id+" Completing the phase1 work");
            Thread.sleep(id* 500L);
            System.out.println(Thread.currentThread().getName()+"="+id+" Reached the barrier point");
            cyclicBarrier.await();
            System.out.println(Thread.currentThread().getName()+"="+id+" Completing the phase2 work");
        } catch (InterruptedException | BrokenBarrierException e) {
            throw new RuntimeException(e);
        }
    }
}
public class CyclicBarrierDemo {

    public static void main(String[] args) {

        CyclicBarrier cyclicBarrier = new CyclicBarrier(3,()->{
            System.out.println("All the threads reached the barrier Opening the Barrier!");
        });

        for(int i=1;i<=3;i++){
            new Thread(new Worker1(cyclicBarrier,i)).start();
        }
    }
}


/*
output:
> Task :JavaNotes.MultiThreading.CyclicBarrierDemo.main()
Thread-1=2 Completing the phase1 work
Thread-0=1 Completing the phase1 work
Thread-2=3 Completing the phase1 work
Thread-0=1 Reached the barrier point
Thread-1=2 Reached the barrier point
Thread-2=3 Reached the barrier point
All the threads reached the barrier Opening the Barrier!
Thread-0=1 Completing the phase2 work
Thread-1=2 Completing the phase2 work
Thread-2=3 Completing the phase2 work

Deprecated Gradle features were used in this build, making it incompatible with Gradle 9.0.


 */