package pes.kneipenfinder;

import android.util.Property;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

/**
 * Created by pes on 28.10.2014.
 */
public class AppProperties {

    Properties prop = new Properties();
    String propFile = "app.properties";

    // Im Konstruktor direkt alle Properties auslesen
    public AppProperties(){
        try {
            BufferedInputStream stream = new BufferedInputStream(new FileInputStream(propFile));
            prop.load(stream);
            stream.close();
        }catch (Exception e){
            System.out.println("Fehler beim Holen der Properties");
        }
    }

    // Einzelne Properties holen
    // Wenn User-Property nicht gefunden wurde, wird automatisch die Default-Property verwendet
    public String getProperties(String key) throws FileNotFoundException {
        String userKey = "u_" + key;
        String defaultKey = "d_" + key;
        if(prop.getProperty(userKey) != null) {
            String property = prop.getProperty(userKey);
            return property;
        }else{
            String property = prop.getProperty(defaultKey);
            return property;
        }
    }

    // Einzelne User-Properties setzen --> Default-Properties werden nie ueberschrieben
    public void setProperties(String key, String value){
        String userKey = "u_" + key;
        prop.setProperty(userKey, value);
    }
}
