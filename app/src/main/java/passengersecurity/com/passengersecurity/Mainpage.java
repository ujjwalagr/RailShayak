package passengersecurity.com.passengersecurity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static passengersecurity.com.passengersecurity.Login.s4;

public class Mainpage extends AppCompatActivity {
    Spinner complaint;
    TextView description, pnronmain, nameonmain, statusonmain;
    Button submit;
    int position = 1;
    SharedPreferences d1;

    String name[] = new String[]{"Select an option",
            "Accident Claims",
            "Allotment of seats - berths by train staff",
            "Bedroll Complaints",
            "Booking of Luggage / Parcels / Goods",
            "Bribery and Corruption",
            "Catering and Vending Services",
            "Cleanliness at Station",
            "Complaint related to Sleeper Class",
            "Feedback / Suggestions",
            "Improper behaviour of commercial staff",
            "Improper behaviour of non commercial staff",
            "Improper behaviour of non railway staff",
            "Maintenance / Cleanliness of coaches",
            "Malfunctioning of Electrical Equipment",
            "Non availability of water",
            "Parking",
            "Passenger Booking",
            "Publicity",
            "Punctuality of Train",
            "Refund of Tickets",
            "Reservation Issues",
            "Retiring Room (ONLINE)",
            "Security",
            "Thefts / Pilferages",
            "Tout Package Service Related Grievances",
            "Unauthorised passengers in coaches",
            "WIFI Service",
            "Working of Enquiry Office",
            "Misc Cause"};
    String complainType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);
        d1 = getSharedPreferences("sp", MODE_PRIVATE);
        complaint = findViewById(R.id.complaint);
        description = findViewById(R.id.description);
        nameonmain = findViewById(R.id.nameonmain);
        pnronmain = findViewById(R.id.pnronmain);
        statusonmain = findViewById(R.id.seatinfoonmain);
        submit = findViewById(R.id.submit);

        nameonmain.setText(d1.getString("k1", null));
        pnronmain.setText(d1.getString("k2", null));
        statusonmain.setText(d1.getString("k5", null));


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, name);
        complaint.setAdapter(arrayAdapter);

        complaint.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                complainType = (String) adapterView.getItemAtPosition(i);
                position = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(Mainpage.this, "Please select an option", Toast.LENGTH_SHORT).show();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position == 0) {
                    Toast.makeText(Mainpage.this, "Select an Issue..!", Toast.LENGTH_SHORT).show();
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Name - " + d1.getString("k1", null) + "\n");
                    sb.append("PNR - " + d1.getString("k2", null) + "\n");
                    sb.append("Contact no-" + d1.getString("k4", null) + "\n");
                    sb.append("Seat No-" + d1.getString("k5", null) + "\n");
                    sb.append("Complaint type - " + complainType + "\n");
                    String descriptionText = description.getText().toString();
                    sb.append(descriptionText);
                    Uri uri = Uri.parse("smsto:8947848732");
                    Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                    intent.putExtra("sms_body", sb.toString());
                    startActivity(intent);

                }
            }
        });
    }

}
