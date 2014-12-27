package pes.kneipenfinder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pes.kneipenfinder.R;

public class displayLocationDetailed extends Activity {

    private Intent i;
    private Context context;
    private int currLocationID;
    private TextView tvName;
    private TextView tvStrasse;
    private TextView tvPLZ;
    private TextView tvOrt;

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

            // Respond aufnehmen
            String respond = home.serverCom.secureCom(json.toString());
            JSONObject jsonObject;
            jsonObject = new JSONObject(respond);
            Boolean status = jsonObject.getBoolean("status");
            if(status){
                JSONArray locationArray;
                locationArray = jsonObject.getJSONArray("location");
                JSONObject location;
                location = locationArray.getJSONObject(0);
                initTab1(location);
                initTab2(location);
                initTab3(location);
                initTab4(location);
            }else{
                //TODO Fehler bei JSON erstellung handeln
                System.out.println("Fehler bei JSON");
            }
        }catch(Exception e){
            // TODO Fehlerbehandlung
            System.out.println("Fehler bei Kommunikation");
        }

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

    // Tab 1 "Übersicht" füllen
    public void initTab1(JSONObject respond) throws JSONException {
        tvName = (TextView) findViewById(R.id.tvName);
        tvName.setText(respond.getString("name"));
        tvStrasse = (TextView) findViewById(R.id.tvStrasse);
        tvStrasse.setText(respond.getString("strasse"));
        // TODO Einkommentieren wenn kommunikation steht
        /*tvPLZ = (TextView) findViewById(R.id.tvPLZ);
        tvPLZ.setText(respond.getString("postcode"));
        tvOrt = (TextView) findViewById(R.id.tvOrt);
        tvOrt.setText(respond.getString("ort"));*/
    }

    // Tab 2 "Öffnungszeiten" füllen
    public void initTab2(JSONObject respond){

    }

    // Tab 3 "Fotos füllen"
    public void initTab3(JSONObject respond){

    }

    // Tab 4 "Bewertungen und Kommentare" füllen
    public void initTab4(JSONObject respond){

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
