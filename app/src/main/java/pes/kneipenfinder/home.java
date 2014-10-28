package pes.kneipenfinder;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Properties;


public class home extends Activity {

    private serverCommunication serverCom;
    private String serverURL = "http://futurebot.de";
    private location location;
    private Properties prop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Starte bei App Start die Kommunikation mit dem Server
        serverCom = new serverCommunication(serverURL);
        location = new location();

        if(!serverCom.initiateHandshake()) {
            //TODO: Fehler handeln
        }else{
            System.out.println("Handshake ok");
        }
    }


    public void button_findLocation(View v){

        Double Lat = location.getLatitude();
        Double Long = location.getLongitude();

        JSONObject json = new JSONObject();
        try {
            json.put("action", "find");
            json.put("lat", location.getLatitude());
            json.put("long", location.getLongitude());
        }catch(Exception e){

        }

        String respond = serverCom.secureCom(json.toString());
        try {

            json = new JSONObject(respond);
            Boolean status = json.getBoolean("status");
            if(!status){
                //TODO: Fehler handeln
                System.out.println("GPS fehler location nicht gefunden oder so");
            }else{
                System.out.println("Kneipen anzeigen");
            }

        }catch(Exception e){
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
