package pes.kneipenfinder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONObject;

import pes.kneipenfinder.R;

public class displayLocationDetailed extends Activity {

    private Intent i;
    private Context context;
    private int currLocationID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_location_detailed);

        context = this;
        Activity activity = (Activity) context;
        // Überschrift setzen
        activity.getActionBar().setTitle("Hier muss der Kneipenname stehen.");

        // Die übergebene LocationID wieder aufnehmen und in eine Variable speichern
        Bundle b = getIntent().getExtras();
        currLocationID = b.getInt("LocationID");

        // Location Details aus der DB holen
        getLocationDetails();
    }

    public void getLocationDetails(){
        JSONObject json = new JSONObject();
        try {
            json.put("action", "getLocationDetails");
            json.put("LocationID", currLocationID);
            System.out.println(json);
        }catch(Exception e){
            // TODO Fehlerbehandlung
        }
        // Respond aufnehmen
        String respond = home.serverCom.secureCom(json.toString());
        System.out.println(respond);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.display_location_detailed, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id) {
            case R.id.action_home:
                // Home
                setContentView(R.layout.activity_home);
                break;
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
                // Social Media --> Facebook, Twitter, Google+
                i = new Intent(getApplicationContext(), socialMedia.class);
                startActivity(i);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
