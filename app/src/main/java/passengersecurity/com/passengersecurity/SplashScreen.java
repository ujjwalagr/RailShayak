package passengersecurity.com.passengersecurity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Thread th = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(3000);
                } catch (Exception h) {
                } finally {
                    Intent i = new Intent(SplashScreen.this, FirstPage.class);
                    startActivity(i);
                    finish();
                }
            }
        };

        th.start();
    }
}
