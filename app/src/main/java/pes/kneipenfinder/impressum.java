package pes.kneipenfinder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import pes.kneipenfinder.R;

public class impressum extends Activity {

    private Intent i;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_impressum);
    }

    // Mail an App-Authoren
    public void button_mailto(View v){
        i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{"peter.sieverding@googlemail.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, "E-Mail zur App Kneipen-Finder");
        i.putExtra(Intent.EXTRA_TEXT, "Text eingeben...");
        context.startActivity(Intent.createChooser(i, "Sende E-mail..."));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.impressum, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
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
