package MockTest.singleton;

public class DesignSingleton {

    // directly doing double locking
    private static volatile DesignSingleton instance;

    private DesignSingleton(){}

    public static DesignSingleton getInstance(){

             if(instance == null){
                 synchronized (DesignSingleton.class){
                     if(instance == null){
                         instance = new DesignSingleton();
                     }
                 }
             }
             return instance;
    }

    public void  greet(){
        System.out.println("Hello for Singleton");
    }
}
