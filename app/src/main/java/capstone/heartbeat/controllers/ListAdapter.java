package capstone.heartbeat.controllers;

/**
 * Created by Lenevo on 12/8/2017.
 */

import java.sql.SQLOutput;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import capstone.heartbeat.R;
import capstone.heartbeat.models.Activity;
import capstone.heartbeat.models.Suggestions;

public class ListAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    List<Activity> suggestions = null;
    SharedPreferences prefs;
    int finalCount = 0;
    boolean newState = false;
    boolean unchecked;

    public ListAdapter(Context context, ArrayList<Activity> suggestions) {
        ctx = context;
        this.suggestions = suggestions;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return suggestions.size();
    }

    @Override
    public Object getItem(int position) {
        return suggestions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public boolean isChecked(int position){
        return suggestions.get(position).checked;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.list_suggestions, parent, false);
        }

        prefs = ctx.getSharedPreferences("values",Context.MODE_PRIVATE);
        final int weight = prefs.getInt("weight",0);
        final int counts = prefs.getInt("count",0);
        final double met = suggestions.get(position).getMETS();
        double cal = ((met*3.5*weight)/200)*15;
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        final double total = prefs.getInt("free",60);
        int min = 15;
        ((TextView) view.findViewById(R.id.title_suggestions)).setText(suggestions.get(position).getActivities());
        ((TextView) view.findViewById(R.id.desc_suggestions)).setText(df.format(cal)+" cal will burn every "+min+" minutes.");

        final CheckBox cbox = (CheckBox) view.findViewById(R.id.cbox_suggestions);
        cbox.setChecked(suggestions.get(position).checked); //false
        cbox.setTag(position);
        final View finalView = view;
        final SharedPreferences.Editor ed = prefs.edit();
        ed.putInt("count",0);
        ed.apply();

        newState = !suggestions.get(position).isChecked();

        cbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unchecked = false;
                int count = prefs.getInt("count",finalCount);
                String sugg = suggestions.get(position).Activities;

                if (cbox.isChecked()){//if not checked
                    if (finalCount * 15 < total) {
                        suggestions.get(position).checked = newState; // true
                        finalCount++;
                        System.out.println("Checked: "+finalCount);
                        ed.putInt("count", count);
                        ed.apply();
                    }else{
                        cbox.setChecked(false);
                        unchecked = true;
                        System.out.println("Count:" +finalCount);
                        Toast.makeText(ctx,"Limited time only!",Toast.LENGTH_SHORT).show();
                    }
                }else{//if checked
                    if(!unchecked){
                        finalCount--;
                        cbox.setChecked(false);
                        suggestions.get(position).checked = !newState;
                        System.out.println("Deleted: "+finalCount);
                        ed.putInt("count", count);
                        ed.apply();
                    }

                }
            }
        });


        //Save suggestion id to tag
        view.setTag(suggestions.get(position).getId());
        return view;
    }


    Suggestions getSuggestion(int position) {
        return ((Suggestions) getItem(position));
    }

    /*ArrayList<Suggestions> getBox() {
        ArrayList<Suggestions> box = new ArrayList<Suggestions>();
        for (Suggestions p : suggestions) {
            if (p.box)
                box.add(p);
        }
        return box;
    }*/

    /*OnCheckedChangeListener myCheckChangList = new OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
            getSuggestion((Integer) buttonView.getTag()).box = isChecked;
        }
    };*/
}