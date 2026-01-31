package MockTest.observer;

public class PhoneUser implements Observer{

    private final String name;
    public PhoneUser(String name){
        this.name = name;
    }

    @Override
    public void update(String temperature, String humidity) {
        System.out.println("Phone user has received the update"+name+","+temperature+","+humidity);
    }
}
