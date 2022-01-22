package com.example.stormy.dataModels;

public class Forecast {

    private Current current;
    private Minute[] minutelyForecast;

    public Current getCurrentWeather() {
        return current;
    }

    public void setCurrentWeather(Current current) {
        this.current = current;
    }

    public Minute[] getMinutelyForecast() {
        return minutelyForecast;
    }

    public void setMinutelyForecast(Minute[] minutelyForecast) {
        this.minutelyForecast = minutelyForecast;
    }
}
