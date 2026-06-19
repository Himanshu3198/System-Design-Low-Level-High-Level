package JavaNotes.practice;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class Printer1{

    private int  counter;
    private int limit;
    private  ReentrantLock rn;
    private Condition conditionOdd;
    private Condition conditionEven;
    public Printer1(int limit){
        this.counter = 1;
        this.limit = limit;
        this.rn = new ReentrantLock();
        this.conditionEven = this.rn.newCondition();
        this.conditionOdd = this.rn.newCondition();
    }

    public void printEven() {

        rn.lock();
        try{
            while(counter < limit){

                while(counter %2 == 1){
                    conditionEven.await();
                }

                System.out.println("Even sequence: "+counter);
                counter++;
                conditionOdd.signal();
            }
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }finally {
            rn.unlock();
        }

    }

    public  void printOdd(){
        rn.lock();
        try{
            while (counter < limit){
                while (counter %2 == 0) {
                    conditionOdd.await();
                }
                System.out.println("Odd sequence: "+counter);
                counter++;
                conditionEven.signal();
            }
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }finally {
            rn.unlock();
        }



    }
}
public class PracticePrintEvenOddUsingReentrantLock {

    public static void main(String[] args) {
        Printer1 p = new Printer1(10);
        Thread t1 = new Thread(p::printOdd);
        Thread t2 = new Thread(p::printEven);
        t1.start();
        t2.start();
    }
}
