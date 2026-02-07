package JavaNotes.classandobject;


abstract class  Payment{
    protected double amount;
    Payment(double amount){
        this.amount = amount;
    }
    abstract void pay();

    void printReceipt(){
        System.out.println("Amount has been processed "+amount);
    }
}

class UPIPay extends Payment{

    UPIPay(double amount){
        super(amount);
    }

    @Override
    void pay(){
        System.out.println("paid by UPI");
    }
}

class CardPay extends  Payment{
    CardPay(double amount){
        super(amount);
    }

    @Override
    void pay(){
        System.out.println("paid by Card");
    }
}
public class AbstractClassDemo {

    public static void main(String[] args) {

        Payment p1 = new UPIPay(500.0);
        Payment p2 = new CardPay(100.0);

        p1.pay();
        p1.printReceipt();
        p2.pay();
        p2.printReceipt();
    }
}
