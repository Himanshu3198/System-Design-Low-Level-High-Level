package MockTest.observer;

public class DisplayUser implements Observer{
    private final String name;
    public DisplayUser(String name){
        this.name = name;
    }
    @Override
    public void update(String temperature, String humidity) {

        System.out.println("Display users received the update "+name+","+temperature+","+humidity);
    }
}
