package capstone.heartbeat.controllers;

import capstone.heartbeat.models.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import capstone.heartbeat.R;

/**
 * Created by torre on 2/13/2018.
 */

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ActivityViewHolder>{

    private Context ctx;
    public List<Activity> activities;

    public ActivityAdapter(Context ctx, List<Activity> activities) {
        this.ctx = ctx;
        this.activities = activities;
    }

    public ActivityAdapter(List<Activity> activities){
        this.activities = activities;
    }


    @Override
    public ActivityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.activity_row,null);
        return new ActivityViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(final ActivityViewHolder holder, int position) {
        Activity activity = activities.get(position);

        if (!activity.isDone()) {
            holder.title.setText(activity.getActivities());
            holder.calories.setText(new StringBuilder().append("").append(activity.getCalories()).toString());
        }else {
            holder.title.setText(activity.getActivities());
            holder.calories.setText(new StringBuilder().append("").append(activity.getCalories()).toString());
            holder.cardView.setCardBackgroundColor(ctx.getColor(R.color.cardview_dark_background));
            holder.cardView.setActivated(false);
            holder.cardView.setEnabled(false);
        }

    }

    @Override
    public int getItemCount() {
        return activities.size();
    }

    public class ActivityViewHolder extends RecyclerView.ViewHolder {

        TextView title,calories;
        CardView cardView;



        public ActivityViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title_activity);
            calories = (TextView) itemView.findViewById(R.id.title_calorie);
            cardView = (CardView) itemView.findViewById(R.id.cardView_row);


        }


    }
}
