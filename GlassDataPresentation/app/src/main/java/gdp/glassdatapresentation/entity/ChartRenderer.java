package gdp.glassdatapresentation.entity;

import android.content.res.ColorStateList;
import android.graphics.Color;

import org.achartengine.chart.PointStyle;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by Cristina on 6/28/2016.
 */
public class ChartRenderer extends XYMultipleSeriesRenderer {

    private String title;
    private String xTitle;
    private String yTitle;
    private Integer numSeries;
    private XYMultipleSeriesRenderer renderer;

    private ArrayList<PointStyle> pointStyles = new ArrayList<PointStyle>(Arrays.asList(PointStyle.SQUARE, PointStyle.CIRCLE, PointStyle.DIAMOND, PointStyle.TRIANGLE, PointStyle.POINT));
    private ArrayList<Integer> colorStyles = new ArrayList<Integer>(Arrays.asList(Color.BLUE, Color.GREEN, Color.MAGENTA, Color.YELLOW, Color.WHITE));

    public ChartRenderer(){}

    public ChartRenderer(String title, String xTitle, String yTitle, Integer numSeries){
        this.title = title;
        this.xTitle = xTitle;
        this.yTitle = yTitle;
        this.numSeries = numSeries;
    }

    public void setRenderer(boolean fillLine) {
        this.renderer = new XYMultipleSeriesRenderer();

        // margins top, left, bottom, right
        this.renderer.setMargins(new int[]{35, 43, 20, 15});
        //this.renderer.setMarginsColor(Color.argb(0x00, 0xff, 0xff, 0x00)); // transparent margins - comment ot be black

        // text style
        this.renderer.setAxisTitleTextSize(20);
        this.renderer.setChartTitleTextSize(24);
        this.renderer.setLabelsTextSize(18);
        this.renderer.setLegendTextSize(18);
        this.renderer.setPointSize(5f);
        this.renderer.setChartTitle(title);
        this.renderer.setXTitle(xTitle);
        this.renderer.setYTitle(yTitle);
        this.renderer.setXRoundedLabels(false);

        // series style
        XYSeriesRenderer r;
        for (int i = 0; i < this.numSeries; i++) {
            r = new XYSeriesRenderer();
            r.setColor(colorStyles.get(i % 5));
            r.setPointStyle(pointStyles.get(i % 5));
            r.setFillPoints(true);
            if (fillLine) {
                r.setFillBelowLine(true);
                r.setFillBelowLineColor((colorStyles.get(i % 5) & 0x00FFFFFF) | 0x40000000);
            }

            this.renderer.addSeriesRenderer(r);
        }

        // axes style
        this.renderer.setShowTickMarks(true);
        this.renderer.setAxesColor(Color.WHITE);
        this.renderer.setShowGrid(true);
        this.renderer.setLabelsColor(Color.WHITE);
    }

    public void setBarRenderer() {
        this.renderer = new XYMultipleSeriesRenderer();

        // margins top, left, bottom, right
        this.renderer.setMargins(new int[]{35, 43, 20, 15});
        //this.renderer.setMarginsColor(Color.argb(0x00, 0xff, 0xff, 0x00)); // transparent margins - comment ot be black

        // text style
        this.renderer.setAxisTitleTextSize(20);
        this.renderer.setChartTitleTextSize(24);
        this.renderer.setLabelsTextSize(18);
        this.renderer.setLegendTextSize(18);
        this.renderer.setChartTitle(title);
        this.renderer.setXTitle(xTitle);
        this.renderer.setYTitle(yTitle);
        this.renderer.setShowGrid(true);
        this.renderer.setXRoundedLabels(false);

        // bar style
        this.renderer.setBarSpacing(0.5);

        // series style
        XYSeriesRenderer r;
        for (int i = 0; i < this.numSeries; i++) {
            r = new XYSeriesRenderer();
            r.setColor(colorStyles.get(i % 5));
            this.renderer.addSeriesRenderer(r);
        }

        // axes style
        this.renderer.setShowTickMarks(true);
        this.renderer.setAxesColor(Color.WHITE);
        this.renderer.setShowGrid(true);
        this.renderer.setLabelsColor(Color.WHITE);

    }


    // number of X ticks
    public void setTickX(int maxX){
        this.renderer.setXLabels(maxX);
    }

    // Y range
    public void setRangeY(double minY, double maxY) {
        this.renderer.setYAxisMin(minY);
        this.renderer.setYAxisMax(maxY);
    }

    public void setRangeX(double minX, double maxX) {
        this.renderer.setXAxisMin(minX);
        this.renderer.setXAxisMax(maxX);
    }

    public void setMonthNames() {
        String[] months = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
        // remove number labels
        renderer.setXLabels(0);

        for (int x = 1; x <= 12; x++) {
            this.renderer.addXTextLabel(x, months[x - 1]);
        }
        this.renderer.setShowCustomTextGridX(true);
    }

    public XYMultipleSeriesRenderer getRenderer() {
        return  this.renderer;
    }

}
