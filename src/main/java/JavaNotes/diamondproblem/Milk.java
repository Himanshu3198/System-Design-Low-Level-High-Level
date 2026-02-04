package JavaNotes.diamondproblem;

public class Milk implements Curd,Paneer{

    @Override
    public void print() {
        Curd.super.print();
    }

    public void printPaneer(){
        Paneer.super.print();
    }
    public static void main(String[] args) {

        Milk milk = new Milk();
        milk.print();
        milk.printPaneer();
    }

}
