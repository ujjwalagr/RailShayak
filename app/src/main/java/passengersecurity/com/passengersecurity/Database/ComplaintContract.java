package passengersecurity.com.passengersecurity.Database;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by ujjwal on 30-03-2018.
 */

public class ComplaintContract {
    //TODO: Change Package Name
    //DONE TODOthing
    public static final String CONTENT_AUTHORITY = "passengersecurity.com.passengersecurity";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_PNR_LOG = "pnr";
    public static final String PATH_SMS = "sms";

    public ComplaintContract() {

    }

    public static class PnrLog implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PNR_LOG);

        public static final String CONTENT_PNR_LOG_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PNR_LOG;

        public static final String CONTENT_PNR_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PNR_LOG;

        public static final String TABLE_NAME = "pnr_log";

        public static final String _ID = BaseColumns._ID;

        public static final String COLUMN_PNR = "pnr";

        public static final String COLUMN_NAME = "name";

        public static final String COLUMN_TRAIN_NO = "train_no";

        public static final String COLUMN_SEAT_DETAILS = "seat_details";

        public static final String COLUMN_DOJ = "doj";

        public static final String COLUMN_DEPT_TIME = "dept_time";

        public static final String COLUMN_STATUS = "status";

    }

    public static class SmsDelivery implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_SMS);

        public static final String _ID = BaseColumns._ID;

        public static final String TABLE_NAME = "sms_delivery";

        public static final String COLUMN_MSG_BODY = "msg_body";

        public static final String COLUMN_DELIVERY_REPORT = "delivery_report";

    }
}
