package JavaNotes.javaconcurrent;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static java.lang.Thread.sleep;

public class ParallelProcessingAndCollection {

    public static String getUser() throws InterruptedException {
        sleep(1000);
        return "UserData";
    }

    public static String getOrders() throws InterruptedException {
        sleep(1500);
        return "OrderData";
    }

    public static String getRecommendations() throws InterruptedException {
        sleep(1200);
        return "RecommendationData";
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        long startTime =  System.currentTimeMillis();
        CompletableFuture<String> userFuture = CompletableFuture.supplyAsync(()-> {
            try {
                return getUser();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        CompletableFuture<String> orderFuture = CompletableFuture.supplyAsync(()-> {
            try {
                return getOrders();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        CompletableFuture<String> recommendationFuture = CompletableFuture.supplyAsync(()-> {
            try {
                return getRecommendations();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        // wait for all
        CompletableFuture<Void> all = CompletableFuture.allOf(userFuture,orderFuture,recommendationFuture);

        // combine result
        CompletableFuture<String> combineResult = all.thenApply(v-> {
            return "Final Response " + userFuture.join() + "|" + orderFuture.join() + "|" + recommendationFuture.join();
        });
        combineResult.thenAccept(System.out::println).join();
//        System.out.println(combineResult.get());

        long  endTime = System.currentTimeMillis();
        System.out.println("Processing complete! Time take :"+(endTime-startTime));
    }


}