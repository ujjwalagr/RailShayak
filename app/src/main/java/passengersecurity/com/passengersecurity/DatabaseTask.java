package passengersecurity.com.passengersecurity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import java.util.HashMap;

import passengersecurity.com.passengersecurity.Database.ComplaintContract;

/**
 * Created by ujjwal on 30/3/18.
 */

@SuppressLint("StaticFieldLeak")
public class DatabaseTask extends AsyncTask {

    private Context context;

    public void DatabaseTask(Context context) {
        this.context = context;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        Cursor cursor = context.getContentResolver().query(Uri.parse("content://sms/inbox"), null, null, null, null);

        if (cursor.moveToFirst()) { // must check the result to prevent exception
            do {
                String[] msgs = {cursor.getString(cursor.getColumnIndex("address")), cursor.getString(cursor.getColumnIndex("body"))};
                if (msgs[0].equals("MD-IRCTCi")
                        || msgs[0].equals("LM-IRCTCi")
                        || msgs[0].equals("DT-IRCTCi")
                        || msgs[0].equals("DM-IRCTCi")
                        || msgs[0].equals("911")) {
//                        String[] messageData = splitMsg(msgs[1]);
                    HashMap<String, String> map = SmsReader.getInstance().splitMsg(msgs[0]);
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(ComplaintContract.PnrLog.COLUMN_PNR, map.get(StringUtil.SMS_KEY_PNR));
                    contentValues.put(ComplaintContract.PnrLog.COLUMN_NAME, map.get(StringUtil.SMS_KEY_NAME));
                    contentValues.put(ComplaintContract.PnrLog.COLUMN_TRAIN_NO, map.get(StringUtil.SMS_KEY_TRAIN));
                    contentValues.put(ComplaintContract.PnrLog.COLUMN_SEAT_DETAILS, map.get(StringUtil.SMS_KEY_SEAT));
                    contentValues.put(ComplaintContract.PnrLog.COLUMN_DOJ, map.get(StringUtil.SMS_KEY_DOJ));
                    contentValues.put(ComplaintContract.PnrLog.COLUMN_DEPT_TIME, map.get(StringUtil.SMS_KEY_DEPT_TIME));

                    context.getContentResolver().insert(ComplaintContract.PnrLog.CONTENT_URI, contentValues);
                }
            } while (cursor.moveToNext());
        } else {
            // empty box, no SMS
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        Cursor cursor = context.getContentResolver().query(ComplaintContract.PnrLog.CONTENT_URI, null, null, null, null);
        cursor.moveToFirst();
        //Log.i("PNR", cursor.getString(cursor.getColumnIndex("pnr")));
    }
}
