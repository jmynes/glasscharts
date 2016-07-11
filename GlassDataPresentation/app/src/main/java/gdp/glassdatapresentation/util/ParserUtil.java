package gdp.glassdatapresentation.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import gdp.glassdatapresentation.entity.DataScope;

/**
 * Created by cadu on 27/06/2016.
 */
public class ParserUtil {

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

    public static DataScope parseDataType(String dataType){
        if (dataType.equals(DataScope.HOUR.getText())){
            return DataScope.HOUR;
        }else if (dataType.equals(DataScope.MONTH.getText())){
            return DataScope.MONTH;
        }else if(dataType.equals(DataScope.WEEK.getText())){
            return DataScope.WEEK;
        }else{
            return null;
        }
    }
}
