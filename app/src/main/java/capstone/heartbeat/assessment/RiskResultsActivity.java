package capstone.heartbeat.assessment;

import android.content.ContentValues;
import android.os.Build;
import android.support.annotation.RequiresApi;
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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.DonutProgress;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.text.Format;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import capstone.heartbeat.controllers.GoalAdapter;
import capstone.heartbeat.controllers.HeartBeatDB;
import capstone.heartbeat.controllers.ListAdapter;
import capstone.heartbeat.controllers.ResultEvaluator;
import capstone.heartbeat.models.Goal;
import capstone.heartbeat.models.User;
import capstone.heartbeat.others.AddPlanActivity;
import capstone.heartbeat.R;
import capstone.heartbeat.calculators.QStrokeFemale;
import capstone.heartbeat.calculators.QStrokeMale;
import capstone.heartbeat.calculators.Qrisk2Female;
import capstone.heartbeat.calculators.Qrisk2Male;

public class RiskResultsActivity extends AppCompatActivity {

    private DonutProgress heartattack,stroke;
    private SharedPreferences prefs,use ;
    public double age,sbp,dbp,totalchl, hdl, height,weight;
    public int smoke,af, diabType1,diabType2,fhcvd,ra,CKD,CHF, HA, VHD,bptreatment,gender ,sticks;
    public int ha, st,nha,nst,act,uid;
    public String sleep,birth;
    private DecimalFormat df;

    ArrayList <String> goals;
    ListAdapter adapter;
    Button btn_lets, btn_cancel;


    ArrayAdapter <String> arrayAdapter;

