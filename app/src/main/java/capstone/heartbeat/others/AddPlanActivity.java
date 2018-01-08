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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import capstone.heartbeat.MainActivity;
import capstone.heartbeat.R;
import capstone.heartbeat.controllers.ActivityDatabase;
import capstone.heartbeat.controllers.ListAdapter;
import capstone.heartbeat.models.Activity;
import capstone.heartbeat.models.Suggestions;


public class AddPlanActivity extends AppCompatActivity {

    public FloatingActionButton btn_addActivity;
    List<Suggestions> suggestions;
    ListAdapter adapter;
    Button btn_addSuggestion, btn_cancel, btn_currentdate;
    public int currYear, currMonth, currDay;
    TextView text_plan_freetime;

    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    private DatabaseReference rootRef,actRef;
    ActivityDatabase myDb;
    ArrayList<Activity> myActivities;
    ArrayList <Activity> selectedActivities;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plans);
        myDb = new ActivityDatabase(AddPlanActivity.this);


        btn_addActivity = (FloatingActionButton) findViewById(R.id.btn_addActivity);

        btn_addActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog dialog = new AlertDialog.Builder(AddPlanActivity.this)
                        .setTitle("Suggestions")
                        .setView(R.layout.fragment_suggestions)
                        .create();

                dialog.show();

                myActivities =  myDb.getActivities();

                //suggestions = new ArrayList<Suggestions>();

                adapter = new ListAdapter (getApplicationContext(), myActivities);

                ListView lvMain = (ListView) dialog.findViewById(R.id.lv_suggestions);
                lvMain.setAdapter(adapter);

                lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                });

                btn_addSuggestion = (Button) dialog.findViewById(R.id.btn_addSuggestion);

                // selected list of activities on add plan form
                selectedActivities = new ArrayList<Activity>();

                btn_addSuggestion.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        StringBuilder selected = new StringBuilder("Selected: \n");

                        for (int i = 0; i < myActivities.size(); i++){
                            if(myActivities.get(i).isChecked()){
                                selectedActivities.add(myActivities.get(i));

                            }
                        }
                        //Toast.makeText(getApplicationContext(), selected.toString(), Toast.LENGTH_SHORT).show();

                        adapter = new ListAdapter (getApplicationContext(), selectedActivities);

                        ListView lvPlan = (ListView) findViewById(R.id.plans_suggestions);
                        lvPlan.setAdapter(adapter);

                        lvPlan.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        });

                        dialog.hide();
                    }
                });

                btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);

                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.hide();
                    }
                });

            }
        });

        btn_currentdate = (Button) findViewById(R.id.btn_currentdate);

        btn_currentdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                currYear = c.get(Calendar.YEAR);
                currMonth = c.get(Calendar.MONTH);
                currDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(AddPlanActivity.this,
                        android.R.style.Theme_Material_Light_Dialog,
                        new mDateSetListener(), currYear, currMonth, currDay){
                    @Override
                    public void onCreate(Bundle savedInstanceState)
                    {
                        super.onCreate(savedInstanceState);
                    }
                };
                dialog.show();
            }
        });

        prefs = getSharedPreferences("values",MODE_PRIVATE);
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
            btn_currentdate.setText(new StringBuilder()
                    // Month is 0 based so add 1
                    .append(mMonth + 1).append("/").append(mDay).append("/")
                    .append(mYear).append(" "));
        }
    }
}
