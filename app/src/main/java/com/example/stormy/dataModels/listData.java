package com.example.stormy.dataModels;

import android.content.Context;

import com.example.stormy.database.temperatureDB;

import java.util.HashMap;
import java.util.List;

public class listData {

    private temperatureDB database;
    public static List<Temperature> temperature;

    public listData(Context context)
    {
        database = new temperatureDB(context);
        temperature = database.getAllTemperatures();
    }

    public HashMap<String, List<Temperature>> getData(){

        HashMap<String, List<Temperature>> detailedData = new HashMap<String, List<Temperature>>();
        detailedData.put("Cities List", temperature);
        return detailedData;
    }
}

