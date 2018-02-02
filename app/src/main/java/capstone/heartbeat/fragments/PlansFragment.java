package capstone.heartbeat.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.content.SharedPreferences;

import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.animation.OvershootInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.annotation.SuppressLint;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import capstone.heartbeat.assessment.DemographicsActivity;
import capstone.heartbeat.controllers.ActivityDatabase;
import capstone.heartbeat.controllers.HeartBeatDB;
import capstone.heartbeat.others.AddPlanActivity;
import capstone.heartbeat.controllers.ExpandableListAdapter;
import capstone.heartbeat.controllers.ListAdapter;
import capstone.heartbeat.R;
import capstone.heartbeat.models.Activity;

public class PlansFragment extends Fragment{
    private ListView mListView;
    private FloatingActionButton mFab;
    private int mPreviousVisibleItem;
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> planlist;
    HashMap<String, List<String>> plan;

    private FloatingActionMenu menuRed;

    private FloatingActionButton menu_createPlan;
    private FloatingActionButton menu_addActivity;

    private List<FloatingActionMenu> menus = new ArrayList<>();
    private Handler mUiHandler = new Handler();

    ArrayList<Activity> suggestions;
    ActivityDatabase myDB;
    ListAdapter adapter;
    Button btn_addSuggestion, btn_cancel;
    private SharedPreferences pref;

    public PlansFragment() {
        // Required empty public constructor
    }

     @SuppressLint("ValidFragment")
    public PlansFragment(SharedPreferences pref){
        this.pref = pref;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_plans_list, container, false);

        // get the listview
        expListView = (ExpandableListView) view.findViewById(R.id.lv_plans);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        params.setMargins(0, 0, 0, 80);
        expListView.setLayoutParams(params);

        registerForContextMenu(expListView);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(getContext(), planlist, plan);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        // Listview Group click listener
        expListView.setOnGroupClickListener(new OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getContext(),
                        planlist.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getContext(),
                        planlist.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();

            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub

                final Dialog dialog = new Dialog(getContext(), android.R.style.Theme_Holo_Light_Dialog);
                dialog.setTitle("Activity Fact");
                dialog.setContentView(R.layout.fact_dialog);

                Button fact_okay = (Button) dialog.findViewById(R.id.fact_okay);
                TextView desc_activity = (TextView) dialog.findViewById(R.id.desc_activity);

                desc_activity.setText(plan.get(
                        planlist.get(groupPosition)).get(
                        childPosition) + " 15 minutes daily can lose of your weight");

                fact_okay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
                return false;
            }
        });

        /*expListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // When clicked on child, function longClick is executed
                if (ExpandableListView.getPackedPositionType(id) == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
                    int groupPosition = ExpandableListView.getPackedPositionGroup(id);
                    int childPosition = ExpandableListView.getPackedPositionChild(id);
                    longClick( expListView, groupPosition, childPosition);
                    return true;
                }
                return false;
            }
        });*/

        return view;
    }

    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);

        ExpandableListView.ExpandableListContextMenuInfo info =
                (ExpandableListView.ExpandableListContextMenuInfo) menuInfo;

        int type =
                ExpandableListView.getPackedPositionType(info.packedPosition);

        int group =
                ExpandableListView.getPackedPositionGroup(info.packedPosition);

        int child =
                ExpandableListView.getPackedPositionChild(info.packedPosition);

        // Only create a context menu for child items
        if (type == ExpandableListView.PACKED_POSITION_TYPE_CHILD)
        {
            // Array created earlier when we built the expandable list
            menu.add(0, v.getId(), 0, "Done");
            menu.add(0, v.getId(),0,"Delete");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean onContextItemSelected(MenuItem menuItem)
    {
        ExpandableListView.ExpandableListContextMenuInfo info =
                (ExpandableListView.ExpandableListContextMenuInfo) menuItem.getMenuInfo();

        int groupPos = 0, childPos = 0;

        int type = ExpandableListView.getPackedPositionType(info.packedPosition);
        if (type == ExpandableListView.PACKED_POSITION_TYPE_CHILD)
        {
            groupPos = ExpandableListView.getPackedPositionGroup(info.packedPosition);


            childPos = ExpandableListView.getPackedPositionChild(info.packedPosition);


            switch (menuItem.getTitle().toString()){

                case "Done" : {
                    HeartBeatDB db = new HeartBeatDB(getContext());
                    db.open();
                    if (plan.get(planlist.get(groupPos)).size() == 1) {
                    db.updatePlanList(planlist.get(groupPos),true);
                    plan.get(planlist.remove(groupPos));
                        listAdapter.notifyDataSetChanged();
                    }else {
                        db.updatePlan(planlist.get(groupPos), plan.get(planlist.get(groupPos)).get(childPos), true);
                         //plan.get(planlist.remove(groupPos)).plan.get;
                    }

                    db.close();
                    return true;
                }
        }



    }



//        // Pull values from the array we built when we created the list
//        String author = mListStringArray[groupPos][0];
//        String page = mListStringArray[groupPos][childPos * 3 + 1];
//        rowId = Integer.parseInt(mListStringArray[groupPos][childPos * 3 + 3]);
//
//        switch (menuItem.getItemId())
//        {
//            case MENU_READ:
//                readNote(rowId);
//                return true;
//
//            case MENU_EDIT:
//                editNote(rowId);
//                return true;
//
//            // etc..
//
//            default:
//                return super.onContextItemSelected(menuItem);
//        }

        return false;
    }

    private void prepareListData() {
        planlist = new ArrayList<String>();
        plan = new HashMap<String, List<String>>();

        HeartBeatDB db = new HeartBeatDB(getContext());
        db.open();
        /*String res = db.getData();*/


        int user = pref.getInt("id",1);
        Toast.makeText(getContext(),user+"",Toast.LENGTH_SHORT).show();


        // adding title of plans
        for (String title:db.getTitle(user)) {
             Toast.makeText(getContext(),title+"",Toast.LENGTH_SHORT).show();
            planlist.add(title);
        }

        // Adding child data

      /*  PlanActivitiesDatabase db1 = new PlanActivitiesDatabase(getContext());
        db1.open();*/
       List<List<String>> Plans = new ArrayList<>();
        List<String> activities = new ArrayList<>();

        Plans = db.getActivities(planlist);

       //replace with data from activities plan tables
        /*List<String> plans1 = new ArrayList<String>();
        for (String title:db.getTitle()) {
            plans1.add(title);
        }*/

        for(int i = 0; i < planlist.size(); i++){
            plan.put(planlist.get(i), Plans.get(i)); // Header, Child data
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListView = (ListView) view.findViewById(R.id.lv_plans);

        menuRed = (FloatingActionMenu) view.findViewById(R.id.menu_red);

        menu_createPlan = (FloatingActionButton) view.findViewById(R.id.menu_createPlan);
        menu_addActivity = (FloatingActionButton) view.findViewById(R.id.menu_addActivity);

        menuRed.setClosedOnTouchOutside(true);
        menuRed.hideMenuButton(false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        menus.add(menuRed);

        menu_createPlan.setOnClickListener(clickListener);
        menu_addActivity.setOnClickListener(clickListener);

        int delay = 400;
        for (final FloatingActionMenu menu : menus) {
            mUiHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    menu.showMenuButton(true);
                }
            }, delay);
            delay += 150;
        }

        menuRed.setOnMenuButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (menuRed.isOpened()) {
                    Toast.makeText(getActivity(), menuRed.getMenuButtonLabelText(), Toast.LENGTH_SHORT).show();
                }

                menuRed.toggle(true);
            }
        });

        createCustomAnimation();
    }

    private void reloadList(){

    }

    private void createCustomAnimation() {
        AnimatorSet set = new AnimatorSet();

        ObjectAnimator scaleOutX = ObjectAnimator.ofFloat(menuRed.getMenuIconView(), "scaleX", 1.0f, 0.2f);
        ObjectAnimator scaleOutY = ObjectAnimator.ofFloat(menuRed.getMenuIconView(), "scaleY", 1.0f, 0.2f);

        ObjectAnimator scaleInX = ObjectAnimator.ofFloat(menuRed.getMenuIconView(), "scaleX", 0.2f, 1.0f);
        ObjectAnimator scaleInY = ObjectAnimator.ofFloat(menuRed.getMenuIconView(), "scaleY", 0.2f, 1.0f);

        scaleOutX.setDuration(50);
        scaleOutY.setDuration(50);

        scaleInX.setDuration(150);
        scaleInY.setDuration(150);

        scaleInX.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                menuRed.getMenuIconView().setImageResource(menuRed.isOpened()
                        ? R.drawable.ic_add : R.drawable.ic_clear);
            }
        });

        set.play(scaleOutX).with(scaleOutY);
        set.play(scaleInX).with(scaleInY).after(scaleOutX);
        set.setInterpolator(new OvershootInterpolator(2));

        menuRed.setIconToggleAnimatorSet(set);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.menu_createPlan:
                    startActivity(new Intent(getContext(), AddPlanActivity.class));
                    break;
                case R.id.menu_addActivity:
                    final Dialog d = new Dialog(getContext(), android.R.style.Theme_Holo_Light_Dialog);
                    d.setTitle("Choose a plan");
                    d.setContentView(R.layout.plan_dialog);
                         int use = pref.getInt("id",1);   
                    final Spinner spinner = (Spinner) d.findViewById(R.id.spinner_plans);
                    Button b1 = (Button) d.findViewById(R.id.sp_next);
                    Button b2 = (Button) d.findViewById(R.id.sp_cancel);

                    HeartBeatDB DeceDB = new HeartBeatDB(getContext());
                    DeceDB.open();
                    List<String> plans = DeceDB.getTitle(use);


                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                            android.R.layout.simple_spinner_item, plans);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(dataAdapter);


                    b1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String titles = spinner.getSelectedItem().toString();
                            displaySuggestionDialog(titles);
                            d.dismiss();
                        }
                    });
                    b2.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v) {
                            d.dismiss();
                        }
                    });
                    d.show();
                    break;
            }
        }
    };

    public void displaySuggestionDialog(final String title){
        final Dialog dialog = new Dialog(getContext(), android.R.style.Theme_Holo_Light_Dialog);
        dialog.setTitle("Suggestions");
        dialog.setContentView(R.layout.fragment_suggestions);
        dialog.create();

        dialog.show();

        dialog.show();
        suggestions = new ArrayList<Activity>();
        myDB = new ActivityDatabase(getContext());

        suggestions = myDB.getActivities();
        adapter = new ListAdapter (getContext(), suggestions);

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

                            List<Activity> selectedActivities = new ArrayList<>();
                            for (Activity acts:suggestions){
                                if(acts.isChecked()){
                                    selectedActivities.add(acts);
                                }
                            }

                            List<String> selected = new ArrayList<>();
                            for (Activity a:selectedActivities
                                    ) {
                                selected.add(a.Activities);
                            }
                            //add here
                            HeartBeatDB plans = new HeartBeatDB(getContext());
                            plans.open();
                            plans.createEntry2(selected,title,false);
                            listAdapter.notifyDataSetChanged();
                            plans.close();

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
}