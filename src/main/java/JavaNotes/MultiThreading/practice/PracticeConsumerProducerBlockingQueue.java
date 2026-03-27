package JavaNotes.MultiThreading.practice;

import jakarta.servlet.ServletOutputStream;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class SharedResource3{
    private final BlockingQueue<Integer> queue;

    public SharedResource3(int capacity){
        this.queue = new ArrayBlockingQueue<>(2);
    }

    public void produce(int value) throws InterruptedException {
        queue.put(value);
        System.out.println("producer publish: "+value);
    }

    public void consumer() throws InterruptedException{
        int val = queue.take();
        System.out.println("Consumer consume: "+val);
    }
}

class Consumer1 implements  Runnable{

    private final SharedResource3 sharedResource3;
    public Consumer1(SharedResource3 sharedResource3){
        this.sharedResource3 = sharedResource3;
    }
    @Override
    public void run(){

        for(int i=0;i<10;i++){
            try {
                sharedResource3.consumer();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

class Producer2 implements Runnable{

    private final SharedResource3 sharedResource3;
    public Producer2(SharedResource3 sharedResource3){
        this.sharedResource3 = sharedResource3;
    }

    @Override
    public void run(){
        for(int i=0;i<10;i++){
            try {
                sharedResource3.produce(i);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
public class PracticeConsumerProducerBlockingQueue {

    public static void main(String[] args) {
        SharedResource3 sharedResource3 = new SharedResource3(5);
//        ExecutorService executor = Executors.newFixedThreadPool(2);
//        executor.execute(new Consumer1(sharedResource3));
//        executor.execute(new Producer2(sharedResource3));
//        executor.close();
        Thread t1 = new Thread(new Consumer1(sharedResource3));
        Thread t2 = new Thread(new Producer2(sharedResource3));
        t1.start();
        t2.start();
    }
}
