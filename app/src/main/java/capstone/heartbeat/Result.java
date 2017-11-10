package capstone.heartbeat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Result extends AppCompatActivity {

   private TextView haresult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_input3);

        Bundle bundle = new Bundle();
        double res = bundle.getDouble("result");
        String ress = Double.toString(res);
        haresult = (TextView)findViewById(R.id.haresult);
        haresult.setText(ress);
    }
}
