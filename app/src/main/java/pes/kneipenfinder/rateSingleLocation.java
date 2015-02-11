package pes.kneipenfinder;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import org.json.JSONObject;

public class rateSingleLocation extends Activity {

    private Intent i;
    private int currLocationID;
    private String currLocationName;
    private EditText Kommentar;
    private RatingBar ratingBar;
    private Context context = this;
    private Button buttonRate;
    private TextView tvLocationName;
    private errorHandling eHandling;
    private JSONObject json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        // Die übergebene LocationID wieder aufnehmen und in eine Variable speichern
        Bundle b = getIntent().getExtras();
        currLocationID = b.getInt("LocationID");
        currLocationName = b.getString("LocationName");

        // Initialisieren
        initRate();

        buttonRate = (Button) findViewById(R.id.rate);
        buttonRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ratingBar.getRating() != 0){
                    try {
                        json = new JSONObject();
                        setJSON();
                        // Respond aufnehmen
                        System.out.println(json);
                        String respond = home.serverCom.secureCom(json.toString());
                        System.out.println(respond);
                        JSONObject jsonObject;
                        jsonObject = new JSONObject(respond);
                        Boolean status = jsonObject.getBoolean("status");
                        if(status){
                            finish();
                            messageDialog msg = new messageDialog(context, "Erfolgreich abgestimmt", "Die Location wurde bewertet.");
                        }else{
                            eHandling = new errorHandling(context,"Fehler bei Abgabe der Bewertung", "Die Location wurde schon bewertet.", "rate");
                        }
                    } catch (Exception e){
                        eHandling = new errorHandling(context,"Fehler bei Abgabe der Bewertung", "Es ist ein unerwarteter Fehler aufgetreten", "rate");
                    }
                }else{
                    eHandling = new errorHandling(context,"Es wurde keine Bewertung abgegeben", "Es muss mindestens mit einem Stern bewertet werden", "");
                }
            }
        });
    }

    public void initRate(){
        tvLocationName = (TextView) findViewById(R.id.tvLocationName);
        tvLocationName.setText(currLocationName);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        ratingBar.setNumStars(5);
        // On Click Listener für die Postleitzahl der Location
        Kommentar = (EditText) findViewById(R.id.editText_Comment);
        Kommentar.setFocusable(false);
        Kommentar.setClickable(true);
        Kommentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // hole das Alert Layout
                LayoutInflater layoutInflater = LayoutInflater.from(context);

                View promptView = layoutInflater.inflate(R.layout.alert_comment, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                alertDialogBuilder.setView(promptView);

                final EditText input = (EditText) promptView.findViewById(R.id.userInput);

                // Erstelle das Fenster
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Hole den User Input und schreibe ihn ins Textfeld
                                Kommentar.setText(input.getText());
                            }
                        })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,	int id) {
                                        dialog.cancel();
                                    }
                                });

                // Erstelle einen Alert Dialog
                AlertDialog alertD = alertDialogBuilder.create();

                alertD.show();
            }
        });
    }

    public void setJSON(){
        try {
            json.put("action", "rateLocation");
            json.put("LocationID", currLocationID);
            json.put("Rating", ratingBar.getRating());
            if(!Kommentar.getText().equals("")) {
                json.put("Comment", Kommentar.getText().toString());
            }
        } catch (Exception e){
            // TODO Fehlerhandling
            System.out.println(e);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.rate, menu);
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
            case R.id.action_socialmedia:
                // Social Media --> Facebook, Twitter, Google+
                i = new Intent(getApplicationContext(), socialMedia.class);
                startActivity(i);
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
