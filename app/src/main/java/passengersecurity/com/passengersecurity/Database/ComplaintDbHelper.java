package passengersecurity.com.passengersecurity.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static passengersecurity.com.passengersecurity.Database.ComplaintContract.PnrLog.COLUMN_DEPT_TIME;
import static passengersecurity.com.passengersecurity.Database.ComplaintContract.PnrLog.COLUMN_DOJ;
import static passengersecurity.com.passengersecurity.Database.ComplaintContract.PnrLog.COLUMN_NAME;
import static passengersecurity.com.passengersecurity.Database.ComplaintContract.PnrLog.COLUMN_PNR;
import static passengersecurity.com.passengersecurity.Database.ComplaintContract.PnrLog.COLUMN_SEAT_DETAILS;
import static passengersecurity.com.passengersecurity.Database.ComplaintContract.PnrLog.COLUMN_STATUS;
import static passengersecurity.com.passengersecurity.Database.ComplaintContract.PnrLog.COLUMN_TRAIN_NO;
import static passengersecurity.com.passengersecurity.Database.ComplaintContract.PnrLog.TABLE_NAME;
import static passengersecurity.com.passengersecurity.Database.ComplaintContract.PnrLog._ID;


public class ComplaintDbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "complaint.db";

    public ComplaintDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SQL_CREATE_PNR_LOG_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_PNR + " TEXT NOT NULL UNIQUE," +
                COLUMN_NAME + " TEXT NOT NULL," +
                COLUMN_TRAIN_NO + " INTEGER NOT NULL," +
                COLUMN_SEAT_DETAILS + " TEXT," +
                COLUMN_DOJ + " TEXT," +
                COLUMN_DEPT_TIME + " TEXT," +
                COLUMN_STATUS + " TEXT);";

        String SQL_CREATE_SMS_DELIVERY_TABLE = "CREATE TABLE " + ComplaintContract.SmsDelivery.TABLE_NAME + "(" +
                ComplaintContract.SmsDelivery._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ComplaintContract.SmsDelivery.COLUMN_MSG_BODY + " TEXT NOT NULL, " +
                ComplaintContract.SmsDelivery.COLUMN_DELIVERY_REPORT + " TEXT);";

        sqLiteDatabase.execSQL(SQL_CREATE_PNR_LOG_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_SMS_DELIVERY_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
