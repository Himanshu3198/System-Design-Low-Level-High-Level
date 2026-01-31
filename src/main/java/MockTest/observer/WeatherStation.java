package MockTest.observer;

import java.util.ArrayList;

import java.util.List;

public class WeatherStation implements Subject{

    private List<Observer> observers;
    private String temperature;
    private String humidity;

    public WeatherStation(){
        this.observers = new ArrayList<>();
    }
    @Override
    public void subscribe(Observer o) {
        observers.add(o);
    }

    @Override
    public void unsubscribe(Observer o) {
       observers.remove(o);
    }

    @Override
    public void notifyObserver() {

        for(Observer o:observers){
            o.update(temperature,humidity);
        }
    }

    public void setWeather(String temperature,String humidity){
        this.temperature = temperature;
        this.humidity = humidity;
        notifyObserver();
    }
}
