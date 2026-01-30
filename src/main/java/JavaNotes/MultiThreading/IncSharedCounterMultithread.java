package JavaNotes.MultiThreading;


class Counter{
    private  int count = 0;

    public synchronized  void increment(){
//        System.out.println("Thread modifying is: "+Thread.currentThread().getName());
        count++;
    }

    public int getCount(){
        return count;
    }
}
public class IncSharedCounterMultithread {
    public static void main(String[] args) throws InterruptedException {

        Counter c = new Counter();

        Thread t1 = new Thread(()->{
            for(int i=0;i<1000;i++){
                c.increment();
            }
        });

        Thread t2 = new Thread(()->{
            for(int i=0;i<1000;i++){
                c.increment();
            }
        });

        Thread t3 = new Thread(()->{
            for(int i=0;i<1000;i++){
                c.increment();
            }
        });

        t1.start();
        t2.start();
        t3.start();
        t1.join();
        t2.join();
        t3.join();
        System.out.println("count is: "+c.getCount());


    }
}
