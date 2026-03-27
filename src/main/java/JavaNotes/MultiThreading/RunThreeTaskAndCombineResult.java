package JavaNotes.MultiThreading;

import java.util.concurrent.CompletableFuture;

public class RunThreeTaskAndCombineResult {
    public static void main(String[] args) {

        CompletableFuture<String> task1 = CompletableFuture.supplyAsync(()->getService("ServiceA"));
        CompletableFuture<String> task2 = CompletableFuture.supplyAsync(()->getService("ServiceB"));
        CompletableFuture<String> task3 = CompletableFuture.supplyAsync(()->getService("ServiceC"));

        CompletableFuture<String> combine = CompletableFuture.allOf(task1,task2,task3).
                thenApply(voidResult->{
                    String result1 = task1.join();
                    String result2 = task2.join();
                    String result3 = task3.join();

                    return result1+"|"+result2+"|"+result3;
                });
        combine.thenAccept(System.out::println).join();

    }

    private static String getService(String service){
        try{
            System.out.println("service"+service+" running");
            Thread.sleep(2000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return "@_"+service+"_@";
    }
}
