package gdp.glassdatapresentation.entity;

/**
 * Created by cadu on 07/07/2016.
 */
public enum DataScope {
    HOUR ("Hour"),
    WEEK ("Week"),
    MONTH ("Month");

    private final String text;

    private DataScope(String text){
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
