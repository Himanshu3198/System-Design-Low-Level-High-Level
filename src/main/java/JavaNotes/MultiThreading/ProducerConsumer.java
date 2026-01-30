package JavaNotes.MultiThreading;


import java.util.LinkedList;
import java.util.Queue;

class MessageQueue{

    private final int CAPACITY;
    Queue<Integer>  queue = new LinkedList<>();

    public MessageQueue(int capacity){
        this.CAPACITY = capacity;
    }


    public synchronized void producer(int x) throws InterruptedException {
        while(queue.size() ==CAPACITY){
            System.out.println("Waiting for message to consume");
            wait();
        }
        queue.offer(x);
        System.out.println("Producer has produced: "+x);
        notify();
    }

    public synchronized void consumer() throws InterruptedException {

        while(queue.isEmpty()){
            System.out.println("Waiting for message!");
            wait();
        }

             int message = queue.poll();
             System.out.println("Message has consumed! "+message);
             notify();


    }
}
public class ProducerConsumer {

    public static void main(String[] args) {

        MessageQueue mq = new MessageQueue(10);
        Thread t1 = new Thread(()->{
            for(int i=1;i<=15;i++){
                try {
                    mq.producer(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(()->{
            for(int j =1;j<=15;j++){
                try{
                    mq.consumer();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        });
        t1.start();
        t2.start();
    }


}
