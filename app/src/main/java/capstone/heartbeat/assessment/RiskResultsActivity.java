package capstone.heartbeat.assessment;

import android.support.v7.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.github.lzyzsd.circleprogress.DonutProgress;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import capstone.heartbeat.controllers.GoalAdapter;
import capstone.heartbeat.controllers.HeartBeatDB;
import capstone.heartbeat.controllers.ListAdapter;
import capstone.heartbeat.models.Goal;
import capstone.heartbeat.others.AddPlanActivity;
import capstone.heartbeat.R;
import capstone.heartbeat.calculators.QStrokeFemale;
import capstone.heartbeat.calculators.QStrokeMale;
import capstone.heartbeat.calculators.Qrisk2Female;
import capstone.heartbeat.calculators.Qrisk2Male;

public class RiskResultsActivity extends AppCompatActivity {

    private DonutProgress heartattack,stroke;
    private SharedPreferences prefs ;
    public double age,sbp,dbp,totalchl, hdl, height,weight;
    public int smoke,af, diabType1,diabType2,fhcvd,ra,CKD,CHF, HA, VHD,bptreatment,gender ;
    public int ha, st,nha,nst;

    ArrayList <String> goals;
    ListAdapter adapter;
    Button btn_lets, btn_cancel;


    ArrayAdapter <String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_risk_results);

        prefs = getSharedPreferences("values",MODE_PRIVATE);
        SharedPreferences.Editor ed = prefs.edit();

        heartattack =(DonutProgress)findViewById(R.id.progress_heartattack);
        stroke = (DonutProgress)findViewById(R.id.progress_stroke);

        age = (double)prefs.getInt("age",0);
        sbp = (double)prefs.getInt("sbp",0);
        dbp = (double)prefs.getInt("dbp",0);
        totalchl =(double)prefs.getInt("chl",0);
        hdl =(double)prefs.getInt("hdl",0);
        height =(double)prefs.getInt("height",0);
        weight =(double)prefs.getInt("weight",0);
        smoke = prefs.getInt("smoke_type",0);
        af = prefs.getInt("irregular",0);
        diabType1 =prefs.getInt("type1",0);
        fhcvd = prefs.getInt("fhcvd",0);
        ra = prefs.getInt("rheumatoid",0);
        CKD = prefs.getInt("chronic",0);
        VHD = prefs.getInt("valvular",0);
        HA = prefs.getInt("heartattack",0);
        bptreatment = prefs.getInt("bptr",0);
        diabType2 = 0;
        CHF = prefs.getInt("congestive",0);
        gender = prefs.getInt("gender",0);

        final int bool[]={5,smoke,af,diabType1,diabType2,fhcvd,ra,bptreatment};
        final double continuous[]={age,sbp,dbp,totalchl,hdl,height,weight};

        final int normalBool[]={5,0,af,diabType1,diabType2,fhcvd,ra,0};
        final double normalContinuous[]={age,120,200,60,170,65};


        Qrisk2Male hm = new Qrisk2Male();
        Qrisk2Female hf = new Qrisk2Female();
        QStrokeMale sm = new QStrokeMale();
        QStrokeFemale sf = new QStrokeFemale();

        double heartAttack=0,Stroke=0,normalHeartAttack=0,normalStroke=0;
        if (gender==0){
            heartAttack = hf.getResult(continuous,bool);
            Stroke = sf.getResult(continuous,bool,VHD,CKD,CHF,HA);
            normalHeartAttack = hf.getResult(normalContinuous,normalBool);
            normalStroke = sf.getResult(normalContinuous,normalBool,VHD,CKD,CHF,HA);
            ha = (int) Math.floor(heartAttack);
            st = (int)Math.floor(Stroke);
            nha = (int) Math.floor(normalHeartAttack);
            nst = (int)Math.floor(normalStroke);
            ed.putInt("haResult",ha);
            ed.putInt("stResult",st);
            ed.putInt("normalHA",nha);
            ed.putInt("normalST",nst);
            ed.commit();
            heartattack.setProgress((float)ha);
            stroke.setProgress((float)st);

        }else if(gender==1){
            heartAttack = hm.getResult(continuous,bool);
            Stroke = sm.getResult(continuous,bool,VHD,CKD,CHF,HA);
            normalHeartAttack = hf.getResult(normalContinuous,normalBool);
            normalStroke = sf.getResult(normalContinuous,normalBool,VHD,CKD,CHF,HA);
            ha = (int) Math.floor(heartAttack);
            st = (int)Math.floor(Stroke);
            nha = (int) Math.floor(normalHeartAttack);
            nst = (int)Math.floor(normalStroke);
            ed.putInt("haResult",ha);
            ed.putInt("stResult",st);
            ed.putInt("normalHA",nha);
            ed.putInt("normalST",nst);
            ed.commit();
            heartattack.setProgress((float)ha);
            stroke.setProgress((float)st);
        }

        HeartBeatDB db = new HeartBeatDB(getApplicationContext());
        db.open();



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.proceed_button, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up next_button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.proceed) {
            final AlertDialog d = new AlertDialog.Builder(RiskResultsActivity.this)
                    .setTitle("Goals")
                    .setView(R.layout.goals_dialog)
                    .create();

            d.show();

            btn_lets = (Button) d.findViewById(R.id.goal_lets);
            btn_cancel = (Button) d.findViewById(R.id.goal_cancel);

            ListView lvMain = (ListView) d.findViewById(R.id.lv_goals);

            ArrayList<Goal> list = new ArrayList<>();

            String [] goals_dummy = new String [] {"Goal 1", "Goal 2", "Goal 3"};
            ArrayList<String> dummygoals = new ArrayList<String>();
            dummygoals.addAll(Arrays.asList(goals_dummy));

            GoalAdapter goalAdapter = new GoalAdapter(getApplicationContext(), list);
            arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_goals, R.id.title_goals, dummygoals);

            lvMain.setAdapter(arrayAdapter);

            lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });

            btn_lets.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddPlanActivity.class));
                finish();
                }
            });

            btn_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    d.hide();
                }
            });
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
