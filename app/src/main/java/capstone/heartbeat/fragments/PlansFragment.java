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
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
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
import android.widget.RelativeLayout;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import capstone.heartbeat.MainActivity;
import capstone.heartbeat.assessment.DemographicsActivity;
import capstone.heartbeat.controllers.ActivityDatabase;
import capstone.heartbeat.controllers.HeartBeatDB;
import capstone.heartbeat.controllers.PlanAdapter;
import capstone.heartbeat.controllers.ResultEvaluator;
import capstone.heartbeat.models.Bank;
import capstone.heartbeat.models.Plan;
import capstone.heartbeat.models.Plans;
import capstone.heartbeat.others.AddPlanActivity;
import capstone.heartbeat.controllers.ExpandableListAdapter;
import capstone.heartbeat.controllers.ListAdapter;
import capstone.heartbeat.R;
import capstone.heartbeat.models.Activity;

import static com.facebook.FacebookSdk.getApplicationContext;

public class PlansFragment extends Fragment{
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    CardView cardview;
    List<Plan> planList;
    List<String> planlist;
    HashMap<String, List<String>> plan;
    ArrayList<Activity> suggestions;
    ActivityDatabase myDB;
    ListAdapter adapter;
    Button btn_addSuggestion, btn_cancel;
    private RecyclerView recyclerView;
    private ListView mListView;
    private FloatingActionButton mFab;
    private int mPreviousVisibleItem;
    private FloatingActionMenu menuRed;
    private FloatingActionButton menu_createPlan;
    private FloatingActionButton menu_addActivity;
    private List<FloatingActionMenu> menus = new ArrayList<>();
    private Handler mUiHandler = new Handler();
    private SharedPreferences pref;
    private double weight;
    private int user;
    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.menu_createPlan:
                    startActivity(new Intent(getContext(), AddPlanActivity.class));
                    break;
                case R.id.menu_addActivity:
                    final Dialog d = new Dialog(getContext(), android.R.style.Theme_Material_Light_Dialog);
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

    public PlansFragment() {
        // Required empty public constructor
    }

     @SuppressLint("ValidFragment")
    public PlansFragment(SharedPreferences pref){
        this.pref = pref;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_plans, container, false);


        recyclerView = (RecyclerView)view.findViewById(R.id.rv_plans);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        planList = new ArrayList<>();


        // preparing list data
        prepareListData();

        planList.add(new Plan("My Plan","Good",100,50,1));
        planList.add(new Plan("My Plan2","Good",80,50,2));
        planList.add(new Plan("My Plan3","Good",90,50,3));
        planList.add(new Plan("My Plan4","Good",50,50,4));
        planList.add(new Plan("My Plan5","Good",60,50,5));

        PlanAdapter adapter = new PlanAdapter(getApplicationContext(), planList);
        recyclerView.setAdapter(adapter);

        // get the listview
        /*expListView = (ExpandableListView) view.findViewById(R.id.lv_plans);

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
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {


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
                                        childPosition) + " 15 minutes daily can lose  g of your weight");




                fact_okay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
                return false;
            }
        });*/

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

            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            String date = df.format(c.getTime());

            HashMap<String,Double> prog = new HashMap<>();
            List<Double> dob = new ArrayList<>();
            switch (menuItem.getTitle().toString()){

                case "Done" : {
                    /*prog.put(date,dob);*/
                    HeartBeatDB db = new HeartBeatDB(getContext());
                    db.open();
                    int use = pref.getInt("id",1);
                    if (plan.get(planlist.get(groupPos)).size() <= 1) {

                        plan.get(planlist.remove(groupPos));
                    db.updatePlanList(planlist.get(groupPos),true);
                    db.updateWeight(use,weight - .70);
                    db.newProgress(use,date,.70,"weight");
                        Intent intent = getActivity().getIntent();
                        startActivity(intent);
                        listAdapter.notifyDataSetChanged();
                    }else {
                        db.updatePlan(planlist.get(groupPos), plan.get(planlist.get(groupPos)).get(childPos), true);
                        db.addCoins(use,3);
                        db.updateWeight(use,weight - .70);
                        db.newProgress(use,date,.70,"weight");
                        Toast.makeText(getContext(),"+3 coin",Toast.LENGTH_SHORT).show();

                    }

                   db.close();
                    //return true;
                }break;
                case "Delete":{
                    HeartBeatDB d = new HeartBeatDB(getContext());
                    d.open();
                    String pl =planlist.get(groupPos);
                    String ac = plan.get(planlist.get(groupPos)).get(childPos);
                    plan.get(planlist.get(groupPos)).remove(childPos);
                    d.deleteAct(pl,ac);
                    d.close();
                    listAdapter.notifyDataSetChanged();
                }break;
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

    @Override
    public void onPrepareOptionsMenu(Menu menu) {

        MenuItem item1 = menu.findItem(R.id.action_coins);
        MenuItem item2 = menu.findItem(R.id.action_hearts);
        MenuItemCompat.setActionView(item1, R.layout.badge_layout);
        RelativeLayout coinCount = (RelativeLayout) MenuItemCompat.getActionView(item1);
        RelativeLayout heartCount = (RelativeLayout) MenuItemCompat.getActionView(item2);
        TextView coin = (TextView)coinCount.findViewById(R.id.badge_coin_text);
        HeartBeatDB db = new HeartBeatDB(getApplicationContext());
        db.open();
        Bank b = new Bank();
        b= db.getPoints(pref.getInt("id",1));
        int coins = b.getCoins();
        System.out.println(coins+"");
        coin.setText(coins+"");
        super.onPrepareOptionsMenu(menu);
    }

    private void prepareListData() {
        planlist = new ArrayList<String>();
        plan = new HashMap<String, List<String>>();

        HeartBeatDB db = new HeartBeatDB(getContext());
        db.open();
        /*String res = db.getData();*/


        int user = pref.getInt("id",1);


        // adding title of plans
        for (String title:db.getTitle(user)) {
            planlist.add(title);
        }

        // Adding child data

      /*  PlanActivitiesDatabase db1 = new PlanActivitiesDatabase(getContext());
        db1.open();*/
       List<List<String>> Plans = new ArrayList<>();

        Plans = db.getActivities(planlist);
        db.close();
        //extract List of activity name


        for(int i = 0; i < planlist.size(); i++){
            plan.put(planlist.get(i), Plans.get(i)); // Header, Child data
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cardview = (CardView) view.findViewById(R.id.cardView);

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

    public void displaySuggestionDialog(final String title){
        weight = pref.getInt("weight",0);
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
                            List<Double> weights = new ArrayList<>();
                            ResultEvaluator re = new ResultEvaluator(getContext());
                            for (Activity a:selectedActivities
                                    ) {
                                selected.add(a.Activities);
                                weights.add(re.getWeightEquivalent(a.getMETS(),weight));
                            }
                            //add here
                            HeartBeatDB plans = new HeartBeatDB(getContext());
                            plans.open();
                            plans.createEntry2(selected,title,false,weights);
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