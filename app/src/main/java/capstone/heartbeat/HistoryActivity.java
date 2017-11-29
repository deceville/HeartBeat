package capstone.heartbeat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
<<<<<<< HEAD
=======
import android.view.ViewGroup;
>>>>>>> e81ae8bfaf791b8f26d8f5ecf2023ac5d7d59139
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

public class HistoryActivity extends AppCompatActivity {
<<<<<<< HEAD
    private Button diabetes_t1no,diabetes_t1yes,famhistory_yes,famhistoy_no;
    private Switch congestive,heartattack,valvular,irrefular,rheumatoid,chronic;
    private int type1,cong,ha,val,irr,rhe,chr,fhcvd;
    private SharedPreferences prefs = getSharedPreferences("values",MODE_PRIVATE);
    private SharedPreferences.Editor editor = prefs.edit();
=======
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
>>>>>>> e81ae8bfaf791b8f26d8f5ecf2023ac5d7d59139

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

<<<<<<< HEAD
        diabetes_t1no = (Button)findViewById(R.id.btn_diabetes_no);
        diabetes_t1yes = (Button)findViewById(R.id.btn_diabetes_yes);
        famhistory_yes =(Button)findViewById(R.id.btn_famhistory_yes);
        famhistoy_no = (Button)findViewById(R.id.btn_famhistory_no);
        congestive = (Switch)findViewById(R.id.sw_congestive);
        heartattack =(Switch)findViewById(R.id.sw_heartattack);
        valvular = (Switch)findViewById(R.id.sw_valvular);
        irrefular = (Switch)findViewById(R.id.sw_irregular);
        rheumatoid =(Switch)findViewById(R.id.sw_rheumatoid);
        chronic = (Switch)findViewById(R.id.sw_chronic);

        diabetes_t1yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type1 = 1;
                editor.putInt("type1",type1);
                editor.commit();
            }
        });
        diabetes_t1no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type1 = 0;
                editor.putInt("type1",type1);
                editor.commit();
            }
        });
        famhistory_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fhcvd = 1;
                editor.putInt("fhcvd",fhcvd);
                editor.commit();
            }
        });
        famhistoy_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fhcvd = 0;
                editor.putInt("fhcvd",fhcvd);
                editor.commit();
            }
        });
        congestive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
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
        heartattack.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    ha = 1;
                    editor.putInt("heartattack",ha);
                    editor.commit();
                }else {ha =0;
                    editor.putInt("heartattack",ha);
                    editor.commit();}
            }
        });
        valvular.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    val = 1;
                    editor.putInt("valvular",val);
                    editor.commit();
                }else {val = 0;
                    editor.putInt("valvular",val);
                    editor.commit();}
            }
        });
        irrefular.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    irr=1;
                    editor.putInt("irregular",irr);
                    editor.commit();
                }else {irr =0;
                    editor.putInt("irregular",irr);
                    editor.commit();}
            }
        });
        rheumatoid.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    rhe = 1;
                    editor.putInt("rheumatoid",rhe);
                    editor.commit();
                }else {rhe =0;
                    editor.putInt("rheumatoid",rhe);
                    editor.commit();}
            }
        });
        chronic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    chr =1;
                    editor.putInt("chronic",chr);
                    editor.commit();
                }else {chr = 0;
                    editor.putInt("chronic",chr);
                    editor.commit();}
            }
        });




=======
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
                if(viewGroupIsVisible || (diabetes_yes.getVisibility() == View.GONE)){
                    diabetes_yes.setVisibility(View.VISIBLE);
                }else{
                    diabetes_yes.setVisibility(View.GONE);
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
            }
        });

        getSwitchValues();

        btn_famhistory_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                famhistory = true;
            }
        });

        btn_famhistory_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                famhistory = false;
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
>>>>>>> e81ae8bfaf791b8f26d8f5ecf2023ac5d7d59139
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
<<<<<<< HEAD

=======
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
>>>>>>> e81ae8bfaf791b8f26d8f5ecf2023ac5d7d59139
            startActivity(new Intent(getApplicationContext(),RiskResultsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
