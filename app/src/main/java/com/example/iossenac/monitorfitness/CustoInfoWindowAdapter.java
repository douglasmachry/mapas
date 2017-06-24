package com.example.iossenac.monitorfitness;

import android.app.Activity;
import android.support.v4.app.ActivityCompat;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;


/**
 * Created by iossenac on 17/06/17.
 */

public class CustoInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private final View mWindow;

    CustoInfoWindowAdapter(Activity act) {
        mWindow = act.getLayoutInflater().inflate(R.layout.custom_info_window,null);
    }


    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
