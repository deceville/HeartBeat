package capstone.heartbeat.fragments;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.DonutProgress;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.MyAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.EntryXComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import capstone.heartbeat.R;
import capstone.heartbeat.fragments.tabfragments.BMIFragment;
import capstone.heartbeat.fragments.tabfragments.BloodPressureFragment;
import capstone.heartbeat.fragments.tabfragments.CholesterolFragment;

import static android.content.Context.MODE_PRIVATE;

public class ResultsFragment extends Fragment {

    private DonutProgress heartattack, stroke;
    private SharedPreferences pref;
    private FragmentTabHost mTabHost;

    public ResultsFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public ResultsFragment(SharedPreferences pref) {
        this.pref = pref;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_results, container, false);

        int ha = pref.getInt("haResult", 0);
        int st = pref.getInt("stResult", 0);

        heartattack = (DonutProgress) view.findViewById(R.id.progress_heartattack);
        stroke = (DonutProgress) view.findViewById(R.id.progress_stroke);

        heartattack.setProgress((float) ha);
        stroke.setProgress((float) st);

        mTabHost = (FragmentTabHost)view.findViewById(android.R.id.tabhost);
        mTabHost.setup(getActivity(), getChildFragmentManager(), android.R.id.tabcontent);

        mTabHost.addTab(mTabHost.newTabSpec("fragmentb").setIndicator("BMI"),
                BMIFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("fragmentc").setIndicator("Cholesterol"),
                CholesterolFragment.class, null);

        for (int i = 0; i < mTabHost.getTabWidget().getChildCount(); i++) {
            TextView tv = (TextView) mTabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title); //Unselected Tabs
            tv.setTextColor(getResources().getColor(R.color.progress_gray_trans));
        }
        setBackgroundColor();
        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                setBackgroundColor();
            }
        });
        Bundle arg2 = new Bundle();
        /*arg2.putInt("Arg for Frag2", 2);
        mTabHost.addTab(mTabHost.newTabSpec("Tab2").setIndicator("Frag Tab2"),
                MyNestedFragment2.class, arg2);*/

        return view;
    }

    private void setBackgroundColor() {
        int inactiveColor = getResources().getColor(R.color.dot_light_screen4);
        int activeColor = getResources().getColor(R.color.colorPrimary);

        // In this loop you will set the inactive tabs backgroung color
        for (int i = 0; i < mTabHost.getTabWidget().getChildCount(); i++) {
            mTabHost.getTabWidget().getChildAt(i).setBackgroundColor(inactiveColor);
            TextView tv = (TextView) mTabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title); //Unselected Tabs
            tv.setTextColor(getResources().getColor(R.color.progress_gray_trans));
        }

        // Here you will set the active tab background color
        mTabHost.getTabWidget().getChildAt(mTabHost.getCurrentTab()).setBackgroundColor(
                activeColor);
        TextView tv = (TextView) mTabHost.getTabWidget().getChildAt(mTabHost.getCurrentTab()).findViewById(android.R.id.title); //Unselected Tabs
        tv.setTextColor(Color.parseColor("#ffffff"));
    }
}
