package pes.kneipenfinder;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.location.Location;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
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
    private AppProperties prop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Starte bei App Start die Kommunikation mit dem Server
        serverCom = new serverCommunication(serverURL);
        location = new location();

        AssetManager assetManager = getAssets();
        prop = new AppProperties(assetManager);

        /**TESTAUSGABE ANFANG*/

        String test = prop.getProp("radius");
        System.out.println("radius: " + test);

        /**TESTAUSGABE ENDE*/


        if(!serverCom.initiateHandshake()) {
            //TODO: Fehler handeln
        }else{
            System.out.println("Handshake ok");
        }
    }

    private void findLocation(){


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

                setContentView(R.layout.activity_find);
                final TableLayout tableLayout = (TableLayout) findViewById(R.id.find_table);

                JSONArray locations = json.getJSONArray("locations");
                for(int i = 0; i < locations.length(); i++){

                    JSONObject location = locations.getJSONObject(i);

                    final TableRow tableRow = new TableRow(this);
                    tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));

                    final TextView text = new TextView(this);
                    text.setText(location.getString("name"));
                    text.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                    tableRow.addView(text);

                    final TextView text2 = new TextView(this);
                    text2.setText(location.getString("distance"));
                    text2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                    tableRow.addView(text2);


                    tableLayout.addView(tableRow);

                }

            }

        }catch(Exception e){

        }

    }

    public void button_findLocation(View v){

        findLocation();

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

                System.out.println("home");
                setContentView(R.layout.activity_home);
                break;

            case R.id.action_find:

                System.out.println("find");
                findLocation();
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
