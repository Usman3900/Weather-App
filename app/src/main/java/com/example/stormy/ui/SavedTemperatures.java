package com.example.stormy.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.stormy.R;
import com.example.stormy.adapters.listTemperatureAdapter;
import com.example.stormy.dataModels.listData;
import com.example.stormy.database.temperatureDB;
import com.example.stormy.dataModels.Temperature;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SavedTemperatures extends AppCompatActivity {

    private RecyclerView r;
    private temperatureDB database;
    private HashMap<String, List<Temperature>> eld;
    private listTemperatureAdapter cel;
    private ExpandableListView elv;
    private List<String> listTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_temperatures);

        database = new temperatureDB(this);

        elv = (ExpandableListView) findViewById(R.id.e1);

        listData l = new listData(this);

        eld = l.getData();

        listTitle = new ArrayList<String>(eld.keySet());

        cel = new listTemperatureAdapter(this,listTitle, eld);

        elv.setAdapter(cel);

    }



    public void deleteTemperatureValue(Temperature t)
    {
        database.deleteTemperature(t);
        //eld.remove(t);

        eld.get(listTitle.get(0)).remove(t);
        cel.notifyDataSetChanged();
    }


    public void showTemperature(Temperature t) {
        Intent intent = new Intent(SavedTemperatures.this, ShowSavedTemperature.class);
        intent.putExtra("showTemperature", (Serializable)t);
        startActivity(intent);
    }
}