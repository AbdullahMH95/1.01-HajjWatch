package hajjhackthonamz.com.hajjwatch;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Marker marker;
    private LocationManager locationManager;
    private Location location;
    private String provider;
    private DatabaseReference mDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Friend");
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("Name", "Ahmed");
        hashMap.put("lat", "-4.00");
        hashMap.put("lon", "4.00");
        mDatabase.push().setValue(hashMap);
        HashMap<String, String> hashMap1 = new HashMap<String, String>();

        hashMap1.put("Name", "Ziyad");
        hashMap1.put("lat", "-4.50");
        hashMap1.put("lon", "4.50");
        mDatabase.push().setValue(hashMap1);
        HashMap<String, String> hashMap2 = new HashMap<String, String>();

        hashMap2.put("Name", "Abdul");
        hashMap2.put("lat", "-5.00");
        hashMap2.put("lon", "5.00");
        mDatabase.push().setValue(hashMap2);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), false);
        mMap = googleMap;



        if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }else{
            Log.i("hello","hello");
            mMap.setMyLocationEnabled(true);
            location = locationManager.getLastKnownLocation(provider);
            double lat = location.getLatitude() ;
            double lon = location.getLongitude();
            LatLng userPostion = new LatLng(lat, lon);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(userPostion));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(30));


            // 21.617661, 39.156629
            Friend friend = new Friend();
            friend.setName("ziyad");
            friend.setLat("21.617661");
            friend.setLon("39.156629");
            Friend friend1 = new Friend();

            // 21.616932, 39.156693
            friend1.setName("ahmed");
            friend1.setLat("21.616932");
            friend1.setLon("39.156693");
            Friend friend2 = new Friend();
             ArrayList<Friend> friends;
            friends = new ArrayList<>();


            // 21.616882, 39.155985
            friend.setName("abdullah");
            friend2.setLat("21.616882");
            friend2.setLon("39.155985");
            friends.add(friend1);
            friends.add(friend2);
            friends.add(friend);



                    for (int i=0;i<friends.size();i++){
                        //addMarker(mGoogleMap);
                        mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(Double.parseDouble(friends.get(i).getLat()),Double.parseDouble(friends.get(i).getLon()))))
                                .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.friendsicon));

                    }



}

    }
    public void animateMarker(final Marker marker, final LatLng toPosition,
                              final boolean hideMarker) {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        Projection proj = mMap.getProjection();
        Point startPoint = proj.toScreenLocation(marker.getPosition());
        final LatLng startLatLng = proj.fromScreenLocation(startPoint);
        final long duration = 500;

        final Interpolator interpolator = new LinearInterpolator();

        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed
                        / duration);
                double lng = t * toPosition.longitude + (1 - t)
                        * startLatLng.longitude;
                double lat = t * toPosition.latitude + (1 - t)
                        * startLatLng.latitude;
                marker.setPosition(new LatLng(lat, lng));

                if (t < 1.0) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16);
                } else {
                    if (hideMarker) {
                        marker.setVisible(false);
                    } else {
                        marker.setVisible(true);
                    }
                }
            }
        });
    }

}
