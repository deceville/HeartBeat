package capstone.heartbeat.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import capstone.heartbeat.R;


public class GoalsFragment extends Fragment{
    ProgressBar progress1, progress2, progress3;

    public GoalsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_goals, container, false);

        progress1 = (ProgressBar) view.findViewById(R.id.progress1);
        progress2 = (ProgressBar) view.findViewById(R.id.progress2);
        progress3 = (ProgressBar) view.findViewById(R.id.progress3);

        return view;
    }

}
