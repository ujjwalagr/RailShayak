package passengersecurity.com.passengersecurity;

/**
 * Created by ujjwal on 30/3/18.
 */

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.util.Log;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.util.Log;


public class MyPhoneStateListener extends PhoneStateListener {
    int mSignalStrength, prevSignalStrength = 0;
    Context mContext;

    public MyPhoneStateListener(Context context) {
        mContext = context;
    }

    @Override
    public void onSignalStrengthsChanged(SignalStrength signalStrength) {
        super.onSignalStrengthsChanged(signalStrength);
        mSignalStrength = signalStrength.getGsmSignalStrength();
        Log.i("Signal Strength", String.valueOf(mSignalStrength));
    }

    public boolean getStrength() {
        if (mSignalStrength > 4) {
            return true;
        } else {
            return false;
        }
    }
}