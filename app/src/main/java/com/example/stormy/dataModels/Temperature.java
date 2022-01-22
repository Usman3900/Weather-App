package com.example.stormy.dataModels;

import com.example.stormy.R;

import java.io.Serializable;

public class Temperature implements Serializable {

    private String summary;
    private String city;
    private String country;
    private String date;
    private double precipChance;
    private double humidity;
    private String time;
    private int iconCode;
    private String icon;
    private double temperature;

    public Temperature() {
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public int getIconCode() {
        return iconCode;
    }

    public void setIconCode(int iconCode) {
        this.iconCode = iconCode;
    }

    public void setIcon(String iconString) {
        this.icon = iconString;
    }

    public Temperature(
                         String date,
                         String city,
                         String country,
                         String summary,
                         String time,
                         double humidity,
                         double precipChance,
                         int iconCode,
                         double temperature,
                         String iconString) {

        this.summary = summary;
        this.city = city;
        this.country = country;
        this.date = date;
        this.precipChance = precipChance;
        this.humidity = humidity;
        this.time = time;
        this.iconCode = iconCode;
        this.temperature = temperature;
        this.icon = iconString;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getPrecipChance() {
        return precipChance;
    }

    public void setPrecipChance(double precipChance) {
        this.precipChance = precipChance;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getIcon() {
        if(iconCode == 200)
        {
            if (icon.equals("t01d")) {
                return R.drawable.t01d;
            }
            else {
                return R.drawable.t01n;
            }
        }

        else if(iconCode == 201)
        {
            if (icon.equals("t02d")) {
                return R.drawable.t02d;
            }
            else {
                return R.drawable.t02n;
            }
        }

        else if(iconCode == 230)
        {
            if (icon.equals("t03d")) {
                return R.drawable.t03d;
            }
            else {
                return R.drawable.t03n;
            }
        }

        else if(iconCode == 231 || iconCode == 232)
        {
            if (icon.equals("t04d") || icon.equals("t05d")) {
                return R.drawable.t04d;
            }
            else {
                return R.drawable.t04n;
            }
        }

        else if(iconCode == 233)
        {
            if (icon.equals("t05d")) {
                return R.drawable.t05d;
            }
            else {
                return R.drawable.t05n;
            }
        }

        else if(iconCode == 300 || iconCode == 301 || iconCode == 302)
        {
            return R.drawable.d02d;
        }

        else if(iconCode == 500 || iconCode == 501 || iconCode == 511)
        {
            return R.drawable.f01d;
        }

        else if(iconCode == 502)
        {
            return R.drawable.r03d;
        }

        else if(iconCode == 520)
        {
            if (icon.equals("r04d")) {
                return R.drawable.r04d;
            }
            else {
                return R.drawable.r04n;
            }
        }

        else if(iconCode == 521)
        {
            if (icon.equals("r05d")) {
                return R.drawable.r05d;
            }
            else {
                return R.drawable.r05n;
            }
        }

        else if(iconCode == 522)
        {
            if (icon.equals("r06d")) {
                return R.drawable.r06d;
            }
            else {
                return R.drawable.r06n;
            }
        }

        else if(iconCode == 600)
        {
            if (icon.equals("s01d")) {
                return R.drawable.s01d;
            }
            else {
                return R.drawable.s01n;
            }
        }

        else if(iconCode == 601 || iconCode == 602)
        {

            return R.drawable.s02d;

        }

        else if(iconCode == 610)
        {
            if (icon.equals("s04d")) {
                return R.drawable.s04d;
            }
            else {
                return R.drawable.s04n;
            }
        }

        else if(iconCode == 611 || iconCode == 612)
        {

            return R.drawable.s05d;

        }

        else if(iconCode == 621)
        {
            if (icon.equals("s01d")) {
                return R.drawable.s01d;
            }
            else {
                return R.drawable.s01n;
            }
        }

        else if(iconCode == 622 || iconCode == 623)
        {
            return R.drawable.s06d;
        }

        else if(iconCode == 700 || iconCode == 711 || iconCode == 721 || iconCode == 731 || iconCode == 741 || iconCode == 751)
        {
            if(icon.contains("d")){
                return R.drawable.a04d;
            }
            else {
                return R.drawable.a04n;
            }
        }

        else if(iconCode == 800)
        {
            if (icon.equals("c01d")) {
                return R.drawable.c01d;
            }
            else {
                return R.drawable.c01n;
            }
        }

        else if(iconCode == 801 || iconCode == 802)
        {
            if (icon.contains("d")) {
                return R.drawable.c02d;
            }
            else {
                return R.drawable.c02n;
            }
        }

        else if(iconCode == 803)
        {
            if (icon.equals("c03d")) {
                return R.drawable.c03d;
            }
            else {
                return R.drawable.c03n;
            }
        }

        else if(iconCode == 804)
        {
            if (icon.equals("c04d")) {
                return R.drawable.c04d;
            }
            else {
                return R.drawable.c04n;
            }
        }

        else if(iconCode == 900)
        {
            return R.drawable.u00d;

        }

        return R.drawable.sunny;

    }

    public String getIconString() {return icon;}
}
