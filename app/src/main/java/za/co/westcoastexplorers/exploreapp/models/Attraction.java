package za.co.westcoastexplorers.exploreapp.models;

import android.view.View;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by rikus on 2017/05/09.
 */

public class Attraction {

    public String name;
    public String website;
    public String number;
    public String facilities;
    public String description;
    public String id;
    public String group = "";
    public String placeId;
    public double lat;
    public double lng;
    public View.OnClickListener clickListener;
    public String thumbnailURL;

}
