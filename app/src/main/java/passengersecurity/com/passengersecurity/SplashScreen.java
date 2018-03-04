package passengersecurity.com.passengersecurity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
public class SplashScreen extends AppCompatActivity {
    SharedPreferences sp;
    String firstrun, signout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        sp = getSharedPreferences("sp", MODE_PRIVATE);
        firstrun = sp.getString("firstrun", null);
        if (firstrun == "no")
            firstrun = "no";
        else
            firstrun = "yes";
        signout = sp.getString("signout", null);
        final Thread th = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(500);
                } catch (Exception e) {
                } finally {

                    if (firstrun == "no" && signout == "yes") {
                        Intent intent = new Intent(SplashScreen.this, Login.class);
                        startActivity(intent);
                        finish();
                    } else if (firstrun == "no" && signout == "no") {
                        Intent intent = new Intent(SplashScreen.this, HomeScreen.class);
                        startActivity(intent);
                        finish();
                    } else if (firstrun == "yes") {
                        Intent i = new Intent(SplashScreen.this, FirstPage.class);
                        startActivity(i);

                    }

                    finish();
                }
            }
        };

        th.start();
    }
}
