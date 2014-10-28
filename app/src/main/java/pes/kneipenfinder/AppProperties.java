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
    public String getProperties(String key) throws FileNotFoundException {
        String property = prop.getProperty(key);
        return property;
    }

    // Einzelne Properties setzen
    public void setProperties(String key, String value){
        prop.setProperty(key, value);
    }
}
