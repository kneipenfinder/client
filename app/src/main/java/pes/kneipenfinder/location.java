package pes.kneipenfinder;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

/**
 * Created by ruppenthal on 23.10.2014.
 */
public class location implements LocationListener {

    public static double latitude;
    public static double longitude;

    public double getLongitude(){
        //return longitude;
        return 51.674967;
    }

    public double getLatitude(){
       // return latitude;
        return 6.941239;
    }

    @Override
    public void onLocationChanged(Location location)
    {
        location.getLatitude();
        location.getLongitude();
        latitude=location.getLatitude();
        longitude=location.getLongitude();
    }

    @Override
    public void onProviderDisabled(String provider)
    {
        //print "Currently GPS is Disabled";
    }
    @Override
    public void onProviderEnabled(String provider)
    {
        //print "GPS got Enabled";
    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras)
    {
    }

}
