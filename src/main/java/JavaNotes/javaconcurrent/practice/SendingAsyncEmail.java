package JavaNotes.javaconcurrent.practice;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class SendingAsyncEmail {

    public static String sendEmail(String message) throws InterruptedException {

        try {
            System.out.println("Processing: " + message + " on " + Thread.currentThread().getName());
            Thread.sleep(2000);
            return message;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {

        List<String> emails = List.of("this is email 1","this is email 2","this is email 3","this is email 4");
        List<CompletableFuture<Void>> futures =emails.stream().map(email->
                                CompletableFuture
                                        .supplyAsync(()-> {
                                            try {
                                                return sendEmail(email);
                                            } catch (InterruptedException e) {
                                                throw new RuntimeException(e);
                                            }
                                        })
                                        .thenApply(String::toUpperCase)
                                        .thenAccept(System.out::println)
        ).toList();
        futures.forEach(CompletableFuture::join);
    }
}
