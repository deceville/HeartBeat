package capstone.heartbeat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SampleInput1 extends AppCompatActivity {

    public EditText age, weight, height, totalchl, hdl, sbp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_input1);

        age = (EditText)findViewById(R.id.Age);
        weight = (EditText)findViewById(R.id.weight);
        height = (EditText)findViewById(R.id.height);
        totalchl = (EditText)findViewById(R.id.chl);
        hdl = (EditText)findViewById(R.id.hdl);
        sbp = (EditText)findViewById(R.id.sbp);

        final String v1,v2,v3,v4,v5,v6;
        v1 = age.getText().toString();
        v2 = weight.getText().toString();
        v3 = height.getText().toString();
        v4 = totalchl.getText().toString();
        v5 = hdl.getText().toString();
        v6 = sbp.getText().toString();


        final double i1,i2,i3,i4,i5,i6;

        i1 = Double.parseDouble(v1);
        i2 = Double.parseDouble(v2);
        i3 = Double.parseDouble(v3);
        i4 = Double.parseDouble(v4);
        i5 = Double.parseDouble(v5);
        i6 = Double.parseDouble(v6);

        Button next = (Button)findViewById(R.id.next);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle extras = new Bundle();
                extras.putDouble("age",i1);
                extras.putDouble("weight",i2);
                extras.putDouble("height",i3);
                extras.putDouble("chl",i4);
                extras.putDouble("hdl",i5);
                extras.putDouble("sbp",i6);


                Intent intent = new Intent(getApplicationContext(),SampleInput2.class);
                intent.putExtras(extras);
                startActivity(intent);

            }
        });

    }
}
