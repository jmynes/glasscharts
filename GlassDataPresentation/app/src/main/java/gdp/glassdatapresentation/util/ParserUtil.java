package gdp.glassdatapresentation.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

import gdp.glassdatapresentation.entity.ChartEntry;
import gdp.glassdatapresentation.entity.ChartRenderer;
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

    public static void attatchMonthLabels(ChartRenderer renderer){
        ArrayList<String> months = getMonths();
        for(int i = 0; i < months.size() - 1; i++){
            renderer.addXTextLabel(Double.parseDouble(String.valueOf(i)), months.get(i));
        }
    }

    private static ArrayList<String> getMonths(){
        ArrayList<String> months = new ArrayList<String>();
        months.add("January");
        months.add("February");
        months.add("March");
        months.add("April");
        months.add("May");
        months.add("June");
        months.add("July");
        months.add("August");
        months.add("September");
        months.add("October");
        months.add("November");
        months.add("December");
        return months;
    }
}
