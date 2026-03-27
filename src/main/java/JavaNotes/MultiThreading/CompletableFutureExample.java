package JavaNotes.MultiThreading;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureExample {

    public static void main(String[] args) {
        String userId = "himash123";

        CompletableFuture<Void> future = CompletableFuture.supplyAsync(()->getUser(userId)).
                thenApply(user->user.toUpperCase()).
                thenApply(s->"Hello"+s).
                thenAccept(System.out::println);
        future.join();
    }
    private static String getUser(String userId){
        try{
            System.out.println("Fetching the user..");
            Thread.sleep(50);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return "__"+userId;
    }
}
