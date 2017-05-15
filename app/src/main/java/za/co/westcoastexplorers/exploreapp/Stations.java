package za.co.westcoastexplorers.exploreapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import za.co.westcoastexplorers.R;
import za.co.westcoastexplorers.exploreapp.controller.FireBaseController;
import za.co.westcoastexplorers.exploreapp.models.Attraction;
import za.co.westcoastexplorers.exploreapp.models.Station;

/**
 * Created by rikus on 2017/05/09.
 */

public class Stations extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stations);
        setTitle(getString(R.string.home_route));

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);


        final LatLngBounds.Builder latLngBoundsBuilder = new LatLngBounds.Builder();
        final ArrayList<LatLng> latLngs = new ArrayList<>();

        for (Station station : FireBaseController.getInstance().getStations()){
            LatLng latLng = new LatLng(station.lat, station.lng);
            latLngs.add(latLng);
            latLngBoundsBuilder.include(latLng);
            MarkerOptions marker = new MarkerOptions().position(latLng).title(station.name).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker));
            mMap.addMarker(marker);
        }

        if (latLngs.size() > 1) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(latLngBoundsBuilder.build(),
                    getResources().getDisplayMetrics().widthPixels,
                    (((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, getResources().getDisplayMetrics()))),
                    (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, getResources().getDisplayMetrics())));
        } else if (!latLngs.isEmpty()) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngs.get(0), 14));
        }
    }
}
