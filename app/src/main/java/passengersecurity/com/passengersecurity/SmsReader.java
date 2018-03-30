package passengersecurity.com.passengersecurity;

import java.util.HashMap;

/**
 * Created by ujjwal on 30/3/18.
 */


public class SmsReader {
    private static final SmsReader ourInstance = new SmsReader();

    private SmsReader() {
    }

    public static SmsReader getInstance() {
        return ourInstance;
    }

    public HashMap<String, String> splitMsg(String msg) {
        HashMap<String, String> map = new HashMap<>();
        String pnr = null;
        String name = null;
        String train_no = null;
        String seat_details = null;
        String doj = null;
        String time = null;
        String checkcorrectmsg;
        if (msg != null) {
            String[] splitData = msg.split(",");
            checkcorrectmsg = splitData[0];
            map.put(StringUtil.SMS_KEY_PNR, splitData[0]);
            if (checkcorrectmsg.charAt(0) == 'P' && checkcorrectmsg.charAt(1) == 'N' && checkcorrectmsg
                    .charAt(2) == 'R' && checkcorrectmsg.charAt(3) == ':') {
                pnr = splitData[0].replace("PNR:", "");
                if (pnr.length() != 10 || !pnr.matches("[0-9]+"))
                    pnr = "not found";
            } else {
                pnr = "not found";

            }
            map.put(StringUtil.SMS_KEY_NAME, splitData[6].split("\\+")[0]);
            map.put(StringUtil.SMS_KEY_TRAIN, splitData[1].replace("TRAIN:", ""));
            map.put(StringUtil.SMS_KEY_SEAT, splitData[7]);
            map.put(StringUtil.SMS_KEY_DOJ, splitData[2].replace("DOJ:", ""));
            map.put(StringUtil.SMS_KEY_DEPT_TIME, splitData[3].replace("TIME:", ""));
        }
        return map;
    }
}