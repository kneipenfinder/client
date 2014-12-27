package pes.kneipenfinder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RatingBar;
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
    private TextView tvMontagVon;
    private TextView tvMontagBis;
    private TextView tvDienstagVon;
    private TextView tvDienstagBis;
    private TextView tvMittwochVon;
    private TextView tvMittwochBis;
    private TextView tvDonnerstagVon;
    private TextView tvDonnerstagBis;
    private TextView tvFreitagVon;
    private TextView tvFreitagBis;
    private TextView tvSamstagVon;
    private TextView tvSamstagBis;
    private TextView tvSonntagVon;
    private TextView tvSonntagBis;
    private RatingBar ratingBar;
    private TextView tvAnzahlBewertungen;

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
    public void initTab2(JSONObject respond) throws JSONException {
        tvMontagVon = (TextView) findViewById(R.id.tvMontagVon);
        if(respond.getString("Open_From_Monday") == "null"){
            tvMontagVon.setText("n.a.");
        }else {
            tvMontagVon.setText(respond.getString("Open_From_Monday"));
        }
        tvMontagBis = (TextView) findViewById(R.id.tvMontagBis);
        if(respond.getString("Open_To_Monday") == "null"){
            tvMontagBis.setText("n.a.");
        }else {
            tvMontagBis.setText(respond.getString("Open_To_Monday"));
        }
        tvDienstagVon = (TextView) findViewById(R.id.tvDienstagVon);
        if(respond.getString("Open_From_Tuesday")== "null"){
            tvDienstagVon.setText("n.a.");
        }else {
            tvDienstagVon.setText(respond.getString("Open_From_Tuesday"));
        }
        tvDienstagBis = (TextView) findViewById(R.id.tvDienstagBis);
        if(respond.getString("Open_To_Tuesday") == "null"){
            tvDienstagBis.setText("n.a.");
        }else {
            tvDienstagBis.setText(respond.getString("Open_To_Tuesday"));
        }
        tvMittwochVon = (TextView) findViewById(R.id.tvMittwochVon);
        if(respond.getString("Open_From_Wednesday") == "null"){
            tvMittwochVon.setText("n.a.");
        }else {
            tvMittwochVon.setText(respond.getString("Open_From_Wednesday"));
        }
        tvMittwochBis = (TextView) findViewById(R.id.tvMittwochBis);
        if(respond.getString("Open_To_Wednesday") == "null"){
            tvMittwochBis.setText("n.a.");
        }else {
            tvMittwochBis.setText(respond.getString("Open_To_Wednesday"));
        }
        tvDonnerstagVon = (TextView) findViewById(R.id.tvDonnerstagVon);
        if(respond.getString("Open_From_Thursday") == "null"){
            tvDonnerstagVon.setText("n.a.");
        }else {
            tvDonnerstagVon.setText(respond.getString("Open_From_Thursday"));
        }
        tvDonnerstagBis = (TextView) findViewById(R.id.tvDonnerstagBis);
        if(respond.getString("Open_To_Thursday") == "null"){
            tvDonnerstagBis.setText("n.a.");
        }else {
            tvDonnerstagBis.setText(respond.getString("Open_To_Thursday"));
        }
        tvFreitagVon = (TextView) findViewById(R.id.tvFreitagVon);
        if(respond.getString("Open_From_Friday") == "null"){
            tvFreitagVon.setText("n.a.");
        }else {
            tvFreitagVon.setText(respond.getString("Open_From_Friday"));
        }
        tvFreitagBis = (TextView) findViewById(R.id.tvFreitagBis);
        if(respond.getString("Open_To_Friday") == "null"){
            tvFreitagBis.setText("n.a.");
        }else {
            tvFreitagBis.setText(respond.getString("Open_To_Friday"));
        }
        tvSamstagVon = (TextView) findViewById(R.id.tvSamstagVon);
        if(respond.getString("Open_From_Saturday") == "null"){
            tvSamstagVon.setText("n.a.");
        }else {
            tvSamstagVon.setText(respond.getString("Open_From_Saturday"));
        }
        tvSamstagBis = (TextView) findViewById(R.id.tvSamstagBis);
        if(respond.getString("Open_To_Saturday") == "null"){
            tvSamstagBis.setText("n.a.");
        }else {
            tvSamstagBis.setText(respond.getString("Open_To_Saturday"));
        }
        tvSonntagVon = (TextView) findViewById(R.id.tvSonntagVon);
        if(respond.getString("Open_From_Sunday") == "null"){
            tvSonntagVon.setText("n.a.");
        }else {
            tvSonntagVon.setText(respond.getString("Open_From_Sunday"));
        }
        tvSonntagBis = (TextView) findViewById(R.id.tvSonntagBis);
        if(respond.getString("Open_To_Sunday") == "null"){
            tvSonntagBis.setText("n.a.");
        }else {
            tvSonntagBis.setText(respond.getString("Open_To_Sunday"));
        }
    }

    // Tab 3 "Fotos füllen"
    public void initTab3(JSONObject respond){

    }

    // Tab 4 "Bewertungen und Kommentare" füllen
    public void initTab4(JSONObject respond) throws JSONException {
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        String rating;
        rating = respond.getString("rating_stars");
        ratingBar.setRating(Float.parseFloat(rating));
        tvAnzahlBewertungen = (TextView) findViewById(R.id.tvAnzahlBewertungen);
        if(respond.getString("rating_amount") == "0"){
            tvAnzahlBewertungen.setText("Keine Bewertungen");
        }else{
            tvAnzahlBewertungen.setText(respond.getString("rating_amount") + " " + "Bewertung(en)");
        }
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
