package gdp.glassdatapresentation.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by cadu on 27/06/2016.
 */
public class DateUtil {

    public static Date parseDateFromString(String date){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date stringToDate = null;
        try {
            stringToDate = df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return stringToDate;
    }
}
