package pes.kneipenfinder;

import android.app.Activity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        findLocation();
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

        String respond = home.serverCom.secureCom(json.toString());
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
                    //if(i != 1) {
                        text.setText(Html.fromHtml("<h5>" + location.getString("name").trim() + "</h5>" + "<br />" + location.getString("street").trim()));
                        text.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                    //}else{
                      //  text.setText("Name und Straße");
                      //  text.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                    //}
                    text.setPadding(1,1,1,1);
                    text.setTextSize(15);
                    text.setTextColor(Color.WHITE);
                    text.setGravity(Gravity.LEFT);
                    text.setBackground(getResources().getDrawable(R.drawable.find_table));
                    tableRow.addView(text);

                    final TextView text2 = new TextView(this);
                    //if(i != 1) {
                        text2.setText(Html.fromHtml("<h4>" + location.getString("distance").trim() + "\n" + "<br />" + "km" + "</h4>"));
                        text2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                    //}else{
                     //   text2.setText("Entfernung");
                     //   text2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                    //}
                    text2.setPadding(1,1,1,1);
                    text2.setTextSize(15);
                    text2.setTextColor(Color.WHITE);
                    text2.setGravity(Gravity.LEFT);
                    text2.setBackground(getResources().getDrawable(R.drawable.find_table));
                    tableRow.addView(text2);

                    final TextView text3 = new TextView(this);
                  //  if(i != 1) {
                        text3.setText(Html.fromHtml("<h4>" + location.getString("orientation").trim()));
                        text3.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                   // }else{
                    //    text3.setText("Richtung");
                       // text3.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                  //  }
                    text3.setPadding(1,1,1,1);
                    text3.setTextSize(15);
                    text3.setTextColor(Color.WHITE);
                    text3.setGravity(Gravity.LEFT);
                    text3.setBackground(getResources().getDrawable(R.drawable.find_table));
                    tableRow.addView(text3);


                    tableLayout.addView(tableRow);

                }

            }

        }catch(Exception e){

        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.find, menu);
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
