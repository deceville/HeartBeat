package capstone.heartbeat.others;

import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import capstone.heartbeat.R;
import capstone.heartbeat.controllers.SwipeControllerActions;
import capstone.heartbeat.controllers.ActivityAdapter;
import capstone.heartbeat.controllers.SwipeController;
import capstone.heartbeat.models.Activity;

public class PlanActivitiesActivity extends AppCompatActivity {
    RecyclerView rv_activities;
    List<Activity> activityList;
    SwipeController swipeController = null;
    ActivityAdapter activityAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_activities);

        rv_activities = (RecyclerView) findViewById(R.id.rv_activities);
        rv_activities.setHasFixedSize(true);
        rv_activities.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        activityList = new ArrayList<>();

        activityList.add(new Activity("Sitting", 30.0));
        activityList.add(new Activity("Dancing", 60.0));
        activityList.add(new Activity("Walking", 40.0));

        activityAdapter = new ActivityAdapter(getApplicationContext(), activityList);
        rv_activities.setAdapter(activityAdapter);

        swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {
                activityAdapter.activities.remove(position);
                activityAdapter.notifyItemRemoved(position);
                activityAdapter.notifyItemRangeChanged(position, activityAdapter.getItemCount());
            }
        });

        swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onLeftClicked(int position) {
                super.onLeftClicked(position);
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
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
