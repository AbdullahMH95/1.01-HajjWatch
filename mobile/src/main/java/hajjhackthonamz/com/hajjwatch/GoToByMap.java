package hajjhackthonamz.com.hajjwatch;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
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
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class GoToByMap extends FragmentActivity implements OnMapReadyCallback {


    private GoogleMap mMap;
    private LocationManager locationManager;
    private Location location;
    private String provider;

    private String lat,lang;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_to_by_map);

        String spilt[] = getIntent().getExtras().getString("where").split(",");
        lat = spilt[0]; lang = spilt[1];

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), false);
        mMap = googleMap;



        if (ActivityCompat.checkSelfPermission(GoToByMap.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(GoToByMap.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(GoToByMap.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }else{
            Log.i("hello","hello");
            mMap.setMyLocationEnabled(true);
            location = locationManager.getLastKnownLocation(provider);
            //double lat = location.getLatitude() ;
            //double lon = location.getLongitude();
            //LatLng userPostion = new LatLng(lat, lon);
            LatLng userPostion = new LatLng(Double.parseDouble(lat),Double.parseDouble(lang));
            CameraUpdate center = CameraUpdateFactory.newLatLng(userPostion);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(userPostion));

            mMap.moveCamera(center);
            mMap.animateCamera(CameraUpdateFactory.zoomTo(18));
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(Double.parseDouble(lat),Double.parseDouble(lang))))
                    .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.friendsicon));

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

    public void goBack(View view) {
        onBackPressed();
    }

    public void goToAddFriends(View view) {
        startActivity(new Intent(getBaseContext(), AddFriend.class));
    }
}
