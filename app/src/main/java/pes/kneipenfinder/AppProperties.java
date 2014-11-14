package pes.kneipenfinder;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.preference.PreferenceManager;


/**
 * Created by Peter on 28.10.2014.
 */
public class AppProperties {

    public errorHandling eHandling;

    // Einzelne Properties holen
    // Wenn User-Property nicht gefunden wurde, wird automatisch die Default-Property verwendet
    public String getProp(String key, Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String name = preferences.getString(key,"");
        if(!name.equalsIgnoreCase(""))
        {
            return name;
        }else{
            return "";
        }
    }

    // Einzelne User-Properties setzen
    public boolean setProp(String key, String value, Context context){
        if (key == "radius"){
            if(isInteger(key)){
                setPropInFile(key,value, context);
                return true;
            }else {
                eHandling = new errorHandling(context,"Es wurde keine gültige Zahl eingegeben", "");
                return false;
            }
        }else if (key == "result"){
            if(isInteger(key)){
                setPropInFile(key, value, context);
                return true;
            }else {
                eHandling = new errorHandling(context, "Es wurde keine gültige Zahl eingegeben", "");
                return false;
            }
        }else{
            setPropInFile(key,value,context);
            return true;
        }
    }

    // Hier die Properties setzen
    public void setPropInFile(String key, String value, Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key,value);
        editor.apply();
    }

    // Properties mit Default-Werten initialisieren
    // Bei Zuruecksetzen auf Default Einstellungen wird diese Methode wieder aufgerufen
    public void initializePref(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("appinit", "appinit");
        editor.putString("radius", "20000");
        editor.putString("result", "10");
        editor.apply();
    }

    // Pruefe, ob wirklich nur Zahlen eingegeben wurden
    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        }
        return true;
    }
}
