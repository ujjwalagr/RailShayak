package passengersecurity.com.passengersecurity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AddContactFragment extends AppCompatActivity {

    private static final long DELAY = 3000;
    RecyclerView rvContacts;
    private Handler handler;
    List<ContactVO> contactVOList;
    SharedPreferences pref;
    Set<String> trustedContacts;
    private AllContactsAdapter contactAdapter;
    Button addSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add_contact);

        rvContacts = findViewById(R.id.rvContacts);

        contactVOList = new ArrayList();
        trustedContacts = new HashSet<>();

        pref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                getAllContacts();
            }
        };

        rvContacts.addOnItemTouchListener(
                new RVItemClickListener(this, new RVItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (contactVOList.get(position).isSelected()) {
                            contactVOList.get(position).setSelected(false);
                            trustedContacts.remove(contactVOList.get(position).getContactNumber());
                            Toast.makeText(AddContactFragment.this, contactVOList.get(position).getContactName() + " removed", Toast.LENGTH_SHORT).show();
                        } else {
                            contactVOList.get(position).setSelected(true);
                            trustedContacts.add(contactVOList.get(position).getContactNumber());
                            Toast.makeText(AddContactFragment.this, contactVOList.get(position).getContactName() + " added", Toast.LENGTH_SHORT).show();
                        }
                        contactAdapter.notifyDataSetChanged();
                    }
                }));

        addSelected = findViewById(R.id.addSelected);
        addSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addContact();
                Toast.makeText(AddContactFragment.this, "Trusted contacts added", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AddContactFragment.this, HomeScreen.class));
            }
        });

    }


    @Override
    protected void onResume() {
        handler.sendEmptyMessageAtTime(1, DELAY);
        super.onResume();
    }

    private void getAllContacts() {
        Toast.makeText(getApplicationContext(), "contactsCalled", Toast.LENGTH_SHORT).show();
        ContactVO contactVO;

        if (getContactReadPermission()) {
            ContentResolver contentResolver = getContentResolver();
            Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {

                    int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
                    if (hasPhoneNumber > 0) {
                        String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                        String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                        contactVO = new ContactVO();
                        contactVO.setContactName(name);
                        contactVO.setSelected(false);

                        Cursor phoneCursor = contentResolver.query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                                new String[]{id},
                                null);
                        if (phoneCursor.moveToNext()) {
                            String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            contactVO.setContactNumber(phoneNumber);
                        }

                        phoneCursor.close();

                        Cursor emailCursor = contentResolver.query(
                                ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                                new String[]{id}, null);
                        while (emailCursor.moveToNext()) {
                            String emailId = emailCursor.getString(emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                        }
                        contactVOList.add(contactVO);
                    }
                }

                contactAdapter = new AllContactsAdapter(contactVOList, getApplicationContext());
                rvContacts.setLayoutManager(new LinearLayoutManager(this));
                rvContacts.setAdapter(contactAdapter);
            }
        } else {
            Toast.makeText(this, "Permission is Required", Toast.LENGTH_SHORT).show();
        }
    }

    private void addContact() {
        SharedPreferences.Editor editor = pref.edit();
        editor.putStringSet("contacts", trustedContacts);
        editor.apply();
    }

    public boolean getContactReadPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_CONTACTS)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }

}