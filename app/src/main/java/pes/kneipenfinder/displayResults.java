package pes.kneipenfinder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pes on 06.12.2014.
 */

public class displayResults {

    private errorHandling eHandling;

    public displayResults(String respond, String titleBar, final Context context){

        try {

            // Context muss übergeben werden, damit die findViewById weiß, aus welcher Activity sie aufgerufen wird
            Activity activity = (Activity) context;

            // Titel der Ergebnisliste setzen
            activity.getActionBar().setTitle(titleBar);
            JSONObject json = new JSONObject();
            // CRLF
            String lineSep = System.getProperty("line.separator");
            json = new JSONObject(respond);
            Boolean status = json.getBoolean("status");
            if(!status){
                eHandling = new errorHandling(context,"", "Es ist ein unerwarteter Fehler aufgetreten", "GPS");
            }else{
                final ListView listView = (ListView) activity.findViewById(R.id.listView);
                final ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
                HashMap<String, String> map = new HashMap<String, String>();
                JSONArray locations = json.getJSONArray("locations");
                for(int i = 0; i < locations.length(); i++){
                    JSONObject location = locations.getJSONObject(i);
                    map = new HashMap<String, String>();
                    map.put("LocationID", location.getString("id").trim());
                    map.put("name", location.getString("name").trim() + lineSep + location.getString("street").trim());
                    map.put("distance", location.getString("distance").trim() + lineSep + "KM");
                    map.put("orientation", location.getString("orientation").trim());
                    mylist.add(map);
                    };
                SimpleAdapter mSchedule = new SimpleAdapter(context, mylist, R.layout.row,
                        new String[] {"name", "distance", "orientation"}, new int[] {R.id.name, R.id.distance, R.id.orientation});
                listView.setAdapter(mSchedule);

                // Neue Activity zum Anzeigen der geklickten Kneipe
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {

                        Intent i = new Intent(context.getApplicationContext(), displayLocationDetailed.class);
                        Bundle bundle = new Bundle();
                        Map value = mylist.get(position);
                        String lid = value.get("LocationID").toString();
                        bundle.putInt("LocationID", Integer.parseInt(lid));
                        i.putExtras(bundle);
                        context.startActivity(i);
                    }
                });
            }
        }catch(Exception e){
            // TODO Fehlerbehandlung
            System.out.println(e.toString());
        }
    }
}
