package passengersecurity.com.passengersecurity;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class WebServices {

    private static final String TAG = "WebService";
    private static WebServices INSTANCE;
    private Context context;
    private String key;


    private RequestQueue queue;

    private WebServices(Context context) {
        this.context = context;
        queue = Volley.newRequestQueue(context);
    }

    private WebServices() {
    }

    public static WebServices getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new WebServices(context);
        }
        return INSTANCE;
    }

    public static WebServices getInstance() {
        return INSTANCE;
    }

    public void processRequest(StringRequest request) throws AuthFailureError {
        request.setShouldCache(false);
        queue.add(request);
    }

}