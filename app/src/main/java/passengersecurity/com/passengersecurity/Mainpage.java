package passengersecurity.com.passengersecurity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import static passengersecurity.com.passengersecurity.Login.s4;

public class Mainpage extends AppCompatActivity {
    TextView t1,t2,t3,t4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);
        t1=findViewById(R.id.tname);
        t2=findViewById(R.id.pnr);
        t3=findViewById(R.id.mailtext);
        t4=findViewById(R.id.numtext);

        t1.setText(Login.s1);
        t2.setText(Login.s2);
        t3.setText(Login.s3);
        t4.setText(Login.s4);



    }

}
