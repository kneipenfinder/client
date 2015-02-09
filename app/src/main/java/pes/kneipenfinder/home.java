package pes.kneipenfinder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class home extends Activity {

    public static serverCommunication serverCom;
    public static String deviceID;
    private String serverURL = helperMethods.getServerURL();
    private location location;
    public static AppProperties prop;
    private Intent i;
    public Context context;
    private errorHandling eHandling;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        context = this;
        // Hole die Geräte ID
        deviceID = helperMethods.getDeviceID(context);
        // Starte bei App Start die Kommunikation mit dem Server
        try {
            serverCom = new serverCommunication(serverURL, context);
        }catch (Exception e){
            // Schließe App, wenn Kommunikation nicht erfolgreich war
            helperMethods.closeApp();
        }
        // Erzeuge Location Objekt
        location = new location();
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000,1, location);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000,1, location);

        // Erzeuge beim Start ein Properties Objekt
        prop = new AppProperties();
        // Wenn noch kein Properties-File existiert, erstelle eins mit den default Werten
        if(prop.getProp("appinit", this) == "") {
            prop.initializePref(this);
        }

        if(!serverCom.initiateHandshake()) {
            eHandling = new errorHandling(context,"", "Es ist ein unerwarteter Fehler aufgetreten", "Handshake");
            // Nun muss sich die App schließen, da sichergestellt werden muss, dass der Anwender nichts mehr ausführt
            helperMethods.closeApp();
        }else{
            System.out.println("Handshake ok");
        }
    }

    public void button_findLocation(View v){
        i = new Intent(getApplicationContext(), find.class);
        startActivity(i);
    }

    public void button_addLocation(View v){
        i = new Intent(getApplicationContext(), add.class);
        startActivity(i);
    }

    public void button_searchLocation(View v){
        i = new Intent(getApplicationContext(), search.class);
        startActivity(i);
    }

    // Nicht mehr verwendet
    public void button_rateLocation(View v){
        // TODO Find for rate --> im Bestimmten umkreis (1km) suchen und zum bewerten anzeigen
        i = new Intent(getApplicationContext(), find.class);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id) {
            case R.id.action_settings:
                // Einstellungen
                i = new Intent(getApplicationContext(), settings.class);
                startActivity(i);
                break;
            case R.id.action_impressum:
                // Impressum
                i = new Intent(getApplicationContext(), impressum.class);
                startActivity(i);
                break;
            case R.id.action_socialmedia:
                // Social Media
                i = new Intent(getApplicationContext(), socialMedia.class);
                startActivity(i);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
