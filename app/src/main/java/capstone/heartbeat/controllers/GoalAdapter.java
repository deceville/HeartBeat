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

        ((TextView) view.findViewById(R.id.title_goals)).setText(goals.get(position).getDescription());

        return view;
    }
}
