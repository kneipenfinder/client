package pes.kneipenfinder;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import pes.kneipenfinder.R;

public class socialMedia extends Activity {

    private Intent i;
    private String gProfile = "";
    private String tProfile = "";
    private String fProfile = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_media);
    }

    public void button_openFacebook(View v){
            try{
                String facebookScheme = "fb://profile/" + fProfile;
                Intent facebookIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(facebookScheme));
                startActivity(facebookIntent);
            } catch (ActivityNotFoundException e) {
                Intent facebookIntent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.facebook.com/profile.php?id="+fProfile));
                startActivity(facebookIntent);
            }
    }

    public void button_openTwitter(View v){
        try {
            this.getPackageManager().getPackageInfo("com.twitter.android", 0);
            i = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?user_id="+tProfile));
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } catch (Exception e) {
            i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/"+tProfile));
        }
        this.startActivity(i);
    }

    public void button_openGoogle(View v){
        try {
            i = new Intent(Intent.ACTION_VIEW);
            i.setClassName("com.google.android.apps.plus",
                    "com.google.android.apps.plus.phone.UrlGatewayActivity");
            i.putExtra("customAppUri", gProfile);
            startActivity(i);
        } catch(ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://plus.google.com/"+gProfile+"/posts")));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.social_media, menu);
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
            case R.id.action_impressum:
                // Impressum
                i = new Intent(getApplicationContext(), impressum.class);
                startActivity(i);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
