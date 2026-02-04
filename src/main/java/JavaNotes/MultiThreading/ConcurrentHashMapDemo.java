package JavaNotes.MultiThreading;

import java.util.HashMap;
import java.util.Map;

public class ConcurrentHashMapDemo {
    public static void main(String[] args) {

        Map<Integer,Integer> map = new HashMap<>();

        // writer
        for(int  i=1;i<=400000;i++){

            int finalI = i;
            int finalI1 = i;
            new Thread(()->{
               map.put(finalI, finalI1 +1);
            }).start();
        }
         // writer
        for(int  i=2;i<=80000;i++){

            int finalI = i;
            int finalI1 = i;
            new Thread(()->{
                map.put(finalI, finalI1 +100);
            }).start();
        }

        // reader

        for(int i=1;i<=40000;i++){
            int finalI = i;
            new Thread(()->{
                map.get(finalI);
                System.out.println("reading the value from map"+map.get(finalI));
            }).start();
        }

        // writer

        for(int  i=2;i<=80000;i++){

            int finalI = i;
            int finalI1 = i;
            new Thread(()->{
                map.put(finalI, finalI1 +10);
            }).start();
        }

        // reader

        for(int i=2;i<=60000;i++){
            int finalI = i;
            new Thread(()->{
                   map.get(finalI);
                System.out.println("reading the value from map"+map.get(finalI));
            }).start();
        }


    }
}
