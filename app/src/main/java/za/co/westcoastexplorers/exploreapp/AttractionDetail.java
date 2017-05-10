package za.co.westcoastexplorers.exploreapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
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

                            findViewById(R.id.detailsheader).setVisibility(View.VISIBLE);
                            boolean hasDetails = false;

                            // set address
                            AppCompatTextView address = (AppCompatTextView)findViewById(R.id.address);
                            if (myPlace.getPhoneNumber() != null) {
                                address.setText(myPlace.getAddress());
                                hasDetails = true;
                                findViewById(R.id.addresscontainer).setVisibility(View.VISIBLE);
                            }

                            // set number
                            AppCompatTextView number = (AppCompatTextView)findViewById(R.id.number);
                            if (myPlace.getPhoneNumber() != null) {
                                number.setText(myPlace.getPhoneNumber());
                                number.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent i = new Intent(Intent.ACTION_CALL);
                                        i.setData(Uri.parse("tel:" + myPlace.getPhoneNumber().toString()));
                                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(i);
                                    }
                                });
                                hasDetails = true;
                                findViewById(R.id.numbercontainer).setVisibility(View.VISIBLE);
                            }

                            // set web
                            AppCompatTextView web = (AppCompatTextView)findViewById(R.id.website);
                            if (myPlace.getWebsiteUri() != null) {
                                web.setText(myPlace.getWebsiteUri().toString());
                                web.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent i = new Intent(Intent.ACTION_VIEW);
                                        i.setData(myPlace.getWebsiteUri());
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

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
