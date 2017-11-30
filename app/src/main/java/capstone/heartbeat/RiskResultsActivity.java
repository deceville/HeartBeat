package capstone.heartbeat;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.github.lzyzsd.circleprogress.DonutProgress;

public class RiskResultsActivity extends AppCompatActivity {

    private DonutProgress heartattack,stroke;
    private SharedPreferences prefs = getSharedPreferences("values",MODE_PRIVATE);
    private double age,sbp, totalchl, hdl, height,weight;
    private int smoke,af, diabType1,diabType2,fhcvd,ra,CKD,CHF, HA, VHD,bptreatment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_risk_results);

        heartattack =(DonutProgress)findViewById(R.id.progress_heartattack);
        stroke = (DonutProgress)findViewById(R.id.progress_stroke);

        age = (double)prefs.getInt("age",0);
        sbp = (double)prefs.getInt("sbp",0);
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
        int gender = prefs.getInt("gender",0);

        final int bool[]={5,smoke,af,diabType1,diabType2,fhcvd,ra,bptreatment};
        final double continuous[]={age,sbp,totalchl,hdl,height,weight};

        Qrisk2Male hm = new Qrisk2Male();
        Qrisk2Female hf = new Qrisk2Female();
        QStrokeMale sm = new QStrokeMale();
        QStrokeFemale sf = new QStrokeFemale();

        double heartAttack,Stroke;
        if (gender==0){
             heartAttack = hf.getResult(continuous,bool);
             Stroke = sf.getResult(continuous,bool,VHD,CKD,CHF,HA);
             heartattack.setProgress((float)heartAttack);
             stroke.setProgress((float)Stroke);

        }else if(gender==1){
            heartAttack = hm.getResult(continuous,bool);
            Stroke = sm.getResult(continuous,bool,VHD,CKD,CHF,HA);
            heartattack.setProgress((float)heartAttack);
            stroke.setProgress((float)Stroke);
        }

    }
}
