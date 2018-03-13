package passengersecurity.com.passengersecurity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class TweetComplain extends Fragment {
    Spinner complaint;
    TextView description, pnronmain, nameonmain, statusonmain;
    Button submit;
    int position = 1;
    SharedPreferences d1;
    String name[] = new String[]{"Select an issue",
            "Accident Claims",
            "Allotment of seats - berths by train staff",
            "Bedroll Complaints",
            "Booking of Luggage / Parcels / Goods",
            "Bribery and Corruption",
            "Catering and Vending Services",
            "Cleanliness at Station",
            "Sexual Exploitation",
            "Improper Behaviour Of co-passenger",
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tweet, container, false);

        d1 = this.getActivity().getSharedPreferences("sp", Context.MODE_PRIVATE);
        complaint = view.findViewById(R.id.complaint);
        description = view.findViewById(R.id.description);
        nameonmain = view.findViewById(R.id.nameonmain);
        pnronmain = view.findViewById(R.id.pnronmain);
        statusonmain = view.findViewById(R.id.seatinfoonmain);
        submit = view.findViewById(R.id.submit);
        nameonmain.setText(d1.getString("k1", null));
        pnronmain.setText(d1.getString("k2", null));
        statusonmain.setText(d1.getString("k5", null));

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(), R.layout.support_simple_spinner_dropdown_item, name);
        complaint.setAdapter(arrayAdapter);

        complaint.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                complainType = (String) adapterView.getItemAtPosition(i);
                position = i;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getActivity(), "Please select an option", Toast.LENGTH_SHORT).show();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position == 0) {
                    Toast.makeText(getActivity(), "Select an Issue..!", Toast.LENGTH_SHORT).show();
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append("@RailMinIndia\n");
                    sb.append("Name - " + d1.getString("k1", null) + "\n");
                    sb.append("PNR - " + d1.getString("k2", null) + "\n");
                    sb.append("Contact no-" + d1.getString("k4", null) + "\n");
                    //sb.append("Seat No-" + d1.getString("k5", null) + "\n");
                    sb.append("Complaint type - " + complainType + "\n");
                    String descriptionText = description.getText().toString();
                    sb.append(descriptionText);

                    String tweetUrl = "https://twitter.com/intent/tweet?text=" + sb.toString();
                    Uri uri = Uri.parse(tweetUrl);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);

                }
            }
        });

        return view;
    }
}
