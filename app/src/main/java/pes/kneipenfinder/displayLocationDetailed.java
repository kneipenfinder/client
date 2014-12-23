package pes.kneipenfinder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;

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

        // Tabcontrol aufbauen
        initTabControl();

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

    // Hole sämtliche Location Informationen aus der Datenbank
    public void getLocationDetails(){
        JSONObject json = new JSONObject();
        try {
            json.put("action", "getLocationDetails");
            json.put("LocationID", currLocationID);
            json.put("lat", location.getLatitude());
            json.put("long", location.getLongitude());
            System.out.println(json);
        }catch(Exception e){
            // TODO Fehlerbehandlung
        }
        // Respond aufnehmen
        String respond = home.serverCom.secureCom(json.toString());
        System.out.println(respond);
    }

    // Tab Control aufbauen
    public void initTabControl(){
        TabHost tabHost=(TabHost)findViewById(R.id.tabControl);
        tabHost.setup();

        TabHost.TabSpec spec1=tabHost.newTabSpec("Tab 1");
        spec1.setContent(R.id.tab1);
        spec1.setIndicator(getResources().getString(R.string.Tabcontrol1));

        TabHost.TabSpec spec2=tabHost.newTabSpec("Tab 2");
        spec2.setIndicator(getResources().getString(R.string.Tabcontrol2));
        spec2.setContent(R.id.tab2);

        TabHost.TabSpec spec3=tabHost.newTabSpec("Tab 3");
        spec3.setIndicator(getResources().getString(R.string.Tabcontrol3));
        spec3.setContent(R.id.tab3);

        TabHost.TabSpec spec4=tabHost.newTabSpec("Tab 3");
        spec4.setIndicator(getResources().getString(R.string.Tabcontrol4));
        spec4.setContent(R.id.tab4);

        tabHost.addTab(spec1);
        tabHost.addTab(spec2);
        tabHost.addTab(spec3);
        tabHost.addTab(spec4);
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
