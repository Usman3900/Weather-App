package com.example.stormy.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.stormy.R;
import com.example.stormy.dataModels.Temperature;

public class tempViewFragment extends Fragment {

    ImageView icon;
    TextView location, time, temperature1, humidity, precip, summary ,weatherStack;
    Temperature temperature;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_show_saved_temperature, container, false);
        icon = view.findViewById(R.id.iconImageView);
        location = view.findViewById(R.id.locationValue);
        time = view.findViewById(R.id.timeValue);
        temperature1 = view.findViewById(R.id.temperatureValue);
        humidity = view.findViewById(R.id.humidityValue);
        precip = view.findViewById(R.id.precipValue);
        summary = view.findViewById(R.id.summaryValue);
        weatherStack = view.findViewById(R.id.weatherStack);

        weatherStack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.weatherbit.io";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        ShowSavedTemperature t = (ShowSavedTemperature)getActivity();
        assert t != null;
        temperature = t.getMyData();
        icon.setImageResource(temperature.getIcon());
        location.setText(temperature.getCity() + ", " + temperature.getCountry());
        time.setText("At " + temperature.getTime() + ", it was");
        temperature1.setText(String.valueOf(Math.round(temperature.getTemperature())));
        humidity.setText(String.valueOf(temperature.getHumidity()));
        precip.setText(String.valueOf(Math.round(temperature.getHumidity())));
        summary.setText(String.valueOf(temperature.getSummary()));

        return view;


    }
}
