package capstone.heartbeat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class SuggestionsFragment extends Fragment{
    ArrayList<Activity> suggestions;
    ListAdapter adapter;
    Button btn_addSuggestion, btn_cancel;

    public SuggestionsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_suggestions, container, false);
        btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
        ActivityDatabase myDB = new ActivityDatabase(getContext());
        btn_cancel.setVisibility(View.GONE);
        suggestions = new ArrayList<Activity>();
        suggestions = myDB.getActivities();

        adapter = new ListAdapter(getContext(), suggestions);

        ListView lvMain = (ListView) view.findViewById(R.id.lv_suggestions);
        lvMain.setAdapter(adapter);

        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        btn_addSuggestion = (Button) view.findViewById(R.id.btn_addSuggestion);

       /* btn_addSuggestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder selected = new StringBuilder("Selected: \n");

                for (int i = 0; i < suggestions.size(); i++){
                    if(suggestions.get(i).isChecked()){
                        selected.append(i).append("\n");
                    }
                }
                Toast.makeText(getContext(), selected.toString(), Toast.LENGTH_SHORT).show();
            }
        });*/

        // Inflate the layout for this fragment
        return view;
    }


}
