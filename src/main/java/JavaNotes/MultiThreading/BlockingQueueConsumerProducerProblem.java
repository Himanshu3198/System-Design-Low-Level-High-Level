package JavaNotes.MultiThreading;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class SharedResource2{

    private final BlockingQueue<Integer> queue;
    public SharedResource2(int capacity){
        this.queue = new ArrayBlockingQueue<>(capacity);
    }

    public void produce(int value) throws InterruptedException {
        queue.put(value);
        System.out.println(Thread.currentThread().getName()+" producer produce the message"+value);
    }

    public void consume() throws InterruptedException{
        int get = queue.take();
        System.out.println(Thread.currentThread().getName()+" consume consume the message"+get);
    }
}

class Consumer implements  Runnable{

    private final SharedResource2 sharedResource;
    public Consumer(SharedResource2 sharedResource){
        this.sharedResource = sharedResource;
    }
    @Override
    public void run() {
        for(int i=0;i<10;i++){
            try {
                sharedResource.consume();
                Thread.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

class Producer implements Runnable{

    private final SharedResource2 sharedResource;
    public Producer(SharedResource2 sharedResource){
        this.sharedResource = sharedResource;
    }
    @Override
    public void run() {
        for(int i=0;i<10;i++){
            try {
                sharedResource.produce(i);
                Thread.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

public class BlockingQueueConsumerProducerProblem {

    public static void main(String[] args) {
        SharedResource2 sharedResource2 = new SharedResource2(5);
        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(new Producer(sharedResource2));
        executor.submit(new Consumer(sharedResource2));
        executor.close();
    }


}
