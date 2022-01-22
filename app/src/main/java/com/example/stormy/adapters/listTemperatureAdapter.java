package com.example.stormy.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stormy.R;
import com.example.stormy.database.temperatureDB;
import com.example.stormy.ui.SavedTemperatures;
import com.example.stormy.dataModels.Temperature;
import com.example.stormy.ui.ShowSavedTemperature;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;


public class listTemperatureAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> listTitle;
    private HashMap<String, List<Temperature>> listDetail;
    private temperatureDB database;

    public listTemperatureAdapter(Context context, List<String> listTitle, HashMap<String, List<Temperature>> listDetail) {
        this.context = context;
        this.listTitle = listTitle;
        this.listDetail = listDetail;
    }

    @Override
    public int getGroupCount() {
        return this.listTitle.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return this.listDetail.get(this.listTitle.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return this.listTitle.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return this.listDetail.get(this.listTitle.get(i)).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_group, parent, false);
        TextView tv1 = convertView.findViewById(R.id.lg1);
        String sGroup = String.valueOf(getGroup(groupPosition));
        tv1.setText(sGroup);
        tv1.setTypeface(null, Typeface.BOLD);
        tv1.setTextColor(Color.WHITE);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.temperature_list, parent, false);
        TextView cityName = convertView.findViewById(R.id.cityName);
        TextView date = convertView.findViewById(R.id.date);
        ImageView delete = convertView.findViewById(R.id.delete);
        Temperature t = (Temperature) getChild(groupPosition,childPosition);
        cityName.setText(String.valueOf(t.getCity()));
        cityName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (context instanceof SavedTemperatures) {
                    ((SavedTemperatures)context).showTemperature(t);
                }

            }
        });
        date.setText(t.getDate());
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (context instanceof SavedTemperatures) {
                    ((SavedTemperatures)context).showTemperature(t);
                }
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setTitle("Confirm Deletion");
                alertDialog.setMessage(t.getDate() + ": " + t.getCity() + ", " + t.getCountry());
                alertDialog.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                alertDialog.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (context instanceof SavedTemperatures) {
                            ((SavedTemperatures)context).deleteTemperatureValue(t);
                        }
                    }
                });

                AlertDialog dialog = alertDialog.create();
                dialog.show();
            }
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }


}