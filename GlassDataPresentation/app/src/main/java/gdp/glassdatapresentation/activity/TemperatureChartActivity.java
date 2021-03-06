package gdp.glassdatapresentation.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.glass.touchpad.Gesture;
import com.google.android.glass.touchpad.GestureDetector;
import com.google.android.glass.widget.CardScrollAdapter;
import com.google.android.glass.widget.CardScrollView;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import gdp.glassdatapresentation.R;
import gdp.glassdatapresentation.entity.ChartEntry;
import gdp.glassdatapresentation.entity.ChartRenderer;
import gdp.glassdatapresentation.entity.DataScope;

// AchartEngine lib imports

/**
 * An {@link Activity} showing a tuggable "Hello World!" card.
 * <p>
 * The main content view is composed of a one-card {@link CardScrollView} that provides tugging
 * feedback to the user when swipe gestures are detected.
 * If your Glassware intends to intercept swipe gestures, you should set the content view directly
 * and use a {@link GestureDetector}.
 *
 * @see <a href="https://developers.google.com/glass/develop/gdk/touch">GDK Developer Guide</a>
 */
public class TemperatureChartActivity extends Activity {
    // control transitioning between activities and info view
    public static boolean isActive = false;

    // litener to process recognized gestures
    private GestureDetector mGestureDetector;

    // to handle the Cardscroller views
    private List<GraphicalView> cViews;
    private CardScrollView mCardScrollView;
    private ChartViewCardScrollAdapter mAdapter;

    private ArrayList<ChartEntry> entries;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        this.entries = (ArrayList<ChartEntry>) this.getIntent().getExtras().get("chart_data");

        createChartViews();

        mCardScrollView = new CardScrollView(this);
        mAdapter = new ChartViewCardScrollAdapter();
        mCardScrollView.setAdapter(mAdapter);
        mCardScrollView.activate();

        // Handle the TAP and SCROLL UP events.
        mGestureDetector = createGestureDetector(this.getApplicationContext());

        setContentView(mCardScrollView);
    }

    /*
    * Handles the Motion Event
    * */
    public boolean onGenericMotionEvent(MotionEvent event) {
        if (mGestureDetector != null) {
            return mGestureDetector.onMotionEvent(event);
        }
        return false;
    }

    /**
     * Call the function to Build a graphical view of the charts
     */
    private void createChartViews(){
        cViews = new ArrayList<GraphicalView>();

        cViews.add(buildView('d'));
        cViews.add(buildView('w'));
        cViews.add(buildView('m'));
    }

    @Override
    protected void onResume() {
        super.onResume();
        isActive = true;
        mCardScrollView.activate();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCardScrollView.deactivate();
    }

    /**
     * Build a graphical view of the charts
     */
    private GraphicalView buildView(char chartType) {
        GraphicalView gView = null;
        boolean fillLine = false;
        // Charts renderer
        ChartRenderer renderer;

        switch (chartType){
            case 'd':
                // day data
                renderer = new ChartRenderer("Average Temperature of the Day", "Time (h:m:s)", "Temperature (°F)", this.getUniqueRooms().size());
                renderer.setRenderer(fillLine);
                renderer.setTickX(24);
                //renderer.setRangeX(0, 24);
                gView = ChartFactory.getLineChartView(this, this.getChartData(DataScope.HOUR), renderer.getRenderer());
                break;
            case 'w':
                // week data
                renderer = new ChartRenderer("Average Temperature of the Week", "Week", "Temperature (°F)", this.getUniqueRooms().size());
                renderer.setRenderer(fillLine);
                renderer.setTickX(5);
                gView = ChartFactory.getLineChartView(this, this.getChartData(DataScope.WEEK), renderer.getRenderer());
                break;
            case 'm':
                //month data
                renderer = new ChartRenderer("Average Temperature by Month", "Month", "Temperature (°F)", this.getUniqueRooms().size());
                renderer.setRenderer(fillLine);
                renderer.setMonthNames();
                gView = ChartFactory.getLineChartView(this, this.getChartData(DataScope.MONTH), renderer.getRenderer());
                break;
        }
        return gView;
    }

    private Set<String> getUniqueRooms(){
        Set<String> rooms = new TreeSet<String>();
        for(ChartEntry ce : this.entries){
            rooms.add(ce.getRoom());
        }
        return rooms;
    }

    private XYMultipleSeriesDataset getChartData(DataScope dataScope){
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        for(String room : this.getUniqueRooms()){
            XYSeries series = new XYSeries(room);
            for(ChartEntry ce : this.entries){
                if(ce.getRoom().equals(room) && ce.getScope().getText().equals(dataScope.getText())){
                    series.add(ce.getTime(), ce.getTemperature());
                }
            }
            dataset.addSeries(series);
        }
        return dataset;
    }

    /**
     * Generates the card scroll chart views adapters
     */
    private class ChartViewCardScrollAdapter extends CardScrollAdapter {

        @Override
        public int getPosition(Object item) {
            return cViews.indexOf(item);
        }

        @Override
        public int getCount() {
            return cViews.size();
        }

        @Override
        public Object getItem(int position) {
            return cViews.get(position);
        }

        @Override
        public int getItemViewType(int position){
            return cViews.get(position).getLayerType();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return cViews.get(position);
        }
    }

    /*
    * Handle TAP and SWIPE UP events
     */
    private GestureDetector createGestureDetector(final Context context) {
        final ArrayList<ChartEntry> entries = this.entries;
        GestureDetector gestureDetector = new GestureDetector(context);
        //Create a base listener for generic gestures
        gestureDetector.setBaseListener( new GestureDetector.BaseListener() {
            @Override
            public boolean onGesture(Gesture gesture) {
                if (gesture == Gesture.TAP) {
                    openOptionsMenu();
                    return true;
                } else if (gesture == Gesture.SWIPE_UP) {
                    Intent humidity = new Intent(context, HumidityChartActivity.class);
                    if (HumidityChartActivity.isActive) {
                        humidity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    }
                    humidity.putExtra("chart_data", entries);
                    startActivity(humidity);

                    return true;
                }
                return false;
            }
        });

        return gestureDetector;
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
        menu.removeItem(R.id.temperature_chart);
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
            case R.id.humidity_chart:
                Intent humidity = new Intent(this, HumidityChartActivity.class);
                if (HumidityChartActivity.isActive) {
                    humidity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                }
                humidity.putExtra("chart_data", this.entries);
                startActivity(humidity);
                return true;
            case R.id.main_menu:
                Intent mainAct = new Intent(this, MainActivity.class);
                if (MainActivity.isActive) {
                    mainAct.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                }
                mainAct.putExtra("chart_data", this.entries);
                startActivity(mainAct);
                return true;
            case R.id.info:
                Intent infoAct = new Intent(this, InfoActivity.class);
                startActivity(infoAct);
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

}

