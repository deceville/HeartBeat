package capstone.heartbeat.account;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import capstone.heartbeat.R;
import capstone.heartbeat.controllers.HeartBeatDB;
import capstone.heartbeat.models.User;

public class ProfileActivity extends AppCompatActivity {

    private TextView prof_fullname,prof_username,prof_birth,prof_sleep;
    private SharedPreferences prefs;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        prefs = getSharedPreferences("login",MODE_PRIVATE);
        int id = prefs.getInt("id",0);

        prof_fullname = (TextView) findViewById(R.id.profile_fullname);
        prof_username = (TextView) findViewById(R.id.profile_username);
        prof_birth = (TextView) findViewById(R.id.profile_birthday);
        prof_sleep = (TextView) findViewById(R.id.profile_sleeptime);

        HeartBeatDB mydb = new HeartBeatDB(getApplicationContext());
        mydb.open();
        User dece = new User();
        dece = mydb.getUserAssessData(id);
        prof_fullname.setText(dece.name);
        prof_username.setText(dece.username);
        prof_birth.setText(dece.birth);
        prof_sleep.setText(dece.sleep);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_button, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up next_button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.edit) {
            startActivity(new Intent(getApplicationContext(),EditProfileActivity.class));
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
