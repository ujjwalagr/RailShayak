package passengersecurity.com.passengersecurity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static com.google.android.gms.internal.zzbfq.NULL;

public class HomeScreen extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "HomeScreen" ;
    static int urlid;
    SharedPreferences d1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        d1 = getSharedPreferences("sp", MODE_PRIVATE);
        View navHeaderView = navigationView.inflateHeaderView(R.layout.nav_header_home_screen);
        TextView nameText = navHeaderView.findViewById(R.id.nameText);
        TextView pnrText = navHeaderView.findViewById(R.id.pnrtext);
        TextView seatText = navHeaderView.findViewById(R.id.seatbooking);
        ImageView img = navHeaderView.findViewById(R.id.imageView);
        img.setImageResource(R.drawable.mytrain);

        nameText.setText(d1.getString("k1", null));
        pnrText.setText("Pnr = " + d1.getString("k2", null));
        seatText.setText(d1.getString("k5", null));

        Location location = GPSUtil.getInstance().getLocation();
        if(location != null) {
            Log.d(TAG, "Location : "+location.getLatitude() + "," + location.getLongitude());
            Toast.makeText(this, "Latitude"+location.getLatitude()+"\n"+"Longitude"+location.getLongitude(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to exit?");
            builder.setCancelable(false);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    HomeScreen.this.finish();
                    finish();
                }
            })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment = null;
        if (id == R.id.tweet_complain) {
            fragment = new TweetComplain();
        } else if (id == R.id.sms_complain) {

            fragment = new SendBySMS();
        } else if (id == R.id.web_based_complain) {

            urlid = 1;
            fragment = new WebFragment();

        } else if (id == R.id.webbased) {

            urlid = 2;
            fragment = new WebFragment();
        } else if (id == R.id.smsbased) {

            urlid = 3;
            fragment = new WebFragment();
        } else if (id == R.id.sign_out) {
            d1.edit().putString("k1", null).apply();
            d1.edit().putString("k2", null).apply();
            d1.edit().putString("k3", null).apply();
            d1.edit().putString("k4", null).apply();
            d1.edit().putString("k5", null).apply();
            d1.edit().putString("signout", "yes").apply();


            Toast.makeText(this, "You have been logged out", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(HomeScreen.this, Login.class);
            startActivity(intent);
            finish();
            return true;
        } else if (id == R.id.add_contact) {
            Intent intent = new Intent(HomeScreen.this, AddContactFragment.class);
            startActivity(intent);
            return true;
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FrameLayout fragmentContainer = findViewById(R.id.fragmentContainer);
        fragmentContainer.removeAllViews();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
        fragmentTransaction.commit();


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (WebFragment.wb.canGoBack()) {
                        WebFragment.wb.goBack();
                    } else {
                        onBackPressed();
                    }
                    return true;
            }
        }

        return super.onKeyDown(keyCode, event);
    }
}

