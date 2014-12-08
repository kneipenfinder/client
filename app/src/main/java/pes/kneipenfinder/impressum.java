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
        TextView impressum = (TextView) findViewById(R.id.impress);
        impressum.setText(getString(R.string.impressum_text));
    }

    // Mail an App-Authoren
    public void button_mailto(View v){
        final Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"peter.sieverding@googlemail.com"});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "E-Mail zur App Kneipen-Finder");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Text eingeben...");
        context.startActivity(Intent.createChooser(emailIntent, "Sende E-mail..."));
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
        }
            return super.onOptionsItemSelected(item);
        }
}
