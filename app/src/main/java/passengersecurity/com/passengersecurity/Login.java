package passengersecurity.com.passengersecurity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    static String s1, s2, s3, s4;
    TextView b1;
    EditText e1,e2,e3,e4;
    SharedPreferences f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        b1=findViewById(R.id.sb);
        e1=findViewById(R.id.nametxt);
        e2=findViewById(R.id.pnrtxt);
        e3=findViewById(R.id.emailtxt);
        e4=findViewById(R.id.phonetxt);
        f=getSharedPreferences("sp",MODE_PRIVATE);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor e = f.edit();
                s1 = e1.getText().toString();
                s2 = e2.getText().toString();
                s3 = e3.getText().toString();
                s4=e4.getText().toString();

                if(s1.isEmpty())
                    Toast.makeText(Login.this, "Please Enter Name", Toast.LENGTH_SHORT).show();
                else if(s2.isEmpty()||s2.length()!=10||!android.util.Patterns.PHONE.matcher(s4).matches())
                    Toast.makeText(Login.this, "Please Enter valid PNR", Toast.LENGTH_SHORT).show();
                else if(s3.isEmpty()|| !android.util.Patterns.EMAIL_ADDRESS.matcher(s3).matches())
                    Toast.makeText(Login.this, "Please Enter The Valid Email", Toast.LENGTH_SHORT).show();
                else if((s4.isEmpty())||(s4.length()!=10)|| !android.util.Patterns.PHONE.matcher(s4).matches())
                    Toast.makeText(Login.this, "InValid Phone No.", Toast.LENGTH_SHORT).show();
                else if(s1.equals("admin")&& s2.equals("1234567890")&& s3.equals("coder@g.com")&&s4.equals("1234567890")) {
                    //b1.setEnabled(true);
                    e.putString("k1",s1);
                    e.putString("k2",s2);
                    e.putString("k3",s3);
                    e.putString("k4",s4);
                    e.commit();

                    b1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i = new Intent(Login.this, Mainpage.class);
                            startActivity(i);
                            Toast.makeText(Login.this, "Successful Register", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else
                {
                    Toast.makeText(Login.this, "Invalid Details", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
