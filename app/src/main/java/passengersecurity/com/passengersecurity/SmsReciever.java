package passengersecurity.com.passengersecurity;

/**
 * Created by ujjwal on 30/3/18.
 */

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

import passengersecurity.com.passengersecurity.Database.ComplaintContract;

import static android.provider.Telephony.Sms.Intents.getMessagesFromIntent;


public class SmsReciever extends BroadcastReceiver {
    Context context;
    //    String[] getSplitStrings = null;
    ContentValues contentValues;
    private HashMap<String, String> map;

    public void onReceive(Context context, Intent intent) {
        this.context = context;

        String checkPnr = "no";

        SmsMessage[] msgs = getMessagesFromIntent(intent);
//        if (msgs[0].getOriginatingAddress().equalsIgnoreCase("MD-IRCTCi")
//                || msgs[0].getOriginatingAddress().equalsIgnoreCase("LM-IRCTCi")
//                || msgs[0].getOriginatingAddress().equalsIgnoreCase("DT-IRCTCi")
//                || msgs[0].getOriginatingAddress().equalsIgnoreCase("DM-IRCTCi")
//                || msgs[0].getOriginatingAddress().equalsIgnoreCase("Ujjwal")) {
//            getSplitStrings = splitMsg(msgs[0].getMessageBody());
        map = SmsReader.getInstance().splitMsg(msgs[0].getMessageBody());
        contentValues = new ContentValues();
        contentValues.put(ComplaintContract.PnrLog.COLUMN_PNR, map.get(StringUtil.SMS_KEY_PNR));
        contentValues.put(ComplaintContract.PnrLog.COLUMN_NAME, map.get(StringUtil.SMS_KEY_NAME));
        contentValues.put(ComplaintContract.PnrLog.COLUMN_TRAIN_NO, map.get(StringUtil.SMS_KEY_TRAIN));
        contentValues.put(ComplaintContract.PnrLog.COLUMN_SEAT_DETAILS, map.get(StringUtil.SMS_KEY_SEAT));
        contentValues.put(ComplaintContract.PnrLog.COLUMN_DOJ, map.get(StringUtil.SMS_KEY_DOJ));
        contentValues.put(ComplaintContract.PnrLog.COLUMN_DEPT_TIME, map.get(StringUtil.SMS_KEY_DEPT_TIME));

        Log.i("data", map.toString());
        Toast.makeText(context, "DATA=" + map.toString(), Toast.LENGTH_SHORT).show();

        if (!isNetworkAvailable()) {

            contentValues.put(ComplaintContract.PnrLog.COLUMN_STATUS, checkPnr);
            context.getContentResolver().insert(ComplaintContract.PnrLog.CONTENT_URI, contentValues);
        } else {
            getjsondata();//TODO Check Pnr and enter details in database
        }

    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void getjsondata() {
        //progressDialog.show();
        StringRequest st = new StringRequest(Request.Method.GET, "https://api.railwayapi.com/v2/pnr-status/pnr/" + map.get(StringUtil.SMS_KEY_PNR) + "/apikey/2cv4xjncln/", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                get_response(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue q = Volley.newRequestQueue(context);
        st.setShouldCache(false);
        q.add(st);
    }

    public void get_response(String response_obj) {
        try {
            JSONObject obj = new JSONObject(response_obj);

            String res = obj.getString("response_code");
            String c_prep = obj.getString("chart_prepared");
            if (res.equals("200")) {
                contentValues.put(ComplaintContract.PnrLog.COLUMN_STATUS, "yes");
                context.getContentResolver().insert(ComplaintContract.PnrLog.CONTENT_URI, contentValues);
                JSONArray arr = obj.getJSONArray("passengers");
                JSONObject pos = arr.getJSONObject(0);

                if (c_prep.equals("true")) {
                    String mydata = "Status=" + pos.getString("current_status");
                } else {
                    String mydata = "Status=" + pos.getString("booking_status");
                }
            }
        } catch (Exception r) {
            //Toast.makeText(this, "Error=" + r, Toast.LENGTH_SHORT).show();
        }
    }
}
