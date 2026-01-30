package JavaNotes.MultiThreading;


class Table1{

    public synchronized void print(int n) {
        System.out.println("Start!");
        for (int i = 1; i <= 5; i++) {
            System.out.println(i);
        }
        System.out.println("Complete!");
    }

}

class MyClass1 extends Thread{

    public void run(){
        Table1 t = new Table1();
        t.print(5);
    }
}
public class SynchronizedMockTest {

    public static void main(String[] args) {

        MyClass1 t1 = new MyClass1();
        MyClass1 t2 = new MyClass1();
        t1.start();
        t2.start();
    }
}
