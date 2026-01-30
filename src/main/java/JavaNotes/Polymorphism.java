package JavaNotes;
// polymorphism- a method can be use or represent in many forms. these are of two type compile time - method overloading,runtime-method overriding

class MethodOverloading{

    public MethodOverloading() {

    }

    public Integer  add(Integer x,Integer y){
        return x+y;
    }
    public Integer add(Integer x,Integer y,Integer z){
        return x+y+z;
    }

    public Double add(Double x,Double y){
        return x+y;
    }
}

// method overriding

class Trader{

    public void execute(){
        System.out.println("Trader execute the trades!");
    }

    public Number getValue(){
        return 100;
    }
}

class AlgoTrader extends Trader{

    @Override
    public void execute(){
        System.out.println("AlgoTrader execute the trades!");
    }

    @Override
    public Integer getValue(){
        return 200;
    }
}
public class Polymorphism {

    public static void main(String[] args) {
          MethodOverloading obj1 = new MethodOverloading();
        System.out.println(obj1.add(2,3));
        System.out.println(obj1.add(2,3,6));
        System.out.println(obj1.add(5.0,3.0));
        // method overriding
        System.out.println("===Method Overriding===");

        Trader td1 = new Trader();
        td1.execute();
        AlgoTrader aTd1 = new AlgoTrader();
        aTd1.execute();
        System.out.println("AlgoTrader value:"+aTd1.getValue());

    }
}
