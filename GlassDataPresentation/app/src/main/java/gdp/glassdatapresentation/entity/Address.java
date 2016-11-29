package gdp.glassdatapresentation.entity;

/**
 * Created by cadu on 08/07/2016.
 */
public enum Address {

    MONTH_DATA("http://mynes.me/glassData/getMonthData"),
    HOUR_DATA("http://mynes.me/glassData/getHourData"),
    YEAR_DATA("http://mynes.me/glassData/getYearData");

    private final String address;

    private Address(String address){
        this.address = address;
    }

    public String getAddress() {
        return address;
    }
}
