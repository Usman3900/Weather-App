package com.example.stormy.database;

import static android.database.sqlite.SQLiteDatabase.CONFLICT_REPLACE;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.stormy.dataModels.Temperature;

import java.util.ArrayList;
import java.util.List;

public class temperatureDB extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "temperature_db";
    private static final String TABLE_NAME = "temperatures";
    private static final String summary = "summary";
    private static final String city = "city";
    private static final String country = "country";
    private static final String date = "date";
    private static final String precipChance = "precipChance";
    private static final String humidity = "humidity";
    private static final String time = "time";
    private static final String iconCode = "iconCode";
    private static final String iconString = "iconString";
    private static final String temperature = "temperature";

    public temperatureDB(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String tableQuery = "CREATE TABLE if not EXISTS "+TABLE_NAME+
                "(" +
                date + " varchar(10),"+
                city + " TEXT, "+
                country + " TEXT, " +
                summary + " TEXT, " +
                time + " TEXT, " +
                humidity + " FLOAT, " +
                precipChance + " FLOAT, " +
                iconCode + " INT, " +
                temperature + " FLOAT, " +
                iconString + " TEXT, " +
                "PRIMARY KEY (" + date + ", " + city +")"
                + ");";



        sqLiteDatabase.execSQL(tableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
    }


    public List<Temperature> getAllTemperatures(){

        List<Temperature> TemperaturesList = new ArrayList<>();
        String query = "SELECT * from "+TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery(query,null);

        if(cursor.moveToFirst()){
            do{
                Temperature userModel = new Temperature(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getFloat(5),
                        cursor.getFloat(6),
                        cursor.getInt(7),
                        cursor.getFloat(8),
                        cursor.getString(9)
                );

                TemperaturesList.add(userModel);
            } while (cursor.moveToNext());
        }

        db.close();
        return TemperaturesList;
    }

    public void addTemperature(Temperature d) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(date, d.getDate());
        contentValues.put(city,d.getCity());
        contentValues.put(country,d.getCountry());
        contentValues.put(summary,d.getSummary());
        contentValues.put(time,d.getTime());
        contentValues.put(humidity,d.getHumidity());
        contentValues.put(precipChance,d.getPrecipChance());
        contentValues.put(iconCode,d.getIconCode());
        contentValues.put(temperature,d.getTemperature());
        contentValues.put(iconString,d.getIconString());

        try {
            db.insertWithOnConflict(TABLE_NAME, null, contentValues, CONFLICT_REPLACE);
        }
        catch (Exception ex)
        {
            Log.d("Exception Error", ex.toString());
        }

        db.close();
    }

    public void deleteTemperature(Temperature t) {

        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = "city=? AND date=?";
        String[] whereArgs = new String[] { String.valueOf(t.getCity()), String.valueOf(t.getDate()) };
        db.delete(TABLE_NAME, whereClause, whereArgs);
        db.close();

    }
}
