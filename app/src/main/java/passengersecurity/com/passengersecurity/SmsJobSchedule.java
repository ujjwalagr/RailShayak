package passengersecurity.com.passengersecurity;

/**
 * Created by ujjwal on 30/3/18.
 */

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;


import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;

import passengersecurity.com.passengersecurity.Database.ComplaintContract;

public class SmsJobSchedule extends JobService {
    TelephonyManager mTelephonyManager;
    MyPhoneStateListener mPhoneStatelistener;

    @Override
    public boolean onStartJob(JobParameters job) {
        mPhoneStatelistener = new MyPhoneStateListener(this);
        mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        mTelephonyManager.listen(mPhoneStatelistener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
        if (mPhoneStatelistener.getStrength()) {
            Cursor cursor = getApplicationContext().getContentResolver().query(ComplaintContract.SmsDelivery.CONTENT_URI, null,
                    ComplaintContract.SmsDelivery.COLUMN_DELIVERY_REPORT + "=not_sent", null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    //TODO Add logic for sms send
                    String message = cursor.getString(cursor.getColumnIndex(ComplaintContract.SmsDelivery.COLUMN_MSG_BODY));
                    cursor.moveToNext();
                }
            }
            Log.i("job", "done");
            cursor.close();
        }
        onStopJob(job);
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        //check again if any sms is left or not
       /* Cursor cursor = getApplicationContext().getContentResolver().query(ComplaintContract.SmsDelivery.CONTENT_URI, null,
                ComplaintContract.SmsDelivery.COLUMN_DELIVERY_REPORT + "=not_sent", null, null);
        if (cursor != null) {
            onStartJob(job);
        }*/

        return false;
    }
}
