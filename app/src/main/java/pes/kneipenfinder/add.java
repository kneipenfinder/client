package pes.kneipenfinder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class add extends Activity {

    EditText name;
    EditText street;
    EditText postcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
    }

    public void button_submitLocation(View v){
        addLocation();
    }

    private void addLocation(){
        name = (EditText) findViewById(R.id.addName);
        street = (EditText) findViewById(R.id.addStreet);
        postcode = (EditText) findViewById(R.id.addPostcode);
        if(checkForm()){
            // TODO: Json Object erstellen mit den Daten der Kneipe und an Server senden
        }
    }

    private boolean checkForm(){
        return (checkName() && checkStreet() && checkPostcode());
    }

    private boolean checkName() {
        String sName = name.getText().toString();
        if(sName.isEmpty()){
            return false;
        }
        return true;
    }

    private boolean checkStreet() {
        String sStreet = street.getText().toString();
        if(sStreet.isEmpty()){
            return false;
        }
        return true;
    }

    private boolean checkPostcode() {
        String sPostcode = postcode.getText().toString();
        if(sPostcode.isEmpty()){
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
