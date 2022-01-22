package com.example.stormy.dataModels;

import android.annotation.SuppressLint;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Minute implements Serializable {

    private double temperature;
    private double precipChance;
    private Long time;
    private int rainChances;
    private int icon;
    private String timeZone;

    public Minute() {
    }

    public Minute(double temperature, double precipChance, Long time, int rainChances, int icon, String timeZone) {
        this.temperature = temperature;
        this.precipChance = precipChance;
        this.time = time;
        this.rainChances = rainChances;
        this.icon = icon;
        this.timeZone = timeZone;
    }

    public int getTemperature() {
        return (int)Math.round(temperature);
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getPrecipChance() {
        return precipChance;
    }

    public void setPrecipChance(double precipChance) {
        this.precipChance = precipChance;
    }

    public String getTime() {

        @SuppressLint("SimpleDateFormat") SimpleDateFormat formmat = new SimpleDateFormat("h:mm a");
        formmat.setTimeZone(TimeZone.getTimeZone(timeZone));
        Date datetime = new Date (time * 1000);
        return formmat.format(datetime);

    }

    public void setTime(Long time) {
        this.time = time;
    }

    public int getRainChances() {
        return rainChances;
    }

    public void setRainChances(int rainChances) {
        this.rainChances = rainChances;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }
}
