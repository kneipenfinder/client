package pes.kneipenfinder;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Properties;

/**
 * Created by pes on 28.10.2014.
 */
public class AppProperties {

    Properties prop = new Properties();
    String propFile = "app.properties";

    // Im Konstruktor direkt alle Properties auslesen
    public AppProperties(AssetManager assetManager){

        try {
            InputStream stream = assetManager.open(propFile);
            prop.load(stream);
            stream.close();
        }catch(Exception e){
            System.out.println("error: "+e.toString());
        }

    }

    // Einzelne Properties holen
    // Wenn User-Property nicht gefunden wurde, wird automatisch die Default-Property verwendet
    public String getProp(String key){
        String userKey = "u_" + key;
        String defaultKey = "d_" + key;
        if(prop.getProperty(userKey).length() != 0) {
            String property = prop.getProperty(userKey);
            return property;
        }else if(prop.getProperty(defaultKey).length() != 0){
            String property = prop.getProperty(defaultKey);
            return property;
        }else{
            return "Property nicht gefunden";
        }
    }

    // Einzelne User-Properties setzen --> Default-Properties werden nie ueberschrieben
    public void setProp(String key, String value){
        String userKey = "u_" + key;
        prop.setProperty(userKey, value);
        try {
            FileOutputStream out = new FileOutputStream(propFile);
            prop.store(out, null);
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }
}
