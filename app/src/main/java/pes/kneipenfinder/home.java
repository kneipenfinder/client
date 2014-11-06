package pes.kneipenfinder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;


public class home extends Activity {

    public static serverCommunication serverCom;
    private String serverURL = "http://futurebot.de";
    private location location;
    public static AppProperties prop;
    private Intent i;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Starte bei App Start die Kommunikation mit dem Server
        serverCom = new serverCommunication(serverURL);
        location = new location();
        // Erzeuge beim Start ein Properties Objekt
        prop = new AppProperties();
        // Wenn noch kein Properties-File existiert, erstelle eins mit den default Werten
        if(prop.getProp("appinit", this) == "") {
            prop.initializePref(this);
        }

        if(!serverCom.initiateHandshake()) {
            //TODO: Fehler handeln
        }else{
            System.out.println("Handshake ok");
        }
    }

    public void button_findLocation(View v){
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
            case R.id.action_home:
                // Home
                setContentView(R.layout.activity_home);
                break;

            case R.id.action_settings:
                // Einstellungen
                i = new Intent(getApplicationContext(), settings.class);
                startActivity(i);
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
