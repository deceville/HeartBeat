package capstone.heartbeat.controllers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import capstone.heartbeat.R;
import capstone.heartbeat.models.Goal;

/**
 * Created by Lenevo on 1/21/2018.
 */

public class GoalAdapter extends BaseAdapter{
    List<Goal> goals = null;
    LayoutInflater lInflater;
    Context context;

    public GoalAdapter(Context context, ArrayList<Goal> goals) {
        this.context = context;
        this.goals = goals;
        lInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return goals.size();
    }

    @Override
    public Object getItem(int position) {

        return goals.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.list_goals, parent, false);
        }
        String description;
        double value = goals.get(position).getValue();
        String duration =goals.get(position).getDuration();
        switch (goals.get(position).getDescription()){
            case "BMI": {
                if (goals.get(position).getAction().equals("reduce")){
                    description = "Reduce " + value + " kilograms in " + duration;
                    ((TextView) view.findViewById(R.id.title_goals)).setText(description);
                }else if (goals.get(position).getAction().equals("gain")){
                    description = "Gain " + value + " kilograms in " + duration;
                    ((TextView) view.findViewById(R.id.title_goals)).setText(description);
                }else {
                    description = "Maintain " + value + " kilograms in " + duration;
                    ((TextView) view.findViewById(R.id.title_goals)).setText(description);
                }
            }
            case "Total Cholesterol":{
                if (goals.get(position).getAction().equals("reduce")){
                    description = "Reduce " + value + " mm/dl in " + duration;
                    ((TextView) view.findViewById(R.id.title_goals)).setText(description);
                }else if (goals.get(position).getAction().equals("gain")){
                    description = "Gain " + value + " mm/dl in " + duration;
                    ((TextView) view.findViewById(R.id.title_goals)).setText(description);
                }else {
                    description = "Maintain " + value + " mm/dl in " + duration;
                    ((TextView) view.findViewById(R.id.title_goals)).setText(description);
                }
            }
            case "HDL":{
                if (goals.get(position).getAction().equals("reduce")){
                    description = "Reduce " + value + " mm/dl in " + duration;
                    ((TextView) view.findViewById(R.id.title_goals)).setText(description);
                }else if (goals.get(position).getAction().equals("gain")){
                    description = "Gain " + value + " mm/dl in " + duration;
                    ((TextView) view.findViewById(R.id.title_goals)).setText(description);
                }else {
                    description = "Maintain " + value + " mm/dl in " + duration;
                    ((TextView) view.findViewById(R.id.title_goals)).setText(description);
                }
            }

        }

        ((TextView) view.findViewById(R.id.duration_goals)).setText(goals.get(position).getDuration());

        return view;
    }
}
