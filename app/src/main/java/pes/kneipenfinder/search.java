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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pes.kneipenfinder.R;

public class search extends Activity {

    private EditText name;
    private EditText street;
    private EditText postcode;
    private EditText place;
    final Context context = this;
    private Intent i;
    private errorHandling eHandling;
    private messageDialog mDialog;
    public String array_spinner[];
    public Spinner type;
    public String selected_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Setze die Spinner Optionen
        array_spinner=new String[5];
        // Spinner Items aufbauen
        try {
            getSpinnerItems items = new getSpinnerItems(true);
            array_spinner = items.buildItems(true);
        }catch (Exception e){
            // TODO Fehlerhandling
        }
        type = (Spinner) findViewById(R.id.location_spinner);
        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, array_spinner);
        type.setAdapter(adapter);

        // Hole alle Edit Felder und setze die Einstellungen
        name = (EditText) findViewById(R.id.searchName);
        name.setFocusable(false);
        name.setClickable(true);
        street = (EditText) findViewById(R.id.searchStreet);
        street.setFocusable(false);
        street.setClickable(true);
        postcode = (EditText) findViewById(R.id.searchPostcode);
        postcode.setFocusable(false);
        postcode.setClickable(true);
        place = (EditText) findViewById(R.id.searchPlace);
        place.setFocusable(false);
        place.setClickable(true);


        // On Click Listener für den Namen der Location
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // hole das Alert Layout
                LayoutInflater layoutInflater = LayoutInflater.from(context);

                View promptView = layoutInflater.inflate(R.layout.alert_name, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                alertDialogBuilder.setView(promptView);

                final EditText input = (EditText) promptView.findViewById(R.id.userInput);

                // Erstelle das Fenster
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Hole den User Input und schreibe ihn ins Textfeld
                                name.setText(input.getText());
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

        // On Click Listener für die Straße der Location
        street.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // hole das Alert Layout
                LayoutInflater layoutInflater = LayoutInflater.from(context);

                View promptView = layoutInflater.inflate(R.layout.alert_street, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                alertDialogBuilder.setView(promptView);

                final EditText input = (EditText) promptView.findViewById(R.id.userInput);

                // Erstelle das Fenster
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Hole den User Input und schreibe ihn ins Textfeld
                                street.setText(input.getText());
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

        // On Click Listener für die Postleitzahl der Location
        postcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // hole das Alert Layout
                LayoutInflater layoutInflater = LayoutInflater.from(context);

                View promptView = layoutInflater.inflate(R.layout.alert_postcode, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                alertDialogBuilder.setView(promptView);

                final EditText input = (EditText) promptView.findViewById(R.id.userInput);

                // Erstelle das Fenster
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Hole den User Input und schreibe ihn ins Textfeld
                                postcode.setText(input.getText());
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

        // On Click Listener für die Ort der Location
        place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // hole das Alert Layout
                LayoutInflater layoutInflater = LayoutInflater.from(context);

                View promptView = layoutInflater.inflate(R.layout.alert_place, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                alertDialogBuilder.setView(promptView);

                final EditText input = (EditText) promptView.findViewById(R.id.userInput);

                // Erstelle das Fenster
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Hole den User Input und schreibe ihn ins Textfeld
                                place.setText(input.getText());
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

    public void button_searchLocation(View v){
        searchLocation();
    }

    private void searchLocation(){
        if(checkForm()){
            JSONObject json = new JSONObject();
            try {
                json.put("action", "search");
                json.put("lat", location.getLatitude());
                json.put("long", location.getLongitude());
                json.put("type", type.getSelectedItem().toString());
                json.put("name", name.getText().toString());
                json.put("street", street.getText().toString());
                json.put("postcode", postcode.getText().toString());

                String respond = home.serverCom.secureCom(json.toString());
                json = new JSONObject(respond);
                displayResults results = new displayResults(respond, "Ergebnisse Suche", context);

            }catch(Exception e){
                // TODO Fehlerbehandlung
            }
        }else{
            mDialog = new messageDialog(context,"Eingaben unvollständig", "Es muss mindestens ein Suchkriterium angegeben sein");
        }
}

    private boolean checkForm(){
        return (checkName() || checkStreet() || checkPostcode() || checkPlace() || checkType());
    }

    // Prüft den Inhalt von Edit Feld "Name"
    private boolean checkName(){
        String sName = name.getText().toString();
        if(sName.isEmpty()){
            return false;
        }
        return true;
    }

    // Prüft den Inhalt von Edit Feld "Strasse"
    private boolean checkStreet(){
        String sStreet = street.getText().toString();
        if(sStreet.isEmpty()){
            return false;

        }
        return true;
    }

    // Prüft den Inhalt von Edit Feld "PLZ"
    private boolean checkPostcode(){
        String sPostcode = postcode.getText().toString();
        if(sPostcode.isEmpty()){
            return false;
        }
        return true;
    }

    // Prüft den Inhalt von Edit Feld "Ort"
    private boolean checkPlace(){
        String sPlace = place.getText().toString();
        if(sPlace.isEmpty()){
            return false;
        }
        return true;
    }

    // Prüft den Inhalt von Spinner "Typ"
    private boolean checkType(){
        System.out.println("Vorher");
        String sType = type.getSelectedItem().toString();
        System.out.println("Nachher");
        if(sType.isEmpty() || sType.trim()=="Alle"){
            return false;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
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
