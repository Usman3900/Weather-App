package com.example.stormy.widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.stormy.R;
import com.example.stormy.dataModels.Temperature;
import com.example.stormy.database.temperatureDB;

import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class temp extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {

            temperatureDB database = new temperatureDB(context);
            List<Temperature> temperature = database.getAllTemperatures();
            if(temperature.size()>0)
            {
                Temperature t = temperature.get(temperature.size() - 1);

                RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.temp);

                views.setTextViewText(R.id.temp, String.valueOf(Math.round(t.getTemperature())));
                views.setTextViewText(R.id.city, t.getCity() + "," + t.getCountry());
                views.setImageViewResource(R.id.image, t.getIcon());

                Intent intent = new Intent(context, temp.class);
                intent.setAction(appWidgetManager.ACTION_APPWIDGET_UPDATE);
                intent.putExtra(appWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0,
                        intent, PendingIntent.FLAG_UPDATE_CURRENT);
                views.setOnClickPendingIntent(R.id.refresh, pendingIntent);
                appWidgetManager.updateAppWidget(appWidgetId, views);
            }

        }
    }


}