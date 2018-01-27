package capstone.heartbeat.fragments;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import capstone.heartbeat.R;
import capstone.heartbeat.controllers.HeartBeatDB;
import capstone.heartbeat.controllers.ResultEvaluator;
import capstone.heartbeat.models.Goal;
import capstone.heartbeat.models.User;


public class GoalsFragment extends Fragment{
    ProgressBar progress1, progress2, progress3,progress4;
    SharedPreferences prefs;
    TextView label1,percentLabel,percent_label2,percent_label3,percent_label4,label2,label3,label4;
    LinearLayout goal2;
    int uid;

    public GoalsFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public GoalsFragment(SharedPreferences pref){this.prefs = pref;}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        uid = prefs.getInt("id",0);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_goals, container, false);



        progress1 = (ProgressBar) view.findViewById(R.id.progress1);
        progress2 = (ProgressBar) view.findViewById(R.id.progress2);
        progress3 = (ProgressBar) view.findViewById(R.id.progress3);
        progress4 = (ProgressBar) view.findViewById(R.id.progress4);

        label1 = (TextView)view.findViewById(R.id.label1);
        percentLabel = (TextView)view.findViewById(R.id.percentLabel1);
        label2= (TextView)view.findViewById(R.id.label2);
        label3 = (TextView)view.findViewById(R.id.label3);
        label4 = (TextView)view.findViewById(R.id.label4);

        percent_label2 = (TextView)view.findViewById(R.id.percent_label2);
        percent_label3 = (TextView)view.findViewById(R.id.percent_label3);
        percent_label4 = (TextView)view.findViewById(R.id.percent_label4);

        goal2 = (LinearLayout)view.findViewById(R.id.goal2);


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        HeartBeatDB myDb = new HeartBeatDB(getContext());
        myDb.open();
        ResultEvaluator re = new ResultEvaluator();
        User user = new User();
        user = myDb.getUserAssessData(uid);

        double weight = user.getWeight();
        double height = user.height;
        Goal dece_goal = re.getBMIGoal(weight,height);
        double loseWeight = 10;
        double percent = Math.round((loseWeight/dece_goal.getValue())*100);


         if (dece_goal.getAction().equals("reduce")) {
        label1.setText("Reduce " + dece_goal.getValue() + " kg of weight");
        }else {
             label1.setText("Gain " + dece_goal.getValue() + " kg of weight");
         }
        percentLabel.setText(percent+ "%");
        progress1.setMax((int)dece_goal.getValue());
        progress1.setProgress((int)loseWeight);


        dece_goal = re.getCholGoal(user.chl);

        double chol_progress = 10;
        double chol_norm = 200;
        double chol_goal =user.chl - 200 ;
        double chol_percent = Math.round((chol_progress/chol_goal)*100);
        double hdl_goal = 80 - user.hdl;

        if (dece_goal.getAction().equals("reduce")) {
            label2.setText("Reduce " + dece_goal.getValue() + " mm/dl of total cholesterol");
        }else {
            label2.setText("Gain " + dece_goal.getValue() + " mm/dl of total cholesterol");
        }


        percent_label2.setText(Double.toString(chol_percent)+"%");
        progress2.setMax((int)chol_goal);
        progress2.setProgress((int)chol_progress);

        if (chol_goal <= 0){
            label2.setVisibility(View.GONE);
            percent_label2.setVisibility(View.GONE);
            progress2.setVisibility(View.GONE);
            goal2.setVisibility(View.INVISIBLE);

        }else{
            label2.setVisibility(View.VISIBLE);
            percent_label2.setVisibility(View.VISIBLE);
            progress2.setVisibility(View.VISIBLE);
        }





        int sbp = user.sbp;
        int dbp = user.dbp;
        myDb.close();



    }
}
