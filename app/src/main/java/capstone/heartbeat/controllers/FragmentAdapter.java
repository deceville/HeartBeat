package capstone.heartbeat.controllers;

import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import capstone.heartbeat.fragments.GoalsFragment;
import capstone.heartbeat.fragments.PlansFragment;
import capstone.heartbeat.fragments.ResultsFragment;
import capstone.heartbeat.fragments.SuggestionsFragment;

/**
 * Created by Lenevo on 3/11/2018.
 */

public class FragmentAdapter extends FragmentPagerAdapter {
    SharedPreferences prefs;
    int id;

    public FragmentAdapter(FragmentManager fm, SharedPreferences prefs, int id){
        super(fm);
        this.prefs = prefs;
        this.id = id;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new ResultsFragment(prefs, id);
            case 1:
                return new SuggestionsFragment(prefs);
            case 2:
                return new PlansFragment(prefs);
            case 3:
                return new GoalsFragment(prefs, id);
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            //
            //Your tab titles
            //
            case 0: return "Results";
            case 1: return "Plans";
            case 2: return "Suggestions";
            case 3: return "Goals";
            default: return null;
        }
    }
}