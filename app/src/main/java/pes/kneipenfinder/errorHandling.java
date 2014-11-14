package pes.kneipenfinder;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by pes on 14.11.2014.
 */
public class errorHandling {
    // Konstruktor
    public errorHandling(Context context, String message, String errorSource){
        String errorCode = getErrorCode(errorSource);
        if(errorCode == ""){
            userError(context,message);
        }else{
            unexpectedError(context,message,errorCode);
        }
    }

    // Errorcode holen, wenn unerwarteter Fehler aufgetreten ist
    public String getErrorCode(String errorSource){
        if(errorSource == "Handshake"){
            return "1111";
        }else if (errorSource == ""){
            return "";
        }else if(errorSource == "Schlüssel"){
            return "2222";
        }else if(errorSource == "Verschlüsseln"){
            return  "3333";
        }else if(errorSource == "Entschlüsseln"){
            return "4444";
        }else if(errorSource == "GPS"){
            return "5555";
        }
        else{
            return "6666";
        }
    }

    // Benutzerbedingter Fehler
    public void userError(Context context, String message){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage("Es ist ein Fehler aufgetreten: " + message);
        builder1.setCancelable(true);
        builder1.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    // Unerwarteter Fehler
    public void unexpectedError(Context context, String message, String errorcode){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage("Es ist ein Fehler aufgetreten: " + message + " ( " + errorcode + " ) ");
        builder1.setCancelable(true);
        builder1.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

}
