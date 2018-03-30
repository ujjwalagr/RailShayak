package passengersecurity.com.passengersecurity;

/**
 * Created by ujjwal on 30/3/18.
 */


public class StringUtil {
    public static final String SMS_KEY_PNR = "pnr";
    public static final String SMS_KEY_NAME = "name";
    public static final String SMS_KEY_TRAIN = "train";
    public static final String SMS_KEY_DOJ = "doj";
    public static final String SMS_KEY_DEPT_TIME = "time";
    public static final String SMS_KEY_SEAT = "seat";
    private static final StringUtil ourInstance = new StringUtil();


    private StringUtil() {
    }

    public static StringUtil getInstance() {
        return ourInstance;
    }


}