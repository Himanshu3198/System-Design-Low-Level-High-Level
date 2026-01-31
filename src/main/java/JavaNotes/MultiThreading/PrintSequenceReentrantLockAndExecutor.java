package JavaNotes.MultiThreading;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class PrintSequenceWithThreadPool{

     ReentrantLock reLock = new ReentrantLock();
     private final Condition conditionA = reLock.newCondition();
     private final Condition conditionB = reLock.newCondition();
     private final Condition conditionC = reLock.newCondition();
     private int state = 0;


     public void printA() throws InterruptedException {
         reLock.lock();
         try {
             while (state != 0) conditionA.await();
             System.out.println("A-> " + Thread.currentThread().getName());
             state = 1;
             conditionB.signal();
         }finally {
             reLock.unlock();
         }
     }

     public void printB() throws InterruptedException{
         reLock.lock();
         try {
             while (state != 1) conditionB.await();
             System.out.println("B->" + Thread.currentThread().getName());
             state = 2;
             conditionC.signal();
         }finally {
             reLock.unlock();
         }

    }

    public void printC() throws InterruptedException{
         reLock.lock();
         try {
             while (state != 2) conditionC.await();
             System.out.println("C->" + Thread.currentThread().getName());
             conditionA.signal();
             state = 0;
         }finally {
             reLock.unlock();
         }
    }




}
public class PrintSequenceReentrantLockAndExecutor {

    public static void main(String[] args) {

       PrintSequenceWithThreadPool p = new PrintSequenceWithThreadPool();
        ExecutorService executor = Executors.newFixedThreadPool(3);
        executor.submit(()->{
            try {
                p.printA();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        executor.submit(()->{
            try{
                p.printB();
            }catch (InterruptedException e){
                throw new RuntimeException(e);
            }
        });

        executor.submit(()->{
            try{
                p.printC();
            }catch (InterruptedException e){
                throw new RuntimeException(e);
            }
        });
        executor.close();
    }
}
