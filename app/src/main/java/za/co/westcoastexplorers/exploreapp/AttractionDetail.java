package za.co.westcoastexplorers.exploreapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.GeoDataApi;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;

import za.co.westcoastexplorers.R;
import za.co.westcoastexplorers.exploreapp.controller.FireBaseController;
import za.co.westcoastexplorers.exploreapp.models.Attraction;

/**
 * Created by rikus on 2017/05/09.
 */

public class AttractionDetail extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;
    private Attraction mAttraction;

    // permissions
    int PERMISSIONCODE = 909;

    String numberGlobal = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attractiondetail);


        for (Attraction attraction : FireBaseController.getInstance().getAttractions()){
            if (getIntent().getExtras().containsKey("id") && attraction.id != null && (attraction.id).equals(getIntent().getExtras().get("id"))){
                mAttraction = attraction;
            }
        }

        setTitle(mAttraction.name);

        boolean hasDetails = false;

        // set number
        AppCompatTextView number = (AppCompatTextView)findViewById(R.id.number);
        if (mAttraction.number != null && !mAttraction.number.isEmpty() && number.getText().toString().isEmpty()) {
            number.setText(mAttraction.number);
            final CharSequence finalNumber = mAttraction.number;
            number.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    numberGlobal = finalNumber.toString();
                    callPhone();
                }
            });
            hasDetails = true;
            findViewById(R.id.numbercontainer).setVisibility(View.VISIBLE);
        }

        // set web
        AppCompatTextView web = (AppCompatTextView)findViewById(R.id.website);
        if (mAttraction.website != null && !mAttraction.website.isEmpty() && web.getText().toString().isEmpty()) {
            web.setText(mAttraction.website);
            final Uri finalWebsite = Uri.parse(mAttraction.website);
            web.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(finalWebsite);
                    startActivity(i);
                }
            });
            hasDetails = true;
            findViewById(R.id.websitecontainer).setVisibility(View.VISIBLE);
        }

        // set facility
        if (mAttraction.facilities != null && !mAttraction.facilities.isEmpty() && mAttraction.facilities.length() == 4) {
            if (mAttraction.facilities.charAt(0) == '1'){
                findViewById(R.id.facBike).setVisibility(View.VISIBLE);
            }

            if (mAttraction.facilities.charAt(1) == '1'){
                findViewById(R.id.facCard).setVisibility(View.VISIBLE);
            }

            if (mAttraction.facilities.charAt(2) == '1'){
                findViewById(R.id.facVoucher).setVisibility(View.VISIBLE);
            }

            if (mAttraction.facilities.charAt(3) == '1'){
                findViewById(R.id.facWifi).setVisibility(View.VISIBLE);
            }


            hasDetails = true;
            findViewById(R.id.facilitycontainer).setVisibility(View.VISIBLE);
        }


        if (hasDetails)
            findViewById(R.id.detailsheader).setVisibility(View.VISIBLE);

        final boolean finalDetails = hasDetails;

        if (mAttraction.placeId != null) {
            try {
                mGoogleApiClient = new GoogleApiClient
                        .Builder(this)
                        .addApi(Places.GEO_DATA_API)
                        .addApi(Places.PLACE_DETECTION_API)
                        .enableAutoManage(this, this)
                        .build();

                Places.GeoDataApi.getPlaceById(mGoogleApiClient, mAttraction.placeId).setResultCallback(new ResultCallback<PlaceBuffer>() {
                    @Override
                    public void onResult(@NonNull PlaceBuffer places) {
                        if (places.getStatus().isSuccess() && places.getCount() > 0) {
                            final Place myPlace = places.get(0);
                            Log.i("PLACES", "Place found: " + myPlace.getName());

                            boolean hasDetails = finalDetails;

                            // set address
                            AppCompatTextView address = (AppCompatTextView)findViewById(R.id.address);
                            if (myPlace.getPhoneNumber() != null) {
                                address.setText(myPlace.getAddress());
                                hasDetails = true;
                                findViewById(R.id.addresscontainer).setVisibility(View.VISIBLE);
                            }

                            // set number
                            AppCompatTextView number = (AppCompatTextView)findViewById(R.id.number);
                            if (myPlace.getPhoneNumber() != null && number.getText().toString().isEmpty()) {
                                number.setText(myPlace.getPhoneNumber());
                                final CharSequence finalNumber = myPlace.getPhoneNumber();
                                number.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        numberGlobal = finalNumber.toString();
                                        callPhone();
                                    }
                                });
                                hasDetails = true;
                                findViewById(R.id.numbercontainer).setVisibility(View.VISIBLE);
                            }

                            // set web
                            AppCompatTextView web = (AppCompatTextView)findViewById(R.id.website);
                            if (myPlace.getWebsiteUri() != null && web.getText().toString().isEmpty()) {
                                web.setText(myPlace.getWebsiteUri().toString());
                                final Uri finalWebsite = myPlace.getWebsiteUri();
                                web.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent i = new Intent(Intent.ACTION_VIEW);
                                        i.setData(finalWebsite);
                                        startActivity(i);
                                    }
                                });
                                hasDetails = true;
                                findViewById(R.id.websitecontainer).setVisibility(View.VISIBLE);
                            }

                            if (hasDetails)
                                findViewById(R.id.detailsheader).setVisibility(View.VISIBLE);


                        } else {
                            Log.e("PLACES", "Place not found");
                        }
                        places.release();
                    }
                });


            } catch (Exception e){
                e.printStackTrace();
            }
        }


        AppCompatTextView tv = (AppCompatTextView)findViewById(R.id.description);
        if (mAttraction.description != null && !mAttraction.description.isEmpty())
            tv.setText(mAttraction.description);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }

    private void callPhone (){
        Intent i = new Intent(Intent.ACTION_CALL);
        i.setData(Uri.parse("tel:" + numberGlobal));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        if (ActivityCompat.checkSelfPermission(AttractionDetail.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AttractionDetail.this, new String[]{Manifest.permission.CALL_PHONE}, 909);
            return;
        }

        startActivity(i);
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        for (int i = 0; i < grantResults.length; i++){
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED){
                if (permissions [i] == Manifest.permission.CALL_PHONE){
                    callPhone();
                }

                return;
            }
        }



        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
