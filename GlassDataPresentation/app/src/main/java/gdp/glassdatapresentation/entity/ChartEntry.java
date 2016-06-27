package gdp.glassdatapresentation.entity;

import java.util.Date;

/**
 * Created by cadu on 24/06/2016.
 */
public class ChartEntry {

    private String device;
    private double temperature;
    private double pressure;
    private double humidity;
    private Date datetime;

    public ChartEntry(){}

    public ChartEntry(String device, double temperature, double pressure, double humidity, Date datetime){
        this.device = device;
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
        this.datetime = datetime;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public Date getTimestamp() {
        return datetime;
    }

    public void setTimestamp(Date datetime) {
        this.datetime = datetime;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
}
