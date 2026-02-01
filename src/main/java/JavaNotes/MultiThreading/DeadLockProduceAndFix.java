package JavaNotes.MultiThreading;

//class DeadLock{
//
//    private final Object lockA = new Object();
//    private final Object lockB = new Object();
//
//    public void task1() throws InterruptedException {
//
//            synchronized (lockA) {
//
//                System.out.println(Thread.currentThread().getName() + "  task1 has acquire lockA");
//
//                Thread.sleep(100);
//
//                synchronized (lockB) {
//                    System.out.println(Thread.currentThread().getName() + "  task1 has acquire lockB");
//                }
//            }
//    }
//
//    public void task2() throws InterruptedException{
//        synchronized (lockB) {
//            System.out.println(Thread.currentThread().getName() + " task2 has acquire lockB");
//
//            Thread.sleep(100);
//            synchronized (lockA) {
//                System.out.println(Thread.currentThread().getName() + " task2 has acquire lockA");
//            }
//        }
//    }
//}

//deadlock fix

class DeadLock{

    private final Object lockA = new Object();
    private final Object lockB = new Object();

    public void task1() throws InterruptedException {

        synchronized (lockA) {

            System.out.println(Thread.currentThread().getName() + "  task1 has acquire lockA");

            Thread.sleep(100);

            synchronized (lockB) {
                System.out.println(Thread.currentThread().getName() + "  task1 has acquire lockB");
            }
        }
    }

    public void task2() throws InterruptedException{
        synchronized (lockA) {
            System.out.println(Thread.currentThread().getName() + " task2 has acquire lockA");

            Thread.sleep(100);
            synchronized (lockB) {
                System.out.println(Thread.currentThread().getName() + " task2 has acquire lockB");
            }
        }
    }
}
public class DeadLockProduceAndFix {

    public static void main(String[] args) {

        DeadLock d = new DeadLock();
        Thread t1 = new Thread(()->{
            try {
                d.task1();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread t2 = new Thread(()->{
            try{
                d.task2();
            }catch (InterruptedException e){
                throw new RuntimeException(e);
            }
        });
        t1.start();
        t2.start();
    }
}
