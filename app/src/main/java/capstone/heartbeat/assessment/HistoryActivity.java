package capstone.heartbeat.assessment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import capstone.heartbeat.R;

public class HistoryActivity extends AppCompatActivity {
    private Button diabetes_t1no,diabetes_t1yes,famhistory_yes,famhistoy_no;
    private Switch congestive,heartattack,valvular,irrefular,rheumatoid,chronic;
    private int dia,type2,type1,cong,ha,val,irr,rhe,chr,fhcvd;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor ;

    public Button btn_diabetes_no, btn_diabetes_yes, btn_famhistory_no, btn_famhistory_yes;
    public ViewGroup diabetes_yes;
    public Switch sw_congestive, sw_heartattack, sw_valvular, sw_irregular, sw_rheumatoid, sw_chronic;
    public Switch sw_diabetes_type1, sw_diabetes_type2;


    private boolean viewGroupIsVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        prefs = getSharedPreferences("values",MODE_PRIVATE);
        editor = prefs.edit();

        diabetes_yes = (ViewGroup) findViewById(R.id.diabetes_yes);
        btn_diabetes_no = (Button) findViewById(R.id.btn_diabetes_no);
        btn_diabetes_yes = (Button) findViewById(R.id.btn_diabetes_yes);

        btn_famhistory_no = (Button) findViewById(R.id.btn_famhistory_no);
        btn_famhistory_yes = (Button) findViewById(R.id.btn_famhistory_yes);

        sw_diabetes_type1 = (Switch) findViewById(R.id.sw_diabetes_type1);
        sw_diabetes_type2 = (Switch) findViewById(R.id.sw_diabetes_type2);

        getSwitchValues();

        btn_diabetes_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dia = 1;
                btn_diabetes_yes.setSelected(true);
                btn_diabetes_no.setSelected(false);
                if(viewGroupIsVisible || (diabetes_yes.getVisibility() == View.GONE)){
                    diabetes_yes.setVisibility(View.VISIBLE);
                }else{
                    diabetes_yes.setVisibility(View.GONE);
                    btn_diabetes_yes.setSelected(false);
                    viewGroupIsVisible = false;
                }
                editor.putInt("dia",dia);
                editor.commit();
            }
        });

        sw_diabetes_type2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    type2 = 1;
                    editor.putInt("type2",type2);
                    editor.commit();
                }else{
                    type2 = 0;
                    editor.putInt("type2",type2);
                    editor.commit();
                }
            }
        });

        sw_diabetes_type1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    type1 = 1;
                    editor.putInt("type1",type1);
                    editor.commit();
                }else{
                    type1 = 0;
                    editor.putInt("type1",type1);
                    editor.commit();
                }
            }
        });

        btn_diabetes_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dia = 0;
                btn_diabetes_no.setSelected(true);
                btn_diabetes_yes.setSelected(false);
                if(viewGroupIsVisible || (diabetes_yes.getVisibility() == View.VISIBLE)){
                    diabetes_yes.setVisibility(View.GONE);
                    viewGroupIsVisible = false;
                }
                editor.putInt("dia",dia);
                editor.commit();
            }
        });

        btn_famhistory_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fhcvd = 1;
                btn_famhistory_yes.setSelected(true);
                btn_famhistory_no.setSelected(false);
                editor.putInt("fhcvd",fhcvd);
                editor.commit();
            }
        });

        btn_famhistory_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fhcvd = 0;
                btn_famhistory_yes.setSelected(false);
                btn_famhistory_no.setSelected(true);
                editor.putInt("fhcvd",fhcvd);
                editor.commit();
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
                if (isChecked){
                    chr = 1;
                    editor.putInt("chronic",chr);
                    editor.commit();
                }else {
                    chr = 0;
                    editor.putInt("chronic",chr);
                    editor.commit();
                }
            }
        });

        sw_congestive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    cong = 1;
                    editor.putInt("congestive",cong);
                    editor.commit();
                }else {
                    cong = 0;
                    editor.putInt("congestive",cong);
                    editor.commit();
                }
            }
        });

        sw_valvular.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    val = 1;
                    editor.putInt("valvular",val);
                    editor.commit();
                }else {
                    val = 0;
                    editor.putInt("valvular",val);
                    editor.commit();
                }
            }
        });

        sw_rheumatoid.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    rhe = 1;
                    editor.putInt("rheumatoid",rhe);
                    editor.commit();
                }else {
                    rhe = 0;
                    editor.putInt("rheumatoid",rhe);
                    editor.commit();
                }
            }
        });

        sw_irregular.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    irr = 1;
                    editor.putInt("irregular",irr);
                    editor.commit();
                }else {
                    irr = 0;
                    editor.putInt("irregular",irr);
                    editor.commit();
                }
            }
        });

        sw_heartattack.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    ha = 1;
                    editor.putInt("heartattack",ha);
                    editor.commit();
                }else {
                    ha = 0;
                    editor.putInt("heartattack",ha);
                    editor.commit();
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
            startActivity(new Intent(getApplicationContext(),RiskResultsActivity.class));
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
