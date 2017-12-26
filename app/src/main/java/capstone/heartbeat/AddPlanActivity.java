package capstone.heartbeat;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class AddPlanActivity extends AppCompatActivity {
    public FloatingActionButton btn_addActivity;
    List<Suggestions> suggestions;
    ListAdapter adapter;
    Button btn_addSuggestion, btn_cancel, btn_currentdate;
    public int currYear, currMonth, currDay;
    TextView text_plan_freetime;

    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plans);


        btn_addActivity = (FloatingActionButton) findViewById(R.id.btn_addActivity);

        btn_addActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog dialog = new AlertDialog.Builder(AddPlanActivity.this)
                        .setTitle("Suggestions")
                        .setView(R.layout.fragment_suggestions)
                        .create();

                dialog.show();
                suggestions = new ArrayList<Suggestions>();
                suggestions.add(new Suggestions(1, "Running","105 calories will burn", false));
                suggestions.add(new Suggestions(2, "Dancing","98 calories will burn", false));
                suggestions.add(new Suggestions(3, "Walking","77 calories will burn", false));
                suggestions.add(new Suggestions(4, "Jumping","78 calories will burn", false));

                adapter = new ListAdapter (getApplicationContext(), suggestions);

                ListView lvMain = (ListView) dialog.findViewById(R.id.lv_suggestions);
                lvMain.setAdapter(adapter);

                lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                });

                btn_addSuggestion = (Button) dialog.findViewById(R.id.btn_addSuggestion);

                btn_addSuggestion.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        StringBuilder selected = new StringBuilder("Selected: \n");

                        for (int i = 0; i < suggestions.size(); i++){
                            if(suggestions.get(i).isChecked()){
                                selected.append(i).append("\n");
                            }
                        }
                        Toast.makeText(getApplicationContext(), selected.toString(), Toast.LENGTH_SHORT).show();
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
