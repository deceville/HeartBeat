package capstone.heartbeat;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    List<Suggestions> suggestions;
    ListAdapter adapter;
    Button btn_addSuggestion, btn_cancel;

    public PlansFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_plans_list, container, false);

        // get the listview
        expListView = (ExpandableListView) view.findViewById(R.id.lv_plans);

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
                Toast.makeText(
                        getContext(),
                        planlist.get(groupPosition)
                                + " : "
                                + plan.get(
                                planlist.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)
                        .show();
                return false;
            }
        });

        return view;
    }

    private void prepareListData() {
        planlist = new ArrayList<String>();
        plan = new HashMap<String, List<String>>();

        // Adding child data
        planlist.add("Plan 1");
        planlist.add("Plan 2");
        planlist.add("Plan 3");

        // Adding child data
        List<String> plans1 = new ArrayList<String>();
        plans1.add("Running");
        plans1.add("Dancing");
        plans1.add("Walking");

        List<String> plans2 = new ArrayList<String>();
        plans2.add("Sitting");
        plans2.add("Dancing");
        plans2.add("Running");
        plans2.add("Jogging");

        List<String> plans3 = new ArrayList<String>();
        plans3.add("Jogging");
        plans3.add("Sitting");
        plans3.add("Dancing");
        plans3.add("Running");

        plan.put(planlist.get(0), plans1); // Header, Child data
        plan.put(planlist.get(1), plans2);
        plan.put(planlist.get(2), plans3);
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
                    final AlertDialog dialog = new AlertDialog.Builder(getContext())
                            .setTitle("Suggestions")
                            .setView(R.layout.fragment_suggestions)
                            .create();

                    dialog.show();
                    suggestions = new ArrayList<Suggestions>();
                    suggestions.add(new Suggestions(1, "Running","105 calories will burn", false));
                    suggestions.add(new Suggestions(2, "Dancing","98 calories will burn", false));
                    suggestions.add(new Suggestions(3, "Walking","77 calories will burn", false));
                    suggestions.add(new Suggestions(4, "Jumping","78 calories will burn", false));

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
                            StringBuilder selected = new StringBuilder("Selected: \n");

                            for (int i = 0; i < suggestions.size(); i++){
                                if(suggestions.get(i).isChecked()){
                                    selected.append(i).append("\n");
                                }
                            }
                            Toast.makeText(getContext(), selected.toString(), Toast.LENGTH_SHORT).show();

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
                    break;
            }
        }
    };
}