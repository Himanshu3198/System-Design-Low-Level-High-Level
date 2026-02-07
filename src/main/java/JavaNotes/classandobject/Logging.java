package JavaNotes.classandobject;



interface LogInterface {

    default void log(){
        System.out.println("this is a default logging");
    }

    static void print(){
        System.out.println("This cannot be changed");
    }

    void display();
}

class LogImplement implements  LogInterface{

    @Override
     public void log(){
        System.out.println("No this custom logging");
    }

    @Override
     public void display(){
        System.out.println("Show some courage");
    }
}

public class Logging{
    public static void main(String[] args) {

          LogInterface.print();
          LogImplement o1 = new LogImplement();
          o1.log();
          o1.display();


    }
}