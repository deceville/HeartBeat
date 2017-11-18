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

   private TextView haresult,stresult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_input3);

        Bundle bundle = getIntent().getExtras();
        double res = bundle.getDouble("result");
        double stroke = bundle.getDouble("stroke");
        String str = Double.toString(stroke);
        String ress = Double.toString(res);
        haresult = (TextView)findViewById(R.id.haresult);
        stresult = (TextView)findViewById(R.id.stresult);
        stresult.setText(str);
        haresult.setText(ress);
    }
}
