package com.example.nguyet.moneymanager3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by tk04w on 12/19/2017.
 */



public class ViewChart extends AppCompatActivity {
    BarChart bc;
    String nam;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    int monthsData[]={0,0,0,0,0,0,0,0,0,0,0,0,0};

    public void setChart() {
        bc.setDrawBarShadow(false);
        bc.setDrawValueAboveBar(true);
        bc.setMaxVisibleValueCount(50);
        bc.setPinchZoom(false);
        bc.setDrawGridBackground(true);
        final ArrayList<BarEntry> barEntries = new ArrayList<>();
        myRef.child("bieudo").child(nam).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data1: dataSnapshot.getChildren())
                {
                    monthsData[Integer.parseInt(data1.getKey().toString())]=data1.getValue(Integer.class);
                }
                for (int i = 1; i < 13; i++) {
                    barEntries.add(new BarEntry(i, monthsData[i]));
                }
                BarDataSet barDataSet = new BarDataSet(barEntries, "Thu - Chi");
                barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                BarData data = new BarData(barDataSet);
                bc.setData(data);
                data.setBarWidth(1);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_chart);
        bc = (BarChart) findViewById(R.id.barchart);
        Intent i = getIntent();
        nam = i.getStringExtra("Year");
        setChart();
    }
}
