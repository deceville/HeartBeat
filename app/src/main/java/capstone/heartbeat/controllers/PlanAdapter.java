package capstone.heartbeat.controllers;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;

import java.util.List;

import capstone.heartbeat.R;
import capstone.heartbeat.models.Plan;
import capstone.heartbeat.others.PlanActivitiesActivity;

/**
 * Created by torre on 2/13/2018.
 */

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.PlanViewHolder>{

    private Context ctx;
    private List<Plan> plans;

    public PlanAdapter(Context ctx, List<Plan> plans) {
        this.ctx = ctx;
        this.plans = plans;
    }


    @Override
    public PlanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.input_card,null);
        return new PlanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PlanViewHolder holder, int position) {
        Plan plan = plans.get(position);

        holder.title.setText(plan.getTitle());
        holder.description.setText(plan.getProgress()+"/"+plan.getMax());
        holder.progBar.setMax((float)plan.getMax());
        holder.progBar.setProgress((float)plan.getProgress());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ctx,holder.title.getText(),Toast.LENGTH_SHORT).show();
                ctx.startActivity(new Intent(ctx, PlanActivitiesActivity.class));
            }
        });

        holder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ctx,"Menu clicked!",Toast.LENGTH_SHORT).show();

            }
        });





    }

    @Override
    public int getItemCount() {
        return plans.size();
    }

    class PlanViewHolder extends RecyclerView.ViewHolder {

        TextView title,description;
        ImageButton more;
        RoundCornerProgressBar progBar;
        CardView cardView;



        public PlanViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.card_title);
            description = (TextView) itemView.findViewById(R.id.prog_desc);
            more = (ImageButton) itemView.findViewById(R.id.more_button);
            progBar = (RoundCornerProgressBar) itemView.findViewById(R.id.progress);
            cardView = (CardView) itemView.findViewById(R.id.cardView);


        }


    }
}
