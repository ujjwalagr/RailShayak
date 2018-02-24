package passengersecurity.com.passengersecurity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class Login extends AppCompatActivity {
    static String s1, s2, s3, s4;
    TextView b1;
    EditText e1,e2,e3,e4;
    SharedPreferences f;
    String mydata;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        b1=findViewById(R.id.sb);
        e1=findViewById(R.id.nametxt);
        e2=findViewById(R.id.pnrtxt);
        e3=findViewById(R.id.emailtxt);
        e4=findViewById(R.id.phonetxt);
        progressDialog = new ProgressDialog(Login.this);
        progressDialog.setMessage("Fetching Details..");
        progressDialog.setCancelable(false);
        f=getSharedPreferences("sp",MODE_PRIVATE);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                s1 = e1.getText().toString();
                s2 = e2.getText().toString();
                s3 = e3.getText().toString();
                s4 = e4.getText().toString();

                if(s1.isEmpty())
                    Toast.makeText(Login.this, "Please Enter Name", Toast.LENGTH_SHORT).show();
                else if(s2.isEmpty()||s2.length()!=10||!android.util.Patterns.PHONE.matcher(s4).matches())
                    Toast.makeText(Login.this, "Please Enter valid PNR", Toast.LENGTH_SHORT).show();
                else if(s3.isEmpty()|| !android.util.Patterns.EMAIL_ADDRESS.matcher(s3).matches())
                    Toast.makeText(Login.this, "Please Enter The Valid Email", Toast.LENGTH_SHORT).show();
                else if((s4.isEmpty())||(s4.length()!=10)|| !android.util.Patterns.PHONE.matcher(s4).matches())
                    Toast.makeText(Login.this, "InValid Phone No.", Toast.LENGTH_SHORT).show();
                else {
                    getjsondata();
                }
            }
        });


    }

    public void getjsondata() {
        progressDialog.show();
        StringRequest st = new StringRequest(Request.Method.GET, "https://api.railwayapi.com/v2/pnr-status/pnr/" + s2.toString() + "/apikey/4ggslvvmn5/", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                get_response(response);
                progressDialog.dismiss();
                //Toast.makeText(MainActivity.this, ""+response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }

        });
        RequestQueue q = Volley.newRequestQueue(Login.this);
        q.add(st);

    }

    public void get_response(String response_obj) {

        try {
            JSONObject obj = new JSONObject(response_obj);

            String res = obj.getString("response_code");
            String c_prep = obj.getString("chart_prepared");
            //Toast.makeText(this, "" + res+"\n"+c_prep, Toast.LENGTH_SHORT).show();
            if (res.equals("200")) {


                //String mydata;
                JSONArray arr = obj.getJSONArray("passengers");
                JSONObject pos = arr.getJSONObject(0);
                if (c_prep.equals("true")) {
                    mydata = "Status=" + pos.getString("current_status");
                } else {
                    Toast.makeText(this, "Chart Not Prepared..", Toast.LENGTH_SHORT).show();
                    mydata = "Status=" + pos.getString("booking_status");
                }
                SharedPreferences.Editor e = f.edit();
                e.putString("k1", s1);
                e.putString("k2", s2);
                e.putString("k3", s3);
                e.putString("k4", s4);
                e.putString("k5", mydata);
                e.commit();
                Intent i = new Intent(Login.this, Mainpage.class);
                i.putExtra("k", mydata);
                startActivity(i);
            } else {
                Toast.makeText(this, "Invalid Pnr", Toast.LENGTH_SHORT).show();
            }


        } catch (Exception r) {
            Toast.makeText(this, "Error=" + r, Toast.LENGTH_SHORT).show();
        }

    }
}
