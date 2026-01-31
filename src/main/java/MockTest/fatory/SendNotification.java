package MockTest.fatory;

public class SendNotification {
    public static void main(String[] args) {
        NotificationFactory notificationFactory = new NotificationFactory();
        Notification channel =notificationFactory.getChannel("Email");
        channel.notify("Hello from Himanshu!");

    }
}
