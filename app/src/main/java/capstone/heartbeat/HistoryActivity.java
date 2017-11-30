package capstone.heartbeat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

public class HistoryActivity extends AppCompatActivity {
    public Button btn_diabetes_no, btn_diabetes_yes, btn_famhistory_no, btn_famhistory_yes;
    public ViewGroup diabetes_yes;
    public Switch sw_congestive, sw_heartattack, sw_valvular, sw_irregular, sw_rheumatoid, sw_chronic;
    public Switch sw_diabetes_type1, sw_diabetes_type2;

    public boolean diabetes = false;
    public boolean diabetes_type1 = false;
    public boolean diabetes_type2 = false;
    public boolean famhistory = false;
    public boolean congestive = false;
    public boolean heartattack = false;
    public boolean valvular = false;
    public boolean irregular = false;
    public boolean rheumatoid = false;
    public boolean chronic = false;

    private boolean viewGroupIsVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        diabetes_yes = (ViewGroup) findViewById(R.id.diabetes_yes);
        btn_diabetes_no = (Button) findViewById(R.id.btn_diabetes_no);
        btn_diabetes_yes = (Button) findViewById(R.id.btn_diabetes_yes);

        btn_famhistory_no = (Button) findViewById(R.id.btn_famhistory_no);
        btn_famhistory_yes = (Button) findViewById(R.id.btn_famhistory_yes);

        sw_diabetes_type1 = (Switch) findViewById(R.id.sw_diabetes_type1);
        sw_diabetes_type2 = (Switch) findViewById(R.id.sw_diabetes_type2);

        btn_diabetes_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diabetes = true;
                btn_diabetes_yes.setSelected(true);
                btn_diabetes_no.setSelected(false);
                if(viewGroupIsVisible || (diabetes_yes.getVisibility() == View.GONE)){
                    diabetes_yes.setVisibility(View.VISIBLE);
                }else{
                    diabetes_yes.setVisibility(View.GONE);
                    btn_diabetes_yes.setSelected(false);
                    viewGroupIsVisible = false;
                }
            }
        });

        sw_diabetes_type2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    diabetes_type2 = true;
                }else{
                    diabetes_type2 = false;
                }
            }
        });

        sw_diabetes_type1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    diabetes_type1 = true;
                }else{
                    diabetes_type1 = false;
                }
            }
        });

        btn_diabetes_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diabetes = false;
                btn_diabetes_no.setSelected(true);
                btn_diabetes_yes.setSelected(false);
                if(viewGroupIsVisible || (diabetes_yes.getVisibility() == View.VISIBLE)){
                    diabetes_yes.setVisibility(View.GONE);
                    viewGroupIsVisible = false;
                }
            }
        });

        getSwitchValues();

        btn_famhistory_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                famhistory = true;
                btn_famhistory_yes.setSelected(true);
                btn_famhistory_no.setSelected(false);
            }
        });

        btn_famhistory_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                famhistory = false;
                btn_famhistory_yes.setSelected(false);
                btn_famhistory_no.setSelected(true);
            }
        });
    }

    public void getSwitchValues(){
        sw_chronic = (Switch) findViewById(R.id.sw_chronic);
        sw_congestive = (Switch) findViewById(R.id.sw_congestive);
        sw_heartattack = (Switch) findViewById(R.id.sw_heartattack);
        sw_irregular = (Switch) findViewById(R.id.sw_irregular);
        sw_rheumatoid = (Switch) findViewById(R.id.sw_rheumatoid);
        sw_valvular = (Switch) findViewById(R.id.sw_valvular);

        sw_chronic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    chronic = true;
                }else{
                    chronic = false;
                }
            }
        });

        sw_congestive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    congestive = true;
                }else{
                    congestive = false;
                }
            }
        });

        sw_valvular.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    valvular = true;
                }else{
                    valvular = false;
                }
            }
        });

        sw_rheumatoid.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    rheumatoid = true;
                }else{
                    rheumatoid = false;
                }
            }
        });

        sw_irregular.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    irregular = true;
                }else{
                    irregular = false;
                }
            }
        });

        sw_heartattack.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    heartattack = true;
                }else{
                    heartattack = false;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.next_button, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up next_button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.next) {
            SharedPreferences prefs = getSharedPreferences("values",MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("Diabetes", diabetes);
            editor.putBoolean("Diabetes type 1", diabetes_type1);
            editor.putBoolean("Diabetes type 2", diabetes_type2);
            editor.putBoolean("Chronic", chronic);
            editor.putBoolean("Congestive", congestive);
            editor.putBoolean("Valvular", valvular);
            editor.putBoolean("Rheumatoid", rheumatoid);
            editor.putBoolean("Irregular", irregular);
            editor.putBoolean("Heartattack", heartattack);
            editor.putBoolean("Family history", famhistory);
            editor.commit();
            startActivity(new Intent(getApplicationContext(),RiskResultsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
