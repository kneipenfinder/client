package pes.kneipenfinder;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by pes on 06.12.2014.
 */

public class displayResults {

    private errorHandling eHandling;

    public displayResults(String respond,String titleBar, Context context){

        try {

            // Context muss übergeben werden, damit die findViewById weiß, aus welcher Activity sie aufgerufen wird
            Activity activity = (Activity) context;

            // Titel der Ergebnisliste setzen
            activity.getActionBar().setTitle(titleBar);

            JSONObject json = new JSONObject();
            json = new JSONObject(respond);
            Boolean status = json.getBoolean("status");
            if(!status){
                eHandling = new errorHandling(context,"", "Es ist ein unerwarteter Fehler aufgetreten", "GPS");
            }else{

                activity.setContentView(R.layout.activity_find);
                final TableLayout tableLayout = (TableLayout) activity.findViewById(R.id.find_table);

                JSONArray locations = json.getJSONArray("locations");
                for(int i = 0; i < locations.length(); i++){

                    JSONObject location = locations.getJSONObject(i);

                    final TableRow tableRow = new TableRow(context);
                    tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));


                    final TextView text = new TextView(context);
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
                    text.setBackground(activity.getResources().getDrawable(R.drawable.find_table));
                    tableRow.addView(text);

                    final TextView text2 = new TextView(context);
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
                    text2.setBackground(activity.getResources().getDrawable(R.drawable.find_table));
                    tableRow.addView(text2);

                    final TextView text3 = new TextView(context);
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
                    text3.setBackground(activity.getResources().getDrawable(R.drawable.find_table));
                    tableRow.addView(text3);


                    tableLayout.addView(tableRow);
                }
            }
        }catch(Exception e){
            // TODO Fehlerbehandlung
        }
    }
}
