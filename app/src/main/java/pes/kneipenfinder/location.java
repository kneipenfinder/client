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
    public static boolean locationActive = false;

    public static double getLongitude(){
        return longitude;
    }

    public static double getLatitude(){
        return latitude;
    }

    @Override
    public void onLocationChanged(Location location)
    {
        locationActive = true;
        System.out.println("Location changed.");
        location.getLatitude();
        location.getLongitude();
        latitude=location.getLatitude();
        longitude=location.getLongitude();
    }

    @Override
    public void onProviderDisabled(String provider)
    {
        System.out.println("Currently GPS is disabled.");
        locationActive = false;
    }
    @Override
    public void onProviderEnabled(String provider)
    {
        System.out.println("GPS enbaled.");
    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras)
    {
        System.out.println("Status Changed.");
    }

}
