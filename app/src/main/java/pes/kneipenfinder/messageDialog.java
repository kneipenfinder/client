package pes.kneipenfinder;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by pes on 02.12.2014.
 */
public class messageDialog {

    public messageDialog(Context context, String title,String message){
        mDialog(context,title, message);
    }

    // Benutzerbedingter Fehler
    public void mDialog(Context context,String title, String message){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        if(!title.isEmpty()){
            builder1.setTitle(title);
        }
        builder1.setTitle(title);
        builder1.setMessage(message);
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
