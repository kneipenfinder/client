package pes.kneipenfinder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import pes.kneipenfinder.R;

public class rateSingleLocation extends Activity {

    private Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.rate, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id) {
            case R.id.action_home:
                // Home
                setContentView(R.layout.activity_home);
                break;
            case R.id.action_settings:
                // Einstellungen
                i = new Intent(getApplicationContext(), settings.class);
                startActivity(i);
                break;
            case R.id.action_socialmedia:
                // Social Media --> Facebook, Twitter, Google+
                i = new Intent(getApplicationContext(), socialMedia.class);
                startActivity(i);
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
