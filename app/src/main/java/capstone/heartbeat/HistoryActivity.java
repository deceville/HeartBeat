package capstone.heartbeat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

public class HistoryActivity extends AppCompatActivity {
    private Button diabetes_t1no,diabetes_t1yes,famhistory_yes,famhistoy_no;
    private Switch congestive,heartattack,valvular,irrefular,rheumatoid,chronic;
    private int type1,cong,ha,val,irr,rhe,chr,fhcvd;
    private SharedPreferences prefs = getSharedPreferences("values",MODE_PRIVATE);
    private SharedPreferences.Editor editor = prefs.edit();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
