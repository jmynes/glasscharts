package gdp.glassdatapresentation.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;

import com.google.android.glass.touchpad.Gesture;
import com.google.android.glass.touchpad.GestureDetector;
import com.google.android.glass.widget.Slider;

import java.lang.reflect.Array;
import java.util.ArrayList;

import gdp.glassdatapresentation.R;
import gdp.glassdatapresentation.entity.Address;
import gdp.glassdatapresentation.entity.ChartEntry;
import gdp.glassdatapresentation.service.ChartFeederTask;
import gdp.glassdatapresentation.service.EntriesMonitor;

public class MainActivity extends Activity {
    // control transitioning between activities and info view
    public static boolean isActive = false;

    private GestureDetector mGestureDetector;
    private ArrayList<ChartEntry> entries;

    private final GestureDetector.BaseListener mBaseListener = new GestureDetector.BaseListener() {
        @Override
        public boolean onGesture(Gesture gesture) {
            if (gesture == Gesture.TAP) {
                openOptionsMenu();
                return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.main_layout);
        mGestureDetector = new GestureDetector(this.getApplicationContext()).setBaseListener(mBaseListener);
        this.entries = new ArrayList<ChartEntry>();
        this.getData(this.entries);
        EntriesMonitor monitor = new EntriesMonitor(this.entries);
        monitor.run();
    }

    protected void onResume(){
        super.onResume();
        isActive = true;
    }
    protected void onPause(){
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.chartsmenu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.removeItem(R.id.main_menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.pressure_chart:
                Intent pressure = new Intent(this, PressureChartActivity.class);
                if (PressureChartActivity.isActive) {
                    pressure.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                }
                pressure.putExtra("chart_data", this.entries);
                startActivity(pressure);
                return true;
            case R.id.temperature_chart:
                Intent temperature = new Intent(this, TemperatureChartActivity.class);
                if (TemperatureChartActivity.isActive) {
                    temperature.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                }
                temperature.putExtra("chart_data", this.entries);
                startActivity(temperature);
                return true;
            case R.id.humidity_chart:
                Intent humidity = new Intent(this, HumidityChartActivity.class);
                if (HumidityChartActivity.isActive) {
                    humidity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                }
                humidity.putExtra("chart_data", this.entries);
                startActivity(humidity);
                return true;
            case R.id.info:
                Intent info = new Intent(this, InfoActivity.class);
                startActivity(info);
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onGenericMotionEvent(MotionEvent event) {
        return mGestureDetector.onMotionEvent(event);
    }

    private void getData(ArrayList<ChartEntry> entries){
        ChartFeederTask hourFeeder = new ChartFeederTask(Address.HOUR_DATA.getAddress(), entries);
        ChartFeederTask yearFeeder = new ChartFeederTask(Address.YEAR_DATA.getAddress(), entries);
        ChartFeederTask monthFeeder = new ChartFeederTask(Address.MONTH_DATA.getAddress(), entries);
        hourFeeder.execute();
        monthFeeder.execute();
        yearFeeder.execute();
        while(!(hourFeeder.finished && monthFeeder.finished && yearFeeder.finished)){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