    private ProgressBar bmi_progress , chol_progress, hdl_progress,sbp_progress,smoke_progress;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_risk_results);

        prefs = getSharedPreferences("values",MODE_PRIVATE);
         use = getSharedPreferences("login",MODE_PRIVATE);
        SharedPreferences.Editor ed = prefs.edit();
        df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

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
        diabType2 = prefs.getInt("type2",0);
        CHF = prefs.getInt("congestive",0);
        gender = prefs.getInt("Gender",0);
        sticks = prefs.getInt("sticks",0);
        sleep = prefs.getString("sleep","10:00 PM");
        birth = prefs.getString("birth",null);
        act = prefs.getInt("physical_type",1);
        uid = prefs.getInt("id",0);



        final int bool[]={5,smoke,af,diabType1,diabType2,fhcvd,ra,bptreatment,CKD};
        final double continuous[]={age,sbp,dbp,totalchl,hdl,height,weight};

        final int normalBool[]={5,0,af,diabType1,diabType2,fhcvd,ra,0,CKD};
        final double normalContinuous[]={age,120,80,200,60,170,65};


        Qrisk2Male hm = new Qrisk2Male();
        Qrisk2Female hf = new Qrisk2Female();
        QStrokeMale sm = new QStrokeMale();
        QStrokeFemale sf = new QStrokeFemale();

        double heartAttack=0,Stroke=0,normalHeartAttack=0,normalStroke=0;
        if (gender==0){
            heartAttack = hf.getResult(continuous,bool);
            Stroke = sf.getResult(continuous,bool,VHD,CHF,HA);
            normalHeartAttack = hf.getResult(normalContinuous,normalBool);
            normalStroke = sf.getResult(normalContinuous,normalBool,VHD,CHF,HA);
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
            System.out.println(ha+"  :  "+st);

        }else if(gender==1){
            heartAttack = hm.getResult(continuous,bool);
            Stroke = sm.getResult(continuous,bool,VHD,CHF,HA);
            normalHeartAttack = hf.getResult(normalContinuous,normalBool);
            normalStroke = sf.getResult(normalContinuous,normalBool,VHD,CHF,HA);
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
            System.out.println(ha+"  :  "+st);
        }





    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onStart() {
        super.onStart();

        bmi_progress = (ProgressBar) findViewById(R.id.bmi_progress);
        chol_progress = (ProgressBar) findViewById(R.id.total_progress);
        hdl_progress = (ProgressBar) findViewById(R.id.hdl_progress);
        sbp_progress = (ProgressBar) findViewById(R.id.systolic_progress);
        smoke_progress = (ProgressBar) findViewById(R.id.sticks_progress);
        TextView bmi_desc = (TextView)findViewById(R.id.bmi_description);
        TextView chl_desc = (TextView)findViewById(R.id.chol_description);
        TextView hdl_desc = (TextView)findViewById(R.id.hdl_description);
        TextView sbp_desc = (TextView)findViewById(R.id.sbp_description);
        TextView smoke_desc = (TextView)findViewById(R.id.smoke_description);

        bmi_progress.setMax(50);

        String desc;

                ResultEvaluator assessor = new ResultEvaluator();

        double bmi = assessor.getBMI(weight,height);
        String bmi_string = df.format(bmi);
        bmi_desc.setText(bmi_string);
        bmi_progress.setProgress((int)bmi);

        chol_progress.setMax(300);
        desc = Double.toString(totalchl) + " mm/dl";
        chl_desc.setText(desc);
        chol_progress.setProgress((int)totalchl);

        hdl_progress.setMax((int) totalchl);
        desc = Double.toString(hdl) + " mm/dl";
        hdl_desc.setText(desc);
        hdl_progress.setProgress((int)hdl);

        sbp_progress.setMax(240);
        desc = Double.toString(sbp) + " mm/Hg";
        sbp_desc.setText(desc);
        sbp_progress.setProgress((int)sbp);

        smoke_progress.setMax(30);
        desc = Integer.toString(sticks+1) + " sticks";
        smoke_desc.setText(desc);
        smoke_progress.setProgress(sticks);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


        int id = use.getInt("id",0);
        System.out.println(id);
        HeartBeatDB db = new HeartBeatDB(getApplicationContext());
        db.open();
        User user = new User();
        user.setHeart_attack(ha);
        user.setStroke(st);
        user.setSmoke(smoke);
        user.setSleep(sleep);
        if (gender==0)
        user.setGender("Female");
        else user.setGender("Male");

        user.setFhcvd(fhcvd);
        user.setDiab1(diabType1);
        user.setDiab2(diabType2);
        user.setVhd(VHD);
        user.setRha(ra);
        user.setCkd(CKD);
        user.setDbp((int)dbp);
        user.setSbp((int)sbp);
        user.setHdl((int)hdl);
        user.setChl((int)totalchl);
        user.setChf(CHF);
        user.setBptr(bptreatment);
        user.setBirth(birth);
        user.setAct(act);
        user.setRa(af);
        user.setHa(HA);
        user.setFhcvd(fhcvd);
        user.setWeight(weight);
        user.setHeight(height);
        db.insertUserData(user,id);
        db.close();

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

            //ADD TO DATABASE




            final AlertDialog d = new AlertDialog.Builder(RiskResultsActivity.this)
                    .setTitle("Goals")
                    .setView(R.layout.goals_dialog)
                    .create();

            d.show();

            btn_lets = (Button) d.findViewById(R.id.goal_lets);
            btn_cancel = (Button) d.findViewById(R.id.goal_cancel);


            HeartBeatDB heDB = new HeartBeatDB(getApplicationContext());
            ResultEvaluator e = new ResultEvaluator();
            heDB.open();
            User Dece = new User();
                    Dece = heDB.getUserAssessData(uid);


            List<Goal> everyones_goal = new ArrayList<>();
            Goal Dece_goal = e.getBMIGoal(weight,height);
            everyones_goal.add(Dece_goal);
            Dece_goal = e.getCholGoal(totalchl);
            everyones_goal.add(Dece_goal);
            Dece_goal = e.getHDLGoal(hdl);
            everyones_goal.add(Dece_goal);

            heDB.close();

            ListView lvMain = (ListView) d.findViewById(R.id.lv_goals);

            ArrayList<Goal> list = new ArrayList<>();
            for (Goal g : everyones_goal
                 ) {
                list.add(g);
            }


            GoalAdapter goalAdapter = new GoalAdapter(getApplicationContext(), list);

            lvMain.setAdapter(goalAdapter);

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
