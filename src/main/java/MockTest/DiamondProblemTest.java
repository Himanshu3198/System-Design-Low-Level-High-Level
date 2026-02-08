package MockTest;

interface Bread{
    default void print(){
        System.out.println("I am burger not bread");
    }
}
interface Patty{
    default void print(){
        System.out.println("I am burger not Patty");
    }
}
class Burger implements Bread,Patty{

    @Override
    public void print() {
        Patty.super.print();
    }
}
public class DiamondProblemTest {
    public static void main(String[] args) {
        Burger b = new Burger();
        b.print();
    }
}
