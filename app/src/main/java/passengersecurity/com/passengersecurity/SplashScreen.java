package passengersecurity.com.passengersecurity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {
    String name, pnr, email, phone, status;
    SharedPreferences d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        d = getSharedPreferences("sp", MODE_PRIVATE);
        name = d.getString("k1", null);
        pnr = d.getString("k2", null);
        email = d.getString("k3", null);
        phone = d.getString("k4", null);
        status = d.getString("k5", null);
        //Toast.makeText(this, "Name="+name+"\n"+"Pnr="+pnr+"\n"+"Email="+email+"\n"+"Phone"+phone+"\n", Toast.LENGTH_SHORT).show();
        Thread th = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(2000);
                } catch (Exception e) {
                } finally {
                    // User is logged in
                    if (name != null && pnr != null && email != null && phone != null && status != null) {
                        //Intent i = new Intent(SplashScreen.this, Mainpage.class);
                        Intent i = new Intent(SplashScreen.this, HomeScreen.class);
                        startActivity(i);
                    }
                    // Not a first time user
                    else {
                        Intent i = new Intent(SplashScreen.this, WelcomActivity.class);
                        startActivity(i);
                    }

                    finish();
                }
            }
        };

        th.start();
    }
}
