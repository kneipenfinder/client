package pes.kneipenfinder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TabHost;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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
    private ListView elvKommentare;
    private ArrayList<String> parentItems = new ArrayList<String>();
    private ArrayList<String> childItems = new ArrayList<String>();
    private ArrayAdapter<String> listAdapter;
    private GridView gvFotos;
    private int gridPadding;
    private int gridNumberOfColumns;
    private static String pictureURL = "location_pictures/";
    private ArrayList<String> filePaths = new ArrayList<String>();
    private gridViewImageAdapter adapter;

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
                JSONObject location;
                location = jsonObject.getJSONObject("location");
                try {
                    initTab1(location);
                }catch (Exception e){
                    // Fehler abfangen
                }
                try {
                    initTab2(location);
                } catch (Exception e){
                    // Fehler abfangen
                }
                try {
                    initTab3(location);
                }catch (Exception e){
                    // Fehler abfangen
                }
                try {
                    initTab4(location);
                }catch (Exception e){
                    // Fehler abfangen
                }
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
        tvPLZ = (TextView) findViewById(R.id.tvPLZ);
        tvPLZ.setText(respond.getString("postcode"));
        tvOrt = (TextView) findViewById(R.id.tvOrt);
        tvOrt.setText(respond.getString("city"));

        final Button buttonFacebook = (Button) findViewById(R.id.button_facebook);
        buttonFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new socialMedia().button_openFacebook(v);
            }
        });

        final Button buttonGoogle = (Button) findViewById(R.id.button_google);
        buttonGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new socialMedia().button_openGoogle(v);
            }
        });

        final Button buttonTwitter = (Button) findViewById(R.id.button_twitter);
        buttonTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new socialMedia().button_openTwitter(v);
            }
        });

        final Button buttonNavigate = (Button) findViewById(R.id.button_maps);
        buttonNavigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String searchString = "";
                    if(!tvName.getText().toString().isEmpty()){
                        searchString = tvName.getText().toString()+",";
                    }
                    if(!tvStrasse.getText().toString().isEmpty()){
                        searchString = searchString + tvStrasse.getText().toString()+",";
                    }
                    if(!tvPLZ.getText().toString().isEmpty()){
                        searchString = searchString + tvPLZ.getText().toString()+",";
                    }
                    if(!tvOrt.getText().toString().isEmpty()){
                        searchString = searchString + tvOrt.getText().toString();
                    }
                    new startGoogleMaps(0,0, searchString, context);
                }catch (Exception e){
                    // TODO FEHLERHANDLING
                    System.out.println(e);
                }
            }
        });
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
        gvFotos = (GridView) findViewById(R.id.gvFotos);
        initilizeGridLayout();
        getPicturepaths();
        // TODO: Image View dynamisch ermittlen
        Integer imageWidth = 100;
        adapter = new gridViewImageAdapter(this, filePaths, imageWidth);
        try {
            gvFotos.setAdapter(adapter);
        } catch (Exception e){
            // TODO Fehlerhandling  beim laden der Fotos
            System.out.println(e);
        }
    }

    // Tab 4 "Bewertungen und Kommentare" füllen
    public void initTab4(JSONObject respond) throws JSONException {
        final Button buttonRate = (Button) findViewById(R.id.button_rate);
        buttonRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), rateSingleLocation.class);
                context.startActivity(intent);
            }
        });
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
        setListView(respond);
    }

    // Befüllt die List View mit den Kommentaren zu der Location aus der DB
    public void setListView(JSONObject respond) throws JSONException {
        elvKommentare = (ListView) findViewById(R.id.elvKommentare);
        elvKommentare.setDividerHeight(2);
        elvKommentare.setClickable(true);
        setParentItems(respond);
        listAdapter = new ArrayAdapter<String>(this, R.layout.elv_simple_row, parentItems);
        elvKommentare.setAdapter(listAdapter);
    }

    // Setzt die Kommentare...
    public void setParentItems(JSONObject respond) throws JSONException {
        JSONArray comments = respond.getJSONArray("comments");
        for(int i = 0; i < comments.length(); i++){
            JSONObject comment = comments.getJSONObject(i);
            if(!comment.getString("Optional_Comment").equals("null")) {
                parentItems.add(i, comment.getString("Optional_Comment"));
            }
            setChildItems(i ,comment.toString());
        }
    }

    // Noch nicht in Benutzung
    public void setChildItems(Integer index, String comment){
        childItems.add(index, comment);
    }

    // initialisiert das Grid Layout zum Anzeigen der Location Fotos
    private void initilizeGridLayout() {
        Resources r = getResources();
        float padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                gridPadding, r.getDisplayMetrics());

        int columnWidth = (int) ((helperMethods.getScreenWidth(context) - ((gridNumberOfColumns + 1) * padding)) / gridNumberOfColumns);

        gvFotos.setNumColumns(3);
        gvFotos.setColumnWidth(columnWidth);
        gvFotos.setStretchMode(GridView.NO_STRETCH);
        gvFotos.setPadding((int) padding, (int) padding, (int) padding,
                (int) padding);
        gvFotos.setHorizontalSpacing((int) padding);
        gvFotos.setVerticalSpacing((int) padding);
    }

    // Holt alle Fotos aus der Datenbank
    public void getPicturepaths(){
        JSONObject json = new JSONObject();
        try {
            json.put("action", "getLocationPictures");
            json.put("LocationID", currLocationID);

            String respond = home.serverCom.secureCom(json.toString());
            System.out.println(respond);
            JSONObject object = new JSONObject(respond);
            Boolean status = object.getBoolean("status");
            if(!status){
                // TODO Errorhandling status nicht ok
            }else{
                JSONArray pictures = object.getJSONArray("pictures");
                for (int i = 0; i < pictures.length(); i++){
                    JSONObject picture = pictures.getJSONObject(i);
                    String currPicture = helperMethods.getServerURLForPictures() + pictureURL + picture.getString("Filename");
                    filePaths.add(currPicture);
                }
            }
        }catch (Exception e){
            // TODO Fehlerhandling
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
