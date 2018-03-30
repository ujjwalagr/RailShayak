package passengersecurity.com.passengersecurity;

import android.*;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

/**
 * Created by Shivam on 30-03-2018.
 */

public class BaseActivity extends AppCompatActivity {
    private final String DELIVERED = "SMS_DELIVERED";
    private MyPhoneStateListener mPhoneStatelistener;
    private TelephonyManager mTelephonyManager;
    private SmsDeliveryReport deliveryReportReceiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GPSUtil.getInstance(getApplicationContext());
        if ((ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.READ_SMS, android.Manifest.permission.RECEIVE_SMS}, 444);
        }

        deliveryReportReceiver = new SmsDeliveryReport();
        mPhoneStatelistener = new MyPhoneStateListener(this);
        mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        mTelephonyManager.listen(mPhoneStatelistener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);


    }

    protected void onResume() {
        super.onResume();
        registerReceiver(deliveryReportReceiver, new IntentFilter(DELIVERED));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(deliveryReportReceiver);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 444:
                new DatabaseTask().execute();
        }
    }
}
