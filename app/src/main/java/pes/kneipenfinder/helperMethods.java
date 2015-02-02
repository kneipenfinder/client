package pes.kneipenfinder;

import android.content.Context;
import android.graphics.Point;
import android.provider.Settings;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by pes on 28.12.2014.
 *
 *  Note: In dieser Klasse befinden sich alle Helfer Methoden.
 */
public class helperMethods {

    public static int getScreenWidth(Context context) {
        int columnWidth;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        final Point point = new Point();
        try {
            display.getSize(point);
        } catch (java.lang.NoSuchMethodError ignore) { // Older device
            point.x = display.getWidth();
            point.y = display.getHeight();
        }
        columnWidth = point.x;
        return columnWidth;
    }

    public static String getServerURL(){
        String serverURL = "http://api.kneipen-finder.de/";
        return serverURL;
    }

    public static String getServerURLForPictures(){
        String serverURL = "http://www.kneipen-finder.de/";
        return serverURL;
    }

    public static String getDeviceID(Context context){
        String id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        return id;
    }

    public static void closeApp(){
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
}
