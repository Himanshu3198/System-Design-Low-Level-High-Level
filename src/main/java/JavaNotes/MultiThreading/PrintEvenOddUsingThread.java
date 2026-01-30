package JavaNotes.MultiThreading;


class Printer{

    private int counter = 1;
    private final int max;

    public Printer(int max){
        this.max = max;
    }

    public synchronized void printOdd() throws InterruptedException {
         while(counter < max) {
             if (counter % 2 == 0) {
                 wait();
             } else {
                 System.out.println("Printing odd: " + counter);
                 counter++;
                 notify();
             }
         }
    }

    public synchronized void printEven() throws InterruptedException{

        while(counter < max) {
            if (counter % 2 == 1) {
                wait();
            } else {
                System.out.println("Printing even: " + counter);
                counter++;
                notify();
            }
        }

    }
}

public class PrintEvenOddUsingThread {
    public static void main(String[] args) {
        int maxNumber = 10;
        Printer p = new Printer(maxNumber);

        Thread t1 = new Thread(()->{
            try{
               p.printOdd();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        });

        Thread t2 = new Thread(()->{
            try{
                p.printEven();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        });

        t1.start();
        t2.start();
    }
}
