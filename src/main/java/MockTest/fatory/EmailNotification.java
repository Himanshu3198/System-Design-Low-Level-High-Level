package MockTest.fatory;

public class EmailNotification implements  Notification{
    @Override
    public void notify(String message) {
        System.out.println("Email notification!"+message);
    }
}
