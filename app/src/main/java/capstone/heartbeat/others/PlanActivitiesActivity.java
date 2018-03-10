package capstone.heartbeat.others;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import capstone.heartbeat.MainActivity;
import capstone.heartbeat.R;
import capstone.heartbeat.controllers.Effects;
import capstone.heartbeat.controllers.HeartBeatDB;
import capstone.heartbeat.controllers.SwipeControllerActions;
import capstone.heartbeat.controllers.ActivityAdapter;
import capstone.heartbeat.controllers.SwipeController;
import capstone.heartbeat.models.Activity;
import capstone.heartbeat.models.Bank;
import capstone.heartbeat.models.Progress;

public class PlanActivitiesActivity extends AppCompatActivity {
    RecyclerView rv_activities;
    List<Activity> activityList;
    SwipeController swipeController = null;
    ActivityAdapter activityAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_activities);

        Effects.getInstance().init(this);

        final String title = getIntent().getStringExtra("name");
        SharedPreferences prefs = getSharedPreferences("login",MODE_PRIVATE);
        final int userId = prefs.getInt("id",1);
        rv_activities = (RecyclerView) findViewById(R.id.rv_activities);
        rv_activities.setHasFixedSize(true);
        rv_activities.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        activityList = new ArrayList<>();


        List<Activity> list = new ArrayList<>();
        HeartBeatDB myDb = new HeartBeatDB(getApplicationContext());
        myDb.open();
        list = myDb.getActivities(title);

        activityAdapter = new ActivityAdapter(getApplicationContext(),list);
        rv_activities.setAdapter(activityAdapter);

        swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {
                super.onRightClicked(position);
                activityAdapter.activities.remove(position);
                activityAdapter.notifyDataSetChanged();
                activityAdapter.notifyItemRangeChanged(position, activityAdapter.getItemCount());

            }
        });

        swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onLeftClicked(int position) {
                super.onLeftClicked(position);

                Effects.getInstance().playSound(Effects.coin_SOUND);

                Toast.makeText(getApplicationContext(),"+10 coin",Toast.LENGTH_SHORT).show();
                HeartBeatDB db = new HeartBeatDB(getApplicationContext());
                db.open();
                Bank coin = db.getPoints(userId);
                int total = coin.getCoins() + 10;
                System.out.println(total);
                db.updateCoins(userId,total);

                double weight = db.getUserAssessData(userId).getWeight();
                double po = activityAdapter.activities.get(position).getWeightLoss()/1000;

                db.updatePlan(title,activityAdapter.activities.get(position).getWeightLoss());
                db.updateWeight(userId,weight - po);
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();
                db.newProgress(userId,df.format(date),activityAdapter.activities.get(position).getWeightLoss(),"BMI");
                List<Progress> pl = db.getweightProg(userId,"BMI");
                for (Progress p:pl
                     ) {
                    System.out.println(p.getDate()+" : "+p.getProgress());
                }

                invalidateOptionsMenu();
                db.updatePlanList(activityAdapter.activities.get(position).getActivities(),true);
                activityAdapter.notifyDataSetChanged();
            }
        });



        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(rv_activities);

        rv_activities.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        // Inflate the menu; this adds items to the action bar if it is present.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up next_button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id == android.R.id.home){
            startActivity(new Intent(PlanActivitiesActivity.this, MainActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
