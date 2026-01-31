package MockTest.fatory;

public class SmsNotification implements Notification{
    @Override
    public void notify(String message) {
        System.out.println("SMS Notification!"+message);
    }
}
