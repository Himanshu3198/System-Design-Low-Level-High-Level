package MockTest.observer;

public class Demo {
    public static void main(String[] args) {

        WeatherStation w = new WeatherStation();
        DisplayUser d1 = new DisplayUser("d1");
        DisplayUser d2 = new DisplayUser("d2");
        PhoneUser p1 = new PhoneUser("p1");
        PhoneUser p2 = new PhoneUser("p2");

        w.subscribe(d1);
        w.subscribe(d2);
        w.subscribe(p1);
        w.subscribe(p2);

        w.setWeather("52C","100F");


    }
}
