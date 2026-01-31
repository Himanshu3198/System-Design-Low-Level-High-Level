package JavaNotes.MultiThreading;



class PrintSequence{

      String[] arr = {"A","B","C"};
      private  int idx;
      private final int size;

      public PrintSequence(){
          idx = 0;
          size = 3;
      }

      public synchronized void printA() throws InterruptedException {
           while(idx<size && !arr[idx].equals("A")){
               wait();
           }
          System.out.println("Print Sequence-> "+Thread.currentThread().getName()+"="+"A");
           idx++;
           notifyAll();
      }

      public synchronized void printB() throws InterruptedException {
          while(idx <size && !arr[idx].equals("B")){
              wait();
          }
          System.out.println("Print Sequence->"+Thread.currentThread().getName()+"="+"B");
          idx++;
          notifyAll();
      }

      public synchronized void printC() throws  InterruptedException{
        while(idx < size && !arr[idx].equals("C")) {
            wait();
        }
        System.out.println("Print Sequence->"+Thread.currentThread().getName()+"="+"C");
        idx++;
        notifyAll();
      }
}
public class PrintSequenceUsingThreeThread {

    public static void main(String[] args) {
        PrintSequence p = new PrintSequence();

        Thread[] threads = new Thread[3];
        for(int i=0;i<3;i++){
            if( i==0){
                threads[i] = new Thread(()->{
                    try {
                        p.printA();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });
            }else if(i == 1){
                threads[i] = new Thread(()->{
                    try{
                        p.printB();
                    }catch (InterruptedException e){
                        throw new RuntimeException(e);
                    }
                });
            }else{
                threads[i] = new Thread(()->{
                    try{
                        p.printC();
                    }catch (InterruptedException e){
                        throw new RuntimeException(e);
                    }
                });
            }

        }

        for(int i=0;i<3;i++){
            threads[i].start();
        }
    }
}
