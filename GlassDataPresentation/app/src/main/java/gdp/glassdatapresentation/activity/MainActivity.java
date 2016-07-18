package gdp.glassdatapresentation.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;

import com.google.android.glass.touchpad.Gesture;
import com.google.android.glass.touchpad.GestureDetector;
import com.google.android.glass.view.WindowUtils;

import java.util.ArrayList;

import gdp.glassdatapresentation.R;
import gdp.glassdatapresentation.entity.Address;
import gdp.glassdatapresentation.entity.ChartEntry;
import gdp.glassdatapresentation.service.ChartFeederTask;
import gdp.glassdatapresentation.service.EntriesMonitor;

public class MainActivity extends Activity {
    // control transitioning between activities and info view
    public static boolean isActive = false;

    private boolean mVoiceMenuEnabled = true;
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

    //@Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        // Requests a voice menu on this activity.
        getWindow().requestFeature(WindowUtils.FEATURE_VOICE_COMMANDS);
        // create gesture detector
        mGestureDetector = new GestureDetector(this.getApplicationContext()).setBaseListener(mBaseListener);

        setContentView(R.layout.main_layout);

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

    //@Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        if (featureId == WindowUtils.FEATURE_VOICE_COMMANDS ||
                featureId == Window.FEATURE_OPTIONS_PANEL) {
            getMenuInflater().inflate(R.menu.chartsmenu, menu);
            return true;
        }
        // Pass through to super to setup touch menu.
        return super.onCreatePanelMenu(featureId, menu);
    }


    //@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.chartsmenu, menu);
        return true;
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.removeItem(R.id.main_menu);

        return super.onPrepareOptionsMenu(menu);
    }

    public boolean onPreparePanel(int featureId, View view, Menu menu) {
        if (featureId == WindowUtils.FEATURE_VOICE_COMMANDS) {
            // toggle this boolean on and off based on some criteria
            menu.removeItem(R.id.main_menu);

            return mVoiceMenuEnabled;
        }
        // Good practice to call through to super for other cases
        return super.onPreparePanel(featureId, view, menu);
    }

    //@Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        if (featureId == WindowUtils.FEATURE_VOICE_COMMANDS ||
                featureId == Window.FEATURE_OPTIONS_PANEL) {

            switch (item.getItemId()) {
                case R.id.pressure_chart:
                    Intent pressure = new Intent(this, PressureChartActivity.class);
                    if (PressureChartActivity.isActive) {
                        pressure.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    }
                    pressure.putExtra("chart_data", this.entries);
                    startActivity(pressure);
                    break;
                case R.id.temperature_chart:
                    Intent temperature = new Intent(this, TemperatureChartActivity.class);
                    if (TemperatureChartActivity.isActive) {
                        temperature.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    }
                    temperature.putExtra("chart_data", this.entries);
                    startActivity(temperature);
                    break;
                case R.id.humidity_chart:
                    Intent humidity = new Intent(this, HumidityChartActivity.class);
                    if (HumidityChartActivity.isActive) {
                        humidity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    }
                    humidity.putExtra("chart_data", this.entries);
                    startActivity(humidity);
                    break;
                case R.id.info:
                    Intent info = new Intent(this, InfoActivity.class);
                    startActivity(info);
                    break;
                default:
                    return true;
            }
            return true;
        }
        return super.onMenuItemSelected(featureId, item);
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
