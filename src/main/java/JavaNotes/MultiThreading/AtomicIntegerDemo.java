package JavaNotes.MultiThreading;

import java.util.concurrent.atomic.AtomicInteger;

class Atomic{

    private AtomicInteger  counter;
    private  int normalCounter;
    public Atomic(){
        this.counter = new AtomicInteger();
        this.normalCounter = 0;
    }

    public void increment(){
        counter.incrementAndGet();
        normalCounter++;
    }

    public int getValue(){
        return  counter.get();
    }

    public int getNormalValue(){
        return  normalCounter;
    }
}
public class AtomicIntegerDemo {

    public static void main(String[] args) {

        Atomic c = new Atomic();
        Thread [] threads = new Thread[100000];
        for(int i=0;i<100000;i++){
            threads[i] =new Thread(c::increment);
            threads[i].start();

        }
        for(Thread t:threads){
            try {
                t.join();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        System.out.println("Check the Atomic Counter value! "+c.getValue());
        System.out.println("Check the Normal Counter value! "+c.getNormalValue());

    }

}
