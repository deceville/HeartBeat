package capstone.heartbeat.account;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import capstone.heartbeat.R;
import capstone.heartbeat.controllers.FileHelper;
import capstone.heartbeat.controllers.HeartBeatDB;
import capstone.heartbeat.models.User;

public class EditProfileActivity extends AppCompatActivity {
    private TextView prof_fullname,prof_username,prof_birth,prof_sleep;
    private SharedPreferences prefs;
    private HeartBeatDB mydb;
    private int ids;
    private User dece;
    private String user,name,birth,sleep;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        prefs = getSharedPreferences("login",MODE_PRIVATE);
         ids = prefs.getInt("id",0);

        prof_fullname = (TextView) findViewById(R.id.edit_fullname);
        prof_username = (TextView) findViewById(R.id.edit_username);
        prof_birth = (TextView) findViewById(R.id.edit_birthday);
        prof_sleep = (TextView) findViewById(R.id.edit_sleeptime);

        mydb = new HeartBeatDB(getApplicationContext());
        mydb.open();
        dece = mydb.getUserAssessData(ids);
        prof_fullname.setText(dece.name);
        prof_username.setText(dece.username);
        prof_birth.setText(dece.birth);
        prof_sleep.setText(dece.sleep);

        user = prof_username.getText().toString();
        name = prof_fullname.getText().toString();
        birth = prof_birth.getText().toString();
        sleep = prof_sleep.getText().toString();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.save_button, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up next_button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String time = sdf.format(new Date());
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.save) {
            mydb = new HeartBeatDB(getApplicationContext());
            mydb.updateUser(user,name,"02/15/93",sleep,this.ids);
            FileHelper.saveChangeToFile(user,name,"02/15/93",sleep,time);
            mydb.close();
            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
