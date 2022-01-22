package com.example.stormy.ui;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.stormy.R;

import com.example.stormy.database.temperatureDB;
import com.example.stormy.dataModels.Current;
import com.example.stormy.dataModels.Forecast;
import com.example.stormy.dataModels.Minute;
import com.example.stormy.dataModels.Temperature;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener, AdapterView.OnItemSelectedListener {

    TextView t, temperatureValue, locationValue,
            timeValue, humidityValue, precipValue,
            summaryValue, stack;
    Forecast forecast;
    ImageView iconImageView;
    ImageView refresh;
    int ImageIcon;
    private Current current;
    Spinner spinner;
    private temperatureDB database;
    static final int READ_BLOCK_SIZE = 100;
    public String city = "Raleigh,US";
    private String CHANNEL_ID = "channel_id";

    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = new temperatureDB(this);
        temperatureValue = findViewById(R.id.temperatureValue);
        locationValue = findViewById(R.id.locationValue);
        timeValue = findViewById(R.id.timeValue);
        humidityValue = findViewById(R.id.humidityValue);
        precipValue = findViewById(R.id.precipValue);
        summaryValue = findViewById(R.id.summaryValue);
        stack = findViewById(R.id.weatherStack);
        iconImageView = findViewById(R.id.iconImageView);
        refresh = findViewById(R.id.imageView);

        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_list);
        spinner.setAdapter(adapter);


        refresh.setClickable(true);

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Refreshing", Toast.LENGTH_SHORT).show();
                forcastWeather(city);
            }
        });

        stack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.weatherbit.io";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });


        forcastWeather("Raleigh,NC");
    }

    private void forcastWeather(String city) {

        String apiKey = "1fccee6484ff462e82a6dd805122142a";

        boolean x = isNetworkAvailable();

        if(x) {
            String check = "https://api.weatherbit.io/v2.0/current?&city=" +
                    city + "&key=" + apiKey + "&include=minutely";

           // Log.d(TAG, check);
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(check)
                    .build();
            //Log.d(TAG, "OK Http is called");

            Call call = client.newCall(request);

            call.enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    alertUserAboutError();
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                    String data = response.body().string();
                    //Log.d(TAG, data);

                    try {
                        forecast = getforecastdata(data);

                        current = forecast.getCurrentWeather();
                        //Log.d(TAG, String.valueOf(current.getTemperature()));


                        //Glide.with(MainActivity.this).load("YourUrl").into(iconImageView);

                                setWeatherOnScreen();


                    }
                    catch (Exception e)
                    {
                        Log.e("MainActivity", String.valueOf(e));
                    }



                    if (data.isEmpty()) {
                        alertUserAboutError();
                    }
                }
            });
        }

        else
        {
            ShowDialogue();
            showSavedTemperature();
        }
    }

    private void setWeatherOnScreen() {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                temperatureValue.setText(String.valueOf(Math.round(current.getTemperature())));
                locationValue.setText(String.valueOf(current.getCity()) + ", " + String.valueOf(current.getCountry()));
                timeValue.setText("At " + String.valueOf(current.getFormattedTime()) + ", it will be");
                humidityValue.setText(String.valueOf((int) current.getHumidity()));
                precipValue.setText(String.valueOf(Math.round(current.getPrecipChance())) + "%");
                summaryValue.setText(String.valueOf(current.getSummary()));
                iconImageView.setImageResource(current.getIcon());
            }
        });

    }

    private Forecast getforecastdata(String data) throws Exception
    {
        Forecast forecast = new Forecast();
        forecast.setCurrentWeather(getCurrentDetails(data));
        forecast.setMinutelyForecast(getMinutelyDetails(data));
        return forecast;
    }

    private Minute[] getMinutelyDetails(String data) throws Exception
    {
        JSONObject weatherData = new JSONObject(data);
        JSONArray dataWeather = weatherData.getJSONArray("data");
        JSONObject weatherData1 = new JSONObject(dataWeather.getString(0));
        String timeZone = weatherData1.getString("timezone");
        JSONArray minutelyData = weatherData.getJSONArray("minutely");
        //Log.d(TAG, "Here is to check the Data"+minutelyData.toString());
        JSONObject object1 = minutelyData.getJSONObject(0);
        //Log.d(TAG, object1.getString("temp"));
        Minute[] minutes = new Minute[minutelyData.length()];

        for (int i=0; i<minutelyData.length(); i++)
        {
            JSONObject object = minutelyData.getJSONObject(i);
            Minute m = new Minute();;
            m.setTemperature(object.getDouble("temp"));
            m.setPrecipChance(object.getDouble("precip"));
            m.setTime(object.getLong("ts"));
            m.setRainChances(object.getInt("snow"));
            m.setTimeZone(timeZone);
            m.setIcon(ImageIcon);
            minutes[i] = m;
        }
        return minutes;

    }

    private Current getCurrentDetails(String data) throws Exception{


        JSONObject weatherData = new JSONObject(data);
       // Log.d(TAG, data);

        JSONArray dataWeather = weatherData.getJSONArray("data");
        String data1 = dataWeather.getString(0);
        JSONObject weatherData1 = new JSONObject(data1);
        JSONObject weatherData2 = new JSONObject(weatherData1.getString("weather"));
        Current datar = new Current();

        datar.setTemperature(weatherData1.getDouble("temp"));
        datar.setPrecipChance(weatherData1.getDouble("precip"));
        datar.setTimeZone(weatherData1.getString("timezone"));
        datar.setCity(weatherData1.getString("city_name"));
        datar.setCountry(weatherData1.getString("country_code"));
        datar.setSummary(weatherData2.getString("description"));
        datar.setIcon(weatherData2.getString("icon"));
        datar.setIconCode(weatherData2.getInt("code"));
        datar.setTime(weatherData1.getLong("ts"));
        datar.setHumidity(weatherData1.getInt("rh"));
        String x = weatherData1.getString("datetime");
        String xx[] = x.split(":");
        datar.setDate(xx[0]);
        ImageIcon = datar.getIcon();
        return datar;
    }

    private boolean isNetworkAvailable() {

        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected())
        {
            return true;
        }


        return false;

    }

    private void ShowDialogue() {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("OOPs Sorry");
        b.setMessage("Internet not Available");
        b.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog ad = b.create();
        ad.show();
    }

    private void alertUserAboutError() {
        Toast.makeText(this, "Error Connection", Toast.LENGTH_LONG).show();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.sorry)
                .setMessage("Internet not Available!")
                .setPositiveButton("OK", null);
        AlertDialog ad = builder.create();
        ad.show();
    }

    public void minutelyClick(View view)
    {

        if(isNetworkAvailable())
        {
            List<Minute> minutes = Arrays.asList(forecast.getMinutelyForecast());
            Intent intent = new Intent(this, minutelyActivity.class);
            intent.putExtra("minutesList", (Serializable) minutes);
            intent.putExtra("ImageIcon", ImageIcon);
            startActivity(intent);
        }
        else
        {
            ShowDialogue();
        }

    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.popmenu, popup.getMenu());
        popup.show();
    }

    public void showMenu(View v) {
        PopupMenu popup = new PopupMenu(this, v);

        // This activity implements OnMenuItemClickListener
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.popmenu);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.SaveTemp:
                if(isNetworkAvailable()) {
                    boolean result = saveTemperature();
                    return true;
                }
                else {
                    ShowDialogue();
                    return false;
                }

            case R.id.retrieve:
                retrieveTemperatures();
                return true;

            default:
                return false;
        }
    }

    private void retrieveTemperatures() {

        Intent intent = new Intent(this, SavedTemperatures.class);
        startActivity(intent);
    }

    private boolean saveTemperature() {

        Temperature d = new Temperature(String.valueOf(current.getDate()),
                current.getCity(),
                current.getCountry(),
                current.getSummary(),
                current.getFormattedTime(),
                current.getHumidity(),
                current.getPrecipChance(),
                current.getIconCode(),
                current.getTemperature(),
                current.getIconString()
        );

        database.addTemperature(d);
        Toast.makeText(this, "Temperature added Successfully", Toast.LENGTH_SHORT).show();
        return true;

    }




    @Override
    protected void onPause() {
        super.onPause();
        try {
            if(isNetworkAvailable()) {

                FileOutputStream fileout = openFileOutput("weatherFile.txt", MODE_PRIVATE);
                OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
                String weatherText =
                                current.getCity() + "-" +
                                current.getCountry() + "-" +
                                current.getIconCode() + "-" +
                                current.getIconString() + "-" +
                                current.getTemperature() + "-" +
                                current.getHumidity() + "-" +
                                current.getPrecipChance() + "-" +
                                current.getTimeZone() + "-" +
                                current.getTime() + "-" +
                                current.getSummary() + "-";

                outputWriter.write(weatherText);
                outputWriter.close();
            }
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showSavedTemperature() {

        try {
            FileInputStream fileIn=openFileInput("weatherFile.txt");

            InputStreamReader InputRead= new InputStreamReader(fileIn);

            char[] inputBuffer= new char[READ_BLOCK_SIZE];
            String s = "";
            int charRead;

            while ((charRead=InputRead.read(inputBuffer))>0) {
                // char to string conversion
                String readstring=String.copyValueOf(inputBuffer,0,charRead);
                s += readstring;
            }
            InputRead.close();

            String weatherData[] = s.split("-");

            current = new Current();
            current.setCity(weatherData[0]);
            current.setCountry(weatherData[1]);
            current.setIconCode(Integer.parseInt(weatherData[2]));
            current.setIcon(weatherData[3]);
            current.setTemperature(Math.round(Double.parseDouble(weatherData[4])));

            float x = Float.parseFloat(weatherData[5]);
            current.setHumidity((int) x);
            current.setPrecipChance(Double.parseDouble(weatherData[6]));
            current.setTimeZone(weatherData[7]);
            current.setTime(Long.parseLong(weatherData[8]));
            current.setSummary(weatherData[9]);

            setWeatherOnScreen();
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        city = (String) adapterView.getItemAtPosition(i);
        Toast.makeText(MainActivity.this, "Getting Temperature", Toast.LENGTH_SHORT).show();
        forcastWeather(city);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        forcastWeather(city);
    }

}