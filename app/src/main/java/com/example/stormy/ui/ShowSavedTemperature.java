package com.example.stormy.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stormy.R;
import com.example.stormy.dataModels.Temperature;

public class ShowSavedTemperature extends AppCompatActivity {

    Temperature temperature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_saved_temperature_actvity);

    }

    public Temperature getMyData() {
        Intent intent = getIntent();
        temperature = (Temperature) intent.getSerializableExtra("showTemperature");
        return temperature;
    }

}