package MockTest;

class PrintSequence{

     private int count;
     private final int capacity;
     public PrintSequence(int capacity){
         this.count = 1;
         this.capacity = capacity;
     }

     public synchronized void printOdd() throws InterruptedException {
         while(count < capacity) {
             while (count % 2 == 0) {
                 wait();
             }
             System.out.println(Thread.currentThread().getName()+" Print Odd sequence " + count);
             count++;
             notify();
         }
     }

     public synchronized void printEven() throws InterruptedException{
         while(count < capacity) {
             while (count % 2 == 1) {
                 wait();
             }
             System.out.println(Thread.currentThread().getName()+" Print Even sequence" + count);
             count++;
             notify();
         }
     }

}
public class PrintEVenOddTest {
    public static void main(String[] args) {
          PrintSequence p = new PrintSequence(10);
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
