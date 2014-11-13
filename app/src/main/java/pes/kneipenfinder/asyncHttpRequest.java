package pes.kneipenfinder;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Peter and Leif on 18.10.2014.
 * Version: 1
 */
public class asyncHttpRequest extends AsyncTask<String, String, String> {

    private Context context;
    ProgressDialog mDialog;

    public asyncHttpRequest(String serverURL, String parameters, Context context) {
        this.context = context;
        String response = doInBackground(serverURL,parameters);
    }

    protected void onPreExecute(){
        mDialog = new ProgressDialog(context);
        mDialog.setMessage("Ergebnisse werden geladen...");
        mDialog.setCancelable(false);
        mDialog.show();
    }


    // Methode für Request. Diese wird im Hintergrund ausgeführt
    protected String doInBackground(String... urls) {;
        try{
            String serverURL = urls[0];
            String parameters = urls[1];
            URL url = new URL(serverURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length", "" + Integer.toString(parameters.getBytes().length));
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            DataOutputStream output = new DataOutputStream(connection.getOutputStream());
            output.writeBytes(parameters);
            output.flush();
            output.close();

            InputStream input = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            StringBuffer response = new StringBuffer();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            reader.close();
            return response.toString();
        } catch (Exception e) {
            return (e.toString());
        }
    }

    @Override
    protected void onPostExecute(String result){
        mDialog.dismiss();
    }
}
