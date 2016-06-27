package gdp.glassdatapresentation.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import org.achartengine.chart.PointStyle;
import org.achartengine.chart.TimeChart;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import gdp.glassdatapresentation.R;

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
public class HumidityChartActivity extends Activity {

    // number of data series to be plotted
    private static final int SERIES_NR = 3;

    // litener to process recognized gestures
    private GestureDetector mGestureDetector;

    // to handle the Cardscroller views 
    private List<GraphicalView> cViews;
    private CardScrollView mCardScrollView;
    private ChartViewCardScrollAdapter mAdapter;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        createChartViews();

        mCardScrollView = new CardScrollView(this);
        mAdapter = new ChartViewCardScrollAdapter();
        mCardScrollView.setAdapter(mAdapter);
        mCardScrollView.activate();

        // Handle the TAP and SCROLL UP events.
        mGestureDetector = createGestureDetector(this);

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
        mCardScrollView.activate();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mCardScrollView.deactivate();
        super.onPause();
    }

    /**
     * Build a graphical view of the charts
     */
    private GraphicalView buildView(char chartType) {
        GraphicalView gView = null;
        XYMultipleSeriesRenderer renderer = null;

        switch (chartType){
            case 'd':
                // day data
                gView = ChartFactory.getLineChartView(this, getDemoDataset(), getDemoRenderer("Humidity of the Day", "Time of Day", "Humidity (%)"));
                break;
            case 'w':
                // week data
                gView = ChartFactory.getLineChartView(this, getDemoDataset(), getDemoRenderer("Humidity by Week", "Week Date", "Humidity (%)"));
                break;
            case 'm':
                //month data
                gView = ChartFactory.getLineChartView(this, getDemoDataset(), getDemoRenderer("Humidity by Month", "Month", "Humidity (%)"));
                break;
            case 't':
                // time chart
                gView = ChartFactory.getTimeChartView(this, getDateDemoDataset(), getDemoRenderer("Time Chart", "x Values", "Humidity (%)"), null);
                break;
        }
        return gView;
    }

    /**
     * Generates Datasets to be added to a chart
     */
    private XYMultipleSeriesDataset getDemoDataset() {
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        final int nr = 10;
        Random r = new Random();
        for (int i = 0; i < SERIES_NR; i++) {
            XYSeries series = new XYSeries("Demo series " + (i + 1));
            for (int k = 0; k < nr; k++) {
                series.add(k, 20 + r.nextInt() % 100);
            }
            dataset.addSeries(series);
        }
        return dataset;
    }

    private XYMultipleSeriesDataset getDateDemoDataset() {
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        final int nr = 10;
        long value = new Date().getTime() - 3 * TimeChart.DAY;
        Random r = new Random();
        for (int i = 0; i < SERIES_NR; i++) {
            TimeSeries series = new TimeSeries("Demo series " + (i + 1));
            for (int k = 0; k < nr; k++) {
                series.add(new Date(value + k * TimeChart.DAY / 4), 20 + r.nextInt() % 100);
            }
            dataset.addSeries(series);
        }
        return dataset;
    }
    private XYMultipleSeriesDataset getBarDemoDataset() {
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        final int nr = 10;
        Random r = new Random();
        for (int i = 0; i < SERIES_NR; i++) {
            CategorySeries series = new CategorySeries("Demo series " + (i + 1));
            for (int k = 0; k < nr; k++) {
                series.add(100 + r.nextInt() % 100);
            }
            dataset.addSeries(series.toXYSeries());
        }
        return dataset;
    }

    /**
     * Generates the chart appearence
     */
    private XYMultipleSeriesRenderer getDemoRenderer(String title, String xTitle, String yTitle) {
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();

        renderer.setMargins(new int[]{30, 40, 15, 15});

        // text style
        renderer.setAxisTitleTextSize(16);
        renderer.setChartTitleTextSize(20);
        renderer.setLabelsTextSize(15);
        renderer.setLegendTextSize(15);
        renderer.setPointSize(5f);
        renderer.setChartTitle(title);
        renderer.setXTitle(xTitle);
        renderer.setYTitle(yTitle);

        // series style
        XYSeriesRenderer r = new XYSeriesRenderer();
        r.setColor(Color.BLUE);
        r.setPointStyle(PointStyle.SQUARE);
        r.setFillPoints(true);
        renderer.addSeriesRenderer(r);
        r = new XYSeriesRenderer();
        r.setPointStyle(PointStyle.CIRCLE);
        r.setColor(Color.GREEN);
        r.setFillPoints(true);
        renderer.addSeriesRenderer(r);
        r = new XYSeriesRenderer();
        r.setPointStyle(PointStyle.DIAMOND);
        r.setColor(Color.MAGENTA);
        r.setFillPoints(true);
        renderer.addSeriesRenderer(r);

        // axes style
        renderer.setAxesColor(Color.DKGRAY);
        renderer.setShowGridX(true);
        renderer.setShowGridY(true);
        renderer.setLabelsColor(Color.LTGRAY);

        return renderer;
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
        GestureDetector gestureDetector = new GestureDetector(context);
        //Create a base listener for generic gestures
        gestureDetector.setBaseListener(new GestureDetector.BaseListener() {
            @Override
            public boolean onGesture(Gesture gesture) {
                if (gesture == Gesture.TAP) {
                    openOptionsMenu();
                    //startActivity(new Intent(context, MainActivity.class));
                    return true;
                } else if (gesture == Gesture.SWIPE_UP) {
                    startActivity(new Intent(context, PressureChartActivity.class));
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
        menu.removeItem(R.id.main_menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.pressure_chart:
                startActivity(new Intent(this, PressureChartActivity.class));
                return true;
            case R.id.temperature_chart:
                startActivity(new Intent(this, TemperatureChartActivity.class));
                return true;
            case R.id.humidity_chart:
                startActivity(new Intent(this, HumidityChartActivity.class));
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }
}
