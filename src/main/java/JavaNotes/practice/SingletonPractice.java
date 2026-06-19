package JavaNotes.practice;

class Singleton{

    private static volatile Singleton instance;

    public static Singleton getInstance(){

        if(instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }

    public String hello(){
        return "hello";
    }
}
public class SingletonPractice {

    public static void main(String[] args) {
        System.out.println("Singleton practice");

        Singleton s1 = Singleton.getInstance();
        Singleton s2 = Singleton.getInstance();

        if(s1.equals(s2)){
            System.out.println("they are equal");
        }else{
            System.out.println("no they are not equal");
        }
    }
}
