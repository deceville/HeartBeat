package capstone.heartbeat.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import capstone.heartbeat.controllers.ActivityDatabase;
import capstone.heartbeat.controllers.HeartBeatDB;
import capstone.heartbeat.controllers.ListAdapter;
import capstone.heartbeat.R;
import capstone.heartbeat.controllers.ResultEvaluator;
import capstone.heartbeat.models.Activity;
import capstone.heartbeat.models.Suggestions;
import capstone.heartbeat.models.User;
import capstone.heartbeat.others.AddPlanActivity;
import capstone.heartbeat.others.AddPlanSActivity;

import static android.content.Context.MODE_PRIVATE;


public class SuggestionsFragment extends Fragment {
    public FloatingActionButton btn_addActivity;
    ArrayList<Activity> suggestions;
    ListAdapter adapter;
    Button btn_addSuggestion, btn_cancel, btn_currentdate, btn_buytime, btn_gotit;
    public int currYear, currMonth, currDay;
    TextView text_plan_freetime, txtWeight, cat_age, cat_BMI, cat_SUGGEST;
    private EditText plan_name;

    public SharedPreferences prefs, use;
    SharedPreferences.Editor editor;
    private DatabaseReference rootRef, actRef;
    ActivityDatabase myDb;
    ArrayList<Activity> myActivities;
    ArrayList<Activity> selectedActivities;
    private FirebaseDatabase database;


    public int uid, free, count;
    public double weight;
    double totalWeight;

    public SuggestionsFragment(){}

    @SuppressLint("ValidFragment")
    public SuggestionsFragment(SharedPreferences prefs) {
        this.prefs = prefs;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        free = prefs.getInt("free", 60);
        weight = prefs.getInt("weight", 60);

        final View view = inflater.inflate(R.layout.fragment_suggestions, container, false);
        btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
        txtWeight = (TextView) view.findViewById(R.id.weight_total);
        btn_addSuggestion = (Button) view.findViewById(R.id.btn_addSuggestion);
        btn_cancel.setVisibility(View.GONE);
        //suggestions = new ArrayList<Activity>();
        myDb = new ActivityDatabase(getContext());
        ResultEvaluator re = new ResultEvaluator(getContext());
        final double met = re.getSuggestedMet();
        System.out.println("met:" + met);
        suggestions = myDb.getSuggestedActivities((int) met);

        adapter = new ListAdapter(getContext(), suggestions);

        ListView lvMain = (ListView) view.findViewById(R.id.lv_suggestions);
        lvMain.setAdapter(adapter);

        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 0, 0);
        btn_addSuggestion.setLayoutParams(params);

        /*btn_addSuggestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder selected = new StringBuilder("Selected: \n");
                List<Activity> select = new ArrayList<>();
                for (int i = 0; i < suggestions.size(); i++) {
                    if (suggestions.get(i).isChecked()) {
                        select.add(suggestions.get(i));
                    }
                }
                startActivity(new Intent(getContext(), AddPlanActivity.class));
            }
        });*/

        btn_addSuggestion = (Button) view.findViewById(R.id.btn_addSuggestion);
        btn_addSuggestion.setEnabled(true);
        // selected list of activities on add plan form
        selectedActivities = new ArrayList<>();
        count = prefs.getInt("count", 1);
        if ((count * 15) < free) {
            btn_addSuggestion.setEnabled(true);
        } else btn_addSuggestion.setEnabled(false);
        btn_addSuggestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get plan name input
                totalWeight = 0;
                ResultEvaluator e = new ResultEvaluator(getContext());
                SharedPreferences.Editor ed = prefs.edit();
                ed.putInt("count", 0);
                ed.apply();
                ArrayList<Activity> selected = new ArrayList<Activity>();
                for (Activity acts : suggestions) {
                    if (acts.isChecked()) {

                        selectedActivities.add(acts);
                        totalWeight += e.getWeightEquivalent(acts.getMETS(), weight);
                        totalWeight = Math.round(totalWeight);
                        selected.add(acts);

                    }

                }

                Bundle b = new Bundle();
                b.putParcelableArrayList("acts", selected);
                b.putDouble("weight",totalWeight);
                startActivity(new Intent(getContext(),AddPlanSActivity.class).putExtras(b));
            }
        });

        // Inflate the layout for this fragment
        return view;
    }


    /*@Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TapTargetView.showFor(getActivity(),
                TapTarget.forView(btn_addSuggestion, "Add selected suggestions", "Select and add suggestions to a new plan")
                        .outerCircleColor(R.color.bg_screen2)      // Specify a color for the outer circle
                        .outerCircleAlpha(0.96f)
                        .titleTextSize(20)
                        .descriptionTextSize(20)
                        .textColor(R.color.standardWhite) // Specify a color for both the title and description text
                        .textTypeface(Typeface.SANS_SERIF)  // Specify a typeface for the text
                        .dimColor(R.color.standardBlack)            // If set, will dim behind the view with 30% opacity of the given color
                        .drawShadow(true)                   // Whether to draw a drop shadow or not
                        .cancelable(false)                  // Whether tapping outside the outer circle dismisses the view
                        .tintTarget(false)                   // Whether to tint the target view's color
                        .transparentTarget(false)            // Specify a custom drawable to draw as the target
                        .targetRadius(60)
        );
    }*/
}
