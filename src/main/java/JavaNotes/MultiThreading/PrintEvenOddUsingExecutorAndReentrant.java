package JavaNotes.MultiThreading;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class Counter1{

     private int number = 1;
     private final int CAPACITY;
     private final ReentrantLock rnLock;
     private final Condition conditionOdd;
     private final Condition conditionEven;

     public Counter1(int capacity){
         this.CAPACITY = capacity;
         this.rnLock = new ReentrantLock();
         this.conditionOdd = rnLock.newCondition();
         this.conditionEven = rnLock.newCondition();
     }

     public void printOdd(){
         while(number <CAPACITY) {
             try {
                 rnLock.lock();
                 while(number % 2 == 0) {
                     conditionOdd.await();
                 }
                 if(number > CAPACITY) break;
                 System.out.println(Thread.currentThread().getName()+" Print Odd: " + number);
                 number++;
                 conditionEven.signal();
             } catch (InterruptedException e) {
                 e.printStackTrace();
             } finally {
                 rnLock.unlock();
             }
         }

     }

     public void printEven() {
         while (number < CAPACITY) {
             try {
                 rnLock.lock();
                 while (number % 2 == 1) {
                     conditionEven.await();
                 }
                 if(number > CAPACITY) break;
                 System.out.println(Thread.currentThread().getName()+" Print even: " + number);
                 number++;
                 conditionOdd.signal();
             } catch (InterruptedException e) {
                 e.printStackTrace();
             } finally {
                 rnLock.unlock();
             }
         }
     }

}
public class PrintEvenOddUsingExecutorAndReentrant {

    public static void main(String[] args) {
        Counter1 c = new Counter1(15);
        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(c::printOdd);
        executor.submit(c::printEven);
        executor.close();
    }
}
