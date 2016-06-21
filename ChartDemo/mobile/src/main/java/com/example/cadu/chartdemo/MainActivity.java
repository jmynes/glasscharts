package com.example.cadu.chartdemo;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private BarChart barChart;
    private BarDataSet barDataSet;
    private ArrayList<String> months;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.createUiElements();
    }

    private void createUiElements(){
        this.barChart = (BarChart) findViewById(R.id.mainBarChart);
        this.months = new ArrayList<>();
        months.add("January");
        months.add("February");
        months.add("March");
        months.add("April");
        months.add("May");
        months.add("June");
        months.add("July");
        months.add("August");
        months.add("September");
        months.add("October");
        months.add("November");
        months.add("December");
        this.configureUiElements();
        this.setData(12, 50);
    }

    private void configureUiElements(){
        this.barChart.setDrawBarShadow(false);
        this.barChart.setDrawValueAboveBar(true);
        this.barChart.setDescription("This is a description");
        this.barChart.setPinchZoom(false);
        this.barChart.setMaxVisibleValueCount(60);
        this.barChart.setDrawGridBackground(false);
    }

    private void setData(int count, float range) {

        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            xVals.add(months.get(i % 12));
        }

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        for (int i = 0; i < count; i++) {
            float mult = (range + 1);
            float val = (float) (Math.random() * mult);
            yVals1.add(new BarEntry(val, i));
        }

        if (barChart.getData() != null &&
                barChart.getData().getDataSetCount() > 0) {
            this.barDataSet = (BarDataSet)barChart.getData().getDataSetByIndex(0);
            this.barDataSet.setYVals(yVals1);
            barChart.getData().setXVals(xVals);
            barChart.getData().notifyDataChanged();
            barChart.notifyDataSetChanged();
        } else {
            this.barDataSet = new BarDataSet(yVals1, "DataSet");
            this.barDataSet.setBarSpacePercent(35f);
            this.barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add( this.barDataSet);

            BarData data = new BarData(xVals, dataSets);
            data.setValueTextSize(10f);

            barChart.setData(data);
        }
    }
}
