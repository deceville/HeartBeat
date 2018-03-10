package capstone.heartbeat.others;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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
import capstone.heartbeat.controllers.HeartBeatDB;
import capstone.heartbeat.controllers.ListAdapter;
import capstone.heartbeat.controllers.ResultEvaluator;
import capstone.heartbeat.models.Activity;
import capstone.heartbeat.models.Bank;
import capstone.heartbeat.models.Suggestions;
import capstone.heartbeat.models.User;


public class AddPlanSActivity extends AppCompatActivity {

    public FloatingActionButton btn_addActivity;
    List<Suggestions> suggestions;
    ListAdapter adapter;
    Button btn_addSuggestion, btn_cancel, btn_currentdate, btn_buytime, btn_gotit;
    public int currYear, currMonth, currDay;
    TextView text_plan_freetime, txtWeight, cat_age, cat_BMI, cat_SUGGEST,shop_coin;
    private EditText plan_name;

    public static SharedPreferences prefs, use;
    SharedPreferences.Editor editor;
    private DatabaseReference rootRef, actRef;
    ActivityDatabase myDb;
    ArrayList<Activity> myActivities;
    ArrayList<Activity> selectedActivities;
    private FirebaseDatabase database;
    public int uid, free, count, coins, total, current, fee, time, hours, minutes;
    public double weight;
    double totalWeight;
    private Bank coin;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plans);

        prefs = getSharedPreferences("values", MODE_PRIVATE);
        use = getSharedPreferences("login", MODE_PRIVATE);
        uid = use.getInt("id", 0);
        weight = prefs.getInt("weight", 60);
        free = prefs.getInt("free", 60);

        Bundle b = getIntent().getExtras();
        ArrayList<Activity> acts = b.getParcelableArrayList("acts");
        double weight = b.getDouble("weight");

       /* cat_age = (TextView) findViewById(R.id.cat_age);
        cat_BMI = (TextView) findViewById(R.id.cat_BMI);
        cat_SUGGEST = (TextView) findViewById(R.id.cat_SUGGEST);
        btn_gotit = (Button) findViewById(R.id.btn_gotit);*/



        myDb = new ActivityDatabase(AddPlanSActivity.this);

        //displaySuggestions();

        // get plan name input
        plan_name = (EditText) findViewById(R.id.plan_name);
        txtWeight = (TextView) findViewById(R.id.weight_total);
        text_plan_freetime = (TextView) findViewById(R.id.text_plan_freetime);

        btn_buytime = (Button) findViewById(R.id.btn_buytime);
        txtWeight.setText(weight + " g");
        adapter = new ListAdapter(getApplicationContext(), acts);

        ListView lvPlan = (ListView) findViewById(R.id.plans_suggestions);
        lvPlan.setAdapter(adapter);
        btn_buytime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(AddPlanSActivity.this, android.R.style.Theme_Material_Light_Dialog);
                dialog.setTitle("Buy Time");
                dialog.setContentView(R.layout.shop_dialog);
                dialog.create();

                dialog.show();

                Button shop_done = (Button) dialog.findViewById(R.id.shop_done);
                Button shop_cancel = (Button) dialog.findViewById(R.id.shop_cancel);
                shop_coin = (TextView) dialog.findViewById(R.id.shop_coin);
                Button btn_5mins = (Button) dialog.findViewById(R.id.btn_5mins);
                Button btn_10mins = (Button) dialog.findViewById(R.id.btn_10mins);
                Button btn_15mins = (Button) dialog.findViewById(R.id.btn_15mins);
                Button btn_30mins = (Button) dialog.findViewById(R.id.btn_30mins);
                Button btn_1hr = (Button) dialog.findViewById(R.id.btn_1hr);
                Button btn_2hr = (Button) dialog.findViewById(R.id.btn_2hr);


                HeartBeatDB db = new HeartBeatDB(getApplicationContext());
                db.open();
                coin = db.getPoints(uid);
                current = 0;
                total = coin.getCoins();
                shop_coin.setText("You currently have " + total + " coins and " + String.format("%02d:%02d", hours, minutes) + " hour available time.");

                btn_5mins.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fee = 5;
                        time = 5;
                        confirmBuy(fee,total,"5 minutes",time);
                        invalidateOptionsMenu();
                    }
                });

                btn_10mins.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fee = 10;
                        time = 10;
                        confirmBuy(fee,total,"10 minutes",time);
                        invalidateOptionsMenu();
                    }
                });

                btn_15mins.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fee = 15;
                        time = 15;
                        confirmBuy(fee,total,"15 minutes",time);
                        invalidateOptionsMenu();
                    }
                });

                btn_30mins.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fee = 30;
                        time = 30;
                        confirmBuy(fee,total,"30 minutes",time);
                        invalidateOptionsMenu();
                    }
                });

                btn_1hr.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fee = 60;
                        time = 60;
                        confirmBuy(fee,total,"1 hour",time);
                        invalidateOptionsMenu();
                    }
                });

                btn_2hr.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fee = 120;
                        time = 120;
                        confirmBuy(fee,total,"2 hours",time);
                        invalidateOptionsMenu();
                    }
                });

                shop_done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

                shop_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });

        btn_addActivity = (FloatingActionButton) findViewById(R.id.btn_addActivity);

        btn_addActivity.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                displaySuggestions();

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

        hours = free/60;
        minutes = free%60;
        System.out.println("initial:"+String.format("%02d:%02d", hours, minutes) + "hour");
        text_plan_freetime.setText(String.format("%02d:%02d", hours, minutes) + "hour");
    }

    public void confirmBuy(final int fee, final int coins, String time, final int freetime){
        AlertDialog.Builder builder = new AlertDialog.Builder(AddPlanSActivity.this, R.style.Theme_AppCompat_Dialog_Alert);
        if(fee <= coins){
            builder.setTitle("Hmm, wait..")
                    .setMessage("Are you sure you want to buy " + time + "?")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            total -= fee;
                            free += freetime;

                            hours = free/60;
                            minutes = free%60;

                            Toast.makeText(getApplicationContext(),"Successfully bought!", Toast.LENGTH_SHORT).show();
                            shop_coin.setText("You currently have " +total+ " coins and "+String.format("%02d:%02d", hours, minutes)+ " hour available time.");
                            System.out.println("total:"+total +"free:"+free);
                            editor.putInt("coin", total);
                            editor.putInt("free",free);
                            HeartBeatDB db = new HeartBeatDB(getApplicationContext());
                            db.open();
                            db.updateCoins(uid,total);
                            db.close();
                            invalidateOptionsMenu();
                            editor.commit();
                            System.out.println("Shopped successfully");
                            System.out.println("after:"+String.format("%02d:%02d", hours, minutes) + "hour");
                            text_plan_freetime.setText(String.format("%02d:%02d", hours, minutes) + "hour");
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert);


            AlertDialog alert = builder.create();
            alert.show();
        }else{
            builder.setTitle("Oh bummer!")
                    .setMessage("You don't have enough coins to buy " + time + "!")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert);

            AlertDialog alert = builder.create();
            alert.show();
        }

    }

    public void displaySuggestions() {
        final Dialog dialog = new Dialog(AddPlanSActivity.this, android.R.style.Theme_Material_Light_Dialog);
        dialog.setTitle("Suggestions");
        dialog.setContentView(R.layout.fragment_suggestions);
        dialog.create();

        dialog.show();

        //myActivities =  myDb.getActivities();

        //suggestions = new ArrayList<Suggestions>();

        ResultEvaluator re = new ResultEvaluator(getApplicationContext());
        double met = re.getSuggestedMet();
        System.out.println("met:" + met);
        myActivities = myDb.getSuggestedActivities(met);
        User dece = new User(met);
        HeartBeatDB deceville = new HeartBeatDB(getApplicationContext());
        deceville.open();
              /*  deceville.insertUserMET(dece,uid);*/
        deceville.close();


        adapter = new ListAdapter(getApplicationContext(), myActivities);

        ListView lvMain = (ListView) dialog.findViewById(R.id.lv_suggestions);
        lvMain.setAdapter(adapter);

        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            int totalTime = 0;

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });


        btn_addSuggestion = (Button) dialog.findViewById(R.id.btn_addSuggestion);
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
                StringBuilder selected = new StringBuilder("Selected: \n");
                totalWeight = 0;
                ResultEvaluator e = new ResultEvaluator(getApplicationContext());
                SharedPreferences.Editor ed = prefs.edit();
                ed.putInt("count", 0);
                ed.apply();
                for (Activity acts : myActivities) {
                    if (acts.isChecked()) {

                        selectedActivities.add(acts);
                        totalWeight += e.getWeightEquivalent(acts.getMETS(), weight);
                        totalWeight = Math.round(totalWeight);

                    }


                }

                       /*SharedPreferences.Editor edit = prefs.edit();
                       edit.putLong("totalWeight",(long)totalWeight);
                       edit.commit();*/

                        /*HeartBeatDB plans = new HeartBeatDB(getApplicationContext());
                        plans.open();
                        plans.createEntry2(selectedActivities,plan_name.getText().toString());
                        plans.close();*/
                //Toast.makeText(getApplicationContext(), selected.toString(), Toast.LENGTH_SHORT).show();

                adapter = new ListAdapter(getApplicationContext(), selectedActivities);

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
                SharedPreferences.Editor ed = prefs.edit();
                ed.putInt("count", 0);
                ed.apply();
                dialog.dismiss();
            }
        });
    }

    public void displayActivityGuide() {
        final Dialog dialog = new Dialog(AddPlanSActivity.this, android.R.style.Theme_Material_Light_Dialog);
        dialog.setTitle("Guide");
        dialog.setContentView(R.layout.category_dialog);
        dialog.create();
        cat_age = (TextView) dialog.findViewById(R.id.cat_age);
        cat_BMI = (TextView) dialog.findViewById(R.id.cat_BMI);
        cat_SUGGEST = (TextView) dialog.findViewById(R.id.cat_SUGGEST);
        btn_gotit = (Button) dialog.findViewById(R.id.btn_gotit);

        HeartBeatDB db = new HeartBeatDB(getApplicationContext());
        db.open();
        User user = db.getUserAssessData(uid);
        ResultEvaluator re = new ResultEvaluator(getApplicationContext());


        String age = user.birth;
        double bmi = re.getBMI(user.weight, user.height);
        bmi = Math.round(bmi);

        System.out.println(age);
        cat_age.setText("Your age is " + age);
        cat_BMI.setText("and your BMI is " + bmi + " which is normal.");
        cat_SUGGEST.setText("You should do ");

        btn_gotit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                displaySuggestions();
            }
        });
        dialog.show();
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


            prefs = getSharedPreferences("values", MODE_PRIVATE);
            editor = prefs.edit();
            editor.putString("plan_name", plan_name.getText().toString());
            editor.apply();

            String title = prefs.getString("plan_name", null);
            String date = prefs.getString("plan_date", null);
            double cal = 100;
            int initialMinutes = 0;
            int freeTime = prefs.getInt("free", 0);

            List<String> selected = new ArrayList<>();
            List<Double> selectedMets = new ArrayList<>();
            ResultEvaluator e = new ResultEvaluator(getApplicationContext());
            for (Activity a : selectedActivities
                    ) {
                selected.add(a.Activities);
                double b = e.getWeightEquivalent(a.getMETS(), weight);
                System.out.println("b: " + b);
                selectedMets.add(b);
                initialMinutes += 15;
            }

            Toast.makeText(getApplicationContext(), uid + "", Toast.LENGTH_SHORT).show();
            HeartBeatDB plans = new HeartBeatDB(getApplicationContext());
            plans.open();
            plans.createEntry1(uid, title, date, cal, initialMinutes, freeTime, false, totalWeight);
            plans.createEntry2(selected, title, false, selectedMets);
            plans.close();

            startActivity(new Intent(getApplicationContext(), MainActivity.class));
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
