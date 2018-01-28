package passengersecurity.com.passengersecurity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.ArrayList;

public class Mainpage extends AppCompatActivity {
    TextView t1,t2,t3,t4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        t1=findViewById(R.id.tname);
        t2=findViewById(R.id.pnr);
        t3=findViewById(R.id.mailtext);
        t4=findViewById(R.id.numtext);
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        ArrayList arrayList = intent.getStringArrayListExtra("values");
        setContentView(R.layout.activity_mainpage);
        t1.setText(arrayList.get(0).toString());
        t2.setText(arrayList.get(1).toString());
        t3.setText(arrayList.get(2).toString());
        t4.setText(arrayList.get(3).toString());


    }

}
