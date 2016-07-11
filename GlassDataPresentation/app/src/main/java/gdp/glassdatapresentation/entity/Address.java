package gdp.glassdatapresentation.entity;

/**
 * Created by cadu on 08/07/2016.
 */
public enum Address {

    MONTH_DATA("https://iit-data-presentation-server.herokuapp.com/getMonthData"),
    HOUR_DATA("https://iit-data-presentation-server.herokuapp.com/getHourData"),
    YEAR_DATA("https://iit-data-presentation-server.herokuapp.com/getYearData");

    private final String address;

    private Address(String address){
        this.address = address;
    }

    public String getAddress() {
        return address;
    }
}
