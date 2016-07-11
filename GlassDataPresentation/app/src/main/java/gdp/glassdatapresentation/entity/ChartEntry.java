package gdp.glassdatapresentation.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by cadu on 24/06/2016.
 */
public class ChartEntry implements Serializable{

    private String room;
    private double avgTemperature;
    private double avgPressure;
    private double avgHumidity;
    private int time;
    private DataScope scope;

    public ChartEntry(){}

    public ChartEntry(String room, double avgTemperature, double avgPressure, double avgHumidity, int time, DataScope scope){
        this.room = room;
        this.avgTemperature = avgTemperature;
        this.avgPressure = avgPressure;
        this.avgHumidity = avgHumidity;
        this.time = time;
        this.scope = scope;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public double getHumidity() {
        return avgHumidity;
    }

    public void setHumidity(double avgHumidity) {
        this.avgHumidity = avgHumidity;
    }

    public double getPressure() {
        return avgPressure;
    }

    public void setPressure(double avgPressure) {
        this.avgPressure = avgPressure;
    }

    public double getTemperature() {
        return avgTemperature;
    }

    public void setTemperature(double avgTemperature) {
        this.avgTemperature = avgTemperature;
    }

    public DataScope getScope() {
        return scope;
    }

    public void setScope(DataScope scope) {
        this.scope = scope;
    }
}
