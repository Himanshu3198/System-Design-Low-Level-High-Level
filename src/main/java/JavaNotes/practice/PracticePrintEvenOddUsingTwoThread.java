package JavaNotes.practice;

class Printer{

    private int  counter;
    private int limit;
    public Printer(int limit){
        this.counter = 1;
        this.limit = limit;
    }

    public synchronized void printEven() throws InterruptedException {

        while(counter < limit){
            while(counter %2 ==1){
                wait();
            }

            System.out.println("Even sequence: "+counter);
            counter++;
            notifyAll();
        }

    }

    public synchronized void printOdd() throws InterruptedException {
        while (counter < limit){
            while (counter %2 == 0) {
                wait();
            }
            System.out.println("Odd sequence: "+counter);
            counter++;
            notifyAll();
        }


    }
}
public class PracticePrintEvenOddUsingTwoThread {

    public static void main(String[] args) {

        Printer p = new Printer(10);
        Thread t1 = new Thread(()->{
            try {
                p.printOdd();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread t2 = new Thread(()->{
            try{
                p.printEven();
            }catch (InterruptedException e){
                throw new RuntimeException(e);
            }
        });
        t1.start();
        t2.start();
    }
}
