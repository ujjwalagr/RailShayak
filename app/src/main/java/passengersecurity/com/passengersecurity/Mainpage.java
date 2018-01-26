package passengersecurity.com.passengersecurity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import static passengersecurity.com.passengersecurity.Login.s4;

public class Mainpage extends AppCompatActivity {
    TextView t1,t2,t3,t4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        t1=findViewById(R.id.nametxt);
        t2=findViewById(R.id.pnrtxt);
        t3=findViewById(R.id.mailtext);
        t4=findViewById(R.id.phonetxt);
        super.onCreate(savedInstanceState);

        t1.setText(Login.s12);
        t2.setText(Login.s12);
        t3.setText(Login.s12);
        t4.setText(Login.s12);
        setContentView(R.layout.activity_mainpage);


    }

}
