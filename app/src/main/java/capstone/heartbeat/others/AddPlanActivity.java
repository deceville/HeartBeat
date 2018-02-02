package capstone.heartbeat.others;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.support.design.widget.FloatingActionButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import capstone.heartbeat.MainActivity;
import capstone.heartbeat.R;
import capstone.heartbeat.controllers.ActivityDatabase;
import capstone.heartbeat.controllers.ListAdapter;
import capstone.heartbeat.controllers.HeartBeatDB;
import capstone.heartbeat.controllers.ResultEvaluator;
import capstone.heartbeat.models.Activity;
import capstone.heartbeat.models.Suggestions;
import capstone.heartbeat.models.User;


public class AddPlanActivity extends AppCompatActivity {

    public FloatingActionButton btn_addActivity;
    List<Suggestions> suggestions;
    ListAdapter adapter;
    Button btn_addSuggestion, btn_cancel, btn_currentdate, btn_buytime;
    public int currYear, currMonth, currDay;
    TextView text_plan_freetime,txtWeight;
    private EditText plan_name;

    public static SharedPreferences prefs,use;
    SharedPreferences.Editor editor;
    private DatabaseReference rootRef,actRef;
    ActivityDatabase myDb;
    ArrayList<Activity> myActivities;
    ArrayList <Activity> selectedActivities;
    private FirebaseDatabase database;
    public int uid;
    public double weight;
    double totalWeight;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plans);

        prefs = getSharedPreferences("values",MODE_PRIVATE);
        use= getSharedPreferences("login",MODE_PRIVATE);
        uid = use.getInt("id",0);
        weight = prefs.getInt("weight",60);

        myDb = new ActivityDatabase(AddPlanActivity.this);

        // get plan name input
        plan_name = (EditText) findViewById(R.id.plan_name);
        txtWeight = (TextView) findViewById(R.id.weight_total);

        btn_buytime = (Button) findViewById(R.id.btn_buytime);

        /*btn_buytime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog dialog = new AlertDialog.Builder(AddPlanActivity.this)
                        .setTitle("Buy Time")
                        .setView(R.layout.shop_dialog)
                        .create();

                dialog.show();
            }
        });*/

        btn_addActivity = (FloatingActionButton) findViewById(R.id.btn_addActivity);

        btn_addActivity.setOnClickListener(new View.OnClickListener() {
             
            @Override
            public void onClick(View v) {
                final AlertDialog dialog = new AlertDialog.Builder(AddPlanActivity.this)
                        .setTitle("Suggestions")
                        .setView(R.layout.fragment_suggestions)
                        .create();

                dialog.show();

                //myActivities =  myDb.getActivities();

                //suggestions = new ArrayList<Suggestions>();


                ResultEvaluator re = new ResultEvaluator();
                double met = re.getSuggestedMet();
                System.out.println("met:" +met);
                myActivities =  myDb.getSuggestedActivities(met);
                User dece = new User(met);
                HeartBeatDB deceville = new HeartBeatDB(getApplicationContext());
                deceville.open();
              /*  deceville.insertUserMET(dece,uid);*/
                deceville.close();


                adapter = new ListAdapter (getApplicationContext(), myActivities);

                ListView lvMain = (ListView) dialog.findViewById(R.id.lv_suggestions);
                lvMain.setAdapter(adapter);

                lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    int totalTime = 0;
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            int time = prefs.getInt("free",60);
                            totalTime = totalTime + 15;
                            if (totalTime > time) {
                                Toast.makeText(getApplicationContext(),"You reached the maximum",Toast.LENGTH_SHORT).show();

                            }
                    }
                });

                btn_addSuggestion = (Button) dialog.findViewById(R.id.btn_addSuggestion);

                // selected list of activities on add plan form
                selectedActivities = new ArrayList<>();

                btn_addSuggestion.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        StringBuilder selected = new StringBuilder("Selected: \n");
                        totalWeight = 0;
                        ResultEvaluator e = new ResultEvaluator();

                        for (Activity acts:myActivities){
                            if(acts.isChecked()){
                                selectedActivities.add(acts);
                                totalWeight += e.getWeightEquivalent(acts.getMETS(),weight);
                                totalWeight = Math.round(totalWeight);

                            }


                        }
                        txtWeight.setText(totalWeight + " g");
                       /*SharedPreferences.Editor edit = prefs.edit();
                       edit.putLong("totalWeight",(long)totalWeight);
                       edit.commit();*/

                        /*HeartBeatDB plans = new HeartBeatDB(getApplicationContext());
                        plans.open();
                        plans.createEntry2(selectedActivities,plan_name.getText().toString());
                        plans.close();*/
                        //Toast.makeText(getApplicationContext(), selected.toString(), Toast.LENGTH_SHORT).show();

                        adapter = new ListAdapter (getApplicationContext(), selectedActivities);

                        ListView lvPlan = (ListView) findViewById(R.id.plans_suggestions);
                        lvPlan.setAdapter(adapter);

                        lvPlan.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        });

                        dialog.dismiss();
                    }
                });

                btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);

                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

            }
        });

        btn_currentdate = (Button) findViewById(R.id.btn_currentdate);

        Calendar c = Calendar.getInstance();
        currYear = c.get(Calendar.YEAR);
        currMonth = c.get(Calendar.MONTH);
        currDay = c.get(Calendar.DAY_OF_MONTH);

        btn_currentdate.setText(new StringBuilder()
                // Month is 0 based so add 1
                .append(currMonth + 1).append("/").append(currDay).append("/")
                .append(currYear).append(" "));
        //initialize editor
       
        SharedPreferences.Editor editor = prefs.edit();
        String plan_date = currMonth + 1 + "/" + currDay + "/" + currYear + " ";
        editor.putString("plan_date", plan_date);
        editor.commit();

        text_plan_freetime = (TextView) findViewById(R.id.text_plan_freetime);
        text_plan_freetime.setText(prefs.getString("freetime", ""));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.save_button, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up next_button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.save) {


            prefs = getSharedPreferences("values",MODE_PRIVATE);
            editor = prefs.edit();
            editor.putString("plan_name", plan_name.getText().toString());
            editor.apply();

            String title = prefs.getString("plan_name",null);
            String date = prefs.getString("plan_date",null);
            double cal = 100;
            int initialMinutes = 0;
            int freeTime = prefs.getInt("free",0);

            List<String> selected = new ArrayList<>();
            for (Activity a:selectedActivities
                 ) {
               selected.add(a.Activities);
               initialMinutes += 15;
            }

            Toast.makeText(getApplicationContext(),uid+"",Toast.LENGTH_SHORT).show();
            HeartBeatDB plans = new HeartBeatDB(getApplicationContext());
            plans.open();
            plans.createEntry1(uid,title,date,cal,initialMinutes,freeTime,false,totalWeight);
            plans.createEntry2(selected,title,false);
            plans.close();

            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class mDateSetListener implements DatePickerDialog.OnDateSetListener {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            // getCalender();
            int mYear = year;
            int mMonth = monthOfYear;
            int mDay = dayOfMonth;


        }
    }
}
