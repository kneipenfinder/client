package pes.kneipenfinder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by pes on 06.12.2014.
 */
public class getSpinnerItems {

    private JSONObject types;

    public getSpinnerItems(boolean addAll) throws JSONException {
        buildItems(addAll);
    }

    public String[] buildItems(boolean addAll) throws JSONException {

        getItemsFromDB();
        System.out.println(types);

        String[] arraySpinner=new String[5];

        JSONArray locations = types.getJSONArray("locationTypes");
        Integer i = 0;
        for(int j = i; j < locations.length(); j++) {
            JSONObject location = locations.getJSONObject(j);
            arraySpinner[j] = location.getString("name");
            i=j;
        }
        if(addAll){
            arraySpinner[i+1] = "Alle";
        }

        /*arraySpinner[0]="Alle";
        arraySpinner[1]="Kneipe";
        arraySpinner[2]="Diskothek";
        arraySpinner[3]="Bar";
        arraySpinner[4]="option 4";*/
        return arraySpinner;
    }

    public void getItemsFromDB(){
        JSONObject json = new JSONObject();
        try {
            json.put("action", "getLocationTypes");

            String respond = home.serverCom.secureCom(json.toString());
            json = new JSONObject(respond);

            if(json.getBoolean("status")){
                types = json;
            }

        }catch(Exception e){
            // TODO Fehlerbehandlung
        }
    }
}
