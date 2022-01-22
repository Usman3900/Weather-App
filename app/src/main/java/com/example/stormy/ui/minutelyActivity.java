package com.example.stormy.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stormy.R;
import com.example.stormy.adapters.minutelyAdapter;
import com.example.stormy.dataModels.Minute;

import java.util.ArrayList;
import java.util.List;


public class minutelyActivity extends AppCompatActivity {

    private minutelyAdapter adapter;


    RecyclerView r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_minutely);

        Intent intent = getIntent();
        List<Minute> minutes = (ArrayList<Minute>) intent.getSerializableExtra("minutesList");

        r = findViewById(R.id.minutelyListItems);

        adapter = new minutelyAdapter(minutes, this);

            r.setAdapter(adapter);
            r.setHasFixedSize(true);
            r.setLayoutManager(new LinearLayoutManager(this));
            r.addItemDecoration(new DividerItemDecoration(r.getContext(), DividerItemDecoration.VERTICAL));


    }


}