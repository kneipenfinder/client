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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import org.json.JSONObject;
import org.xml.sax.ErrorHandler;


public class add extends Activity {

    private EditText name;
    private EditText street;
    private EditText postcode;
    private EditText place;
    final Context context = this;
    private Intent i;
    private errorHandling eHandling;
    private String array_spinner[];
    private Spinner type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        // Setze die Spinner Optionen
        // TODO: Spinner Optionen aus der Datenbanktabelle holen
        array_spinner=new String[5];
        array_spinner[0]="Kneipe";
        array_spinner[1]="Disco";
        array_spinner[2]="Bar";
        array_spinner[3]="option 4";
        array_spinner[4]="option 5";
        Spinner s = (Spinner) findViewById(R.id.location_spinner);
        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, array_spinner);
        s.setAdapter(adapter);

        // Hole alle Edit Felder und setze die Einstellungen
        name = (EditText) findViewById(R.id.addName);
        name.setFocusable(false);
        name.setClickable(true);
        street = (EditText) findViewById(R.id.addStreet);
        street.setFocusable(false);
        street.setClickable(true);
        postcode = (EditText) findViewById(R.id.addPostcode);
        postcode.setFocusable(false);
        postcode.setClickable(true);
        place = (EditText) findViewById(R.id.addplace);
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

    public void button_submitLocation(View v){
        addLocation();
    }

    private void addLocation(){
        name = (EditText) findViewById(R.id.addName);
        street = (EditText) findViewById(R.id.addStreet);
        postcode = (EditText) findViewById(R.id.addPostcode);
        place = (EditText) findViewById(R.id.addplace);
        type = (Spinner) findViewById(R.id.location_spinner);

        if(checkForm()){
            JSONObject json = new JSONObject();
            try {
                json.put("action", "add");
                json.put("lat", location.getLatitude());
                json.put("long", location.getLongitude());
                json.put("type", type.getSelectedItem());
                json.put("name", name.getText().toString());
                json.put("street", street.getText().toString());
                json.put("postcode", postcode.getText().toString());
                json.put("place", place.getText().toString());
            }catch(Exception e){

            }

            String respond = home.serverCom.secureCom(json.toString());
            System.out.println(respond);
        }
    }

    private boolean checkForm(){
        return (checkName() && checkStreet() && checkPostcode() && checkPlace());
    }

    private boolean checkName() {
        String sName = name.getText().toString();
        if(sName.isEmpty()){
            eHandling = new errorHandling(context,"Eingaben nicht korrekt", "Name wurde nicht eingegeben.","");
            return false;
        }
        return true;
    }

    private boolean checkStreet() {
        String sStreet = street.getText().toString();
        if(sStreet.isEmpty()){
            eHandling = new errorHandling(context,"Eingaben nicht korrekt", "Straße wurde nicht eingegeben.","");
        }
        return true;
    }

    private boolean checkPostcode() {
        String sPostcode = postcode.getText().toString();
        if(sPostcode.isEmpty()){
            eHandling = new errorHandling(context,"Eingaben nicht korrekt", "Postleitzahl wurde nicht eingegeben.","");
            return false;
        }
        return true;
    }

    private boolean checkPlace() {
        String sPostcode = place.getText().toString();
        if(sPostcode.isEmpty()){
            eHandling = new errorHandling(context,"Eingaben nicht korrekt" ,"Ort wurde nicht eingegeben.","");
            return false;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add, menu);
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
