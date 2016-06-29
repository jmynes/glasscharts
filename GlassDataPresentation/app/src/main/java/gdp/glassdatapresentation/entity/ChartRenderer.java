package gdp.glassdatapresentation.entity;

import android.content.res.ColorStateList;
import android.graphics.Color;

import org.achartengine.chart.PointStyle;
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

    public void setRenderer() {
        this.renderer = new XYMultipleSeriesRenderer();

        this.renderer.setMargins(new int[]{30, 40, 15, 15});

        // text style
        this.renderer.setAxisTitleTextSize(16);
        this.renderer.setChartTitleTextSize(20);
        this.renderer.setLabelsTextSize(15);
        this.renderer.setLegendTextSize(15);
        this.renderer.setPointSize(5f);
        this.renderer.setChartTitle(title);
        this.renderer.setXTitle(xTitle);
        this.renderer.setYTitle(yTitle);

        // series style
        XYSeriesRenderer r;
        for (int i = 0; i < this.numSeries; i++) {
            r = new XYSeriesRenderer();
            r.setColor(colorStyles.get(i%5));
            r.setPointStyle(pointStyles.get(i%5));
            r.setFillPoints(true);
            this.renderer.addSeriesRenderer(r);
        }

        // axes style
        this.renderer.setAxesColor(Color.DKGRAY);
        this.renderer.setShowGridX(true);
        this.renderer.setShowGridY(true);
        this.renderer.setLabelsColor(Color.LTGRAY);
    }

    public XYMultipleSeriesRenderer getRenderer() {
        return  this.renderer;
    }

}
