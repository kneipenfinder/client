package pes.kneipenfinder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import pes.kneipenfinder.R;

public class find extends Activity {

    private errorHandling eHandling;
    Context context;
    private Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        findLocation(this);
    }

    private void findLocation(Context context){

        this.context = context;
        Double Lat = location.getLatitude();
        Double Long = location.getLongitude();

        JSONObject json = new JSONObject();
        try {
            json.put("action", "find");
            json.put("lat", location.getLatitude());
            json.put("long", location.getLongitude());
        }catch(Exception e){
            // TODO Fehlerbehandlung
        }
        // Respond aufnehmen
        String respond = home.serverCom.secureCom(json.toString());
        // Suchergebnisse anzeigen
        displayResults results = new displayResults(respond,"Ergebnisse Umkreissuche",context);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.find, menu);
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
        }
        return super.onOptionsItemSelected(item);
    }
}
