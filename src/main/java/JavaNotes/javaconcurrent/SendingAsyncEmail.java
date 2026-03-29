package JavaNotes.javaconcurrent;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class SendingAsyncEmail {

    public static String  sendEmail(String message) throws InterruptedException {
        System.out.println("Async processing the email");
        Thread.sleep(1000);
        return message;
    }
    public static void main(String[] args) {

        List<String> emails = List.of("this is email 1","this is email 2","this is email 3","this is email 4");

        List<CompletableFuture<Void>> futures = emails.stream().
                map(email ->CompletableFuture.supplyAsync(()-> {
                            try {
                                return sendEmail(email);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }).
                        thenApply(String::toUpperCase).thenAcceptAsync(System.out::println)).toList();
        futures.forEach(CompletableFuture::join);

    }
}


//Async processing the email
//Async processing the email
//Async processing the email
//THIS IS EMAIL 1
//Async processing the email
//THIS IS EMAIL 3
//THIS IS EMAIL 2
//THIS IS EMAIL 4
