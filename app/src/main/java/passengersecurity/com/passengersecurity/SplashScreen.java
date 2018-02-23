package passengersecurity.com.passengersecurity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class SplashScreen extends AppCompatActivity {

    SharedPreferences d;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        d=getSharedPreferences("sp",MODE_PRIVATE);
        final String name = d.getString("k1",null);
        final String pnr = d.getString("k2",null);
        final String email = d.getString("k3",null);
        final String phone = d.getString("k4",null);
        //Toast.makeText(this, "Name="+name+"\n"+"Pnr="+pnr+"\n"+"Email="+email+"\n"+"Phone"+phone+"\n", Toast.LENGTH_SHORT).show();
        Thread th = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(3000);
                } catch (Exception h) {
                } finally {
                    if(name!=null && pnr!=null && email!=null && phone!=null)
                    {
                        Intent i = new Intent(SplashScreen.this, Mainpage.class);
                        startActivity(i);
                    }
                    else
                    {
                        Intent i = new Intent(SplashScreen.this,FirstPage.class);
                        startActivity(i);
                    }

                    finish();
                }
            }
        };

        th.start();
    }
}
