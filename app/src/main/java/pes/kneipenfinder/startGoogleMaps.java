package pes.kneipenfinder;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by pes on 08.12.2014.
 */
public class startGoogleMaps {

    public startGoogleMaps(double latitude, double longitude, String locationSearchText,Context context){

        String url = "";
        // URL erzeugen ...
        if(latitude == 0 && longitude == 0) {
            url = "geo:" + latitude + "," + longitude;
        } else if(!locationSearchText.isEmpty()){
            url = "geo:0,0?q="+locationSearchText;
        }

        // ... und Maps aufrufen
        if(!url.isEmpty()) {
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(url));
            context.startActivity(intent);
        }
    }
}
