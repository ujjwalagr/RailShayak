package passengersecurity.com.passengersecurity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

public class GPSUtil implements LocationListener {

    private static GPSUtil INSTANCE;
    private final Context context;
    private static final String TAG = "GPSUtil";

    LocationManager locationManager;
    private Location location;

    public static GPSUtil getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new GPSUtil(context);
        }
        return INSTANCE;
    }

    public static GPSUtil getInstance() {
        return INSTANCE;
    }

    private GPSUtil(Context context) {
        this.context = context;
        try {
            locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    5000, 5, this);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("MissingPermission")
    public void refreshLocation() {
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, this);
    }

    public Location getLastLocation(){
        return location;
    }

    @Override
    public void onLocationChanged(Location location) {
        Toast.makeText(context,"location"+location.getLongitude()+","+location.getLatitude(),Toast.LENGTH_LONG).show();
        this.location = location;
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(context, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }
}