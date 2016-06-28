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

import gdp.glassdatapresentation.R;

public class MainActivity extends Activity {
    public static boolean isActive = false;

    private GestureDetector mGestureDetector;

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
                startActivity(pressure);
                return true;
            case R.id.temperature_chart:
                Intent temperature = new Intent(this, TemperatureChartActivity.class);
                if (TemperatureChartActivity.isActive) {
                    temperature.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                }
                startActivity(temperature);
                return true;
            case R.id.humidity_chart:
                Intent humidity = new Intent(this, HumidityChartActivity.class);
                if (HumidityChartActivity.isActive) {
                    humidity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                }
                startActivity(humidity);
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onGenericMotionEvent(MotionEvent event) {
        return mGestureDetector.onMotionEvent(event);
    }
}
