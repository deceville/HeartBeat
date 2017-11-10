package capstone.heartbeat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class SampleInput2 extends AppCompatActivity {

    public RadioGroup gender,type1,type2,rha,ckd,chf,heartAttack,bp,fhcvd,af;
    public String v1,v2,v3,v4,v5,v6,v7,v8,v9,v10;
    public Button go;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_input2);

        gender= (RadioGroup)findViewById(R.id.gender);
        type1=(RadioGroup)findViewById(R.id.type1);
        type2=(RadioGroup)findViewById(R.id.type2);
        rha =(RadioGroup)findViewById(R.id.rha);
        ckd=(RadioGroup)findViewById(R.id.ckd);
        chf = (RadioGroup)findViewById(R.id.chf);
        heartAttack = (RadioGroup)findViewById(R.id.heartAttack);
        bp = (RadioGroup)findViewById(R.id.bp);
        fhcvd = (RadioGroup)findViewById(R.id.fhcvd);
        af = (RadioGroup)findViewById(R.id.af);


        v1 = ((RadioButton)findViewById(gender.getCheckedRadioButtonId())).getText().toString();

        v2 = ((RadioButton)findViewById(type1.getCheckedRadioButtonId())).getText().toString();

        v3 = ((RadioButton)findViewById(type2.getCheckedRadioButtonId())).getText().toString();
        v4 = ((RadioButton)findViewById(rha.getCheckedRadioButtonId())).getText().toString();
        v5 = ((RadioButton)findViewById(ckd.getCheckedRadioButtonId())).getText().toString();
        v6 = ((RadioButton)findViewById(chf.getCheckedRadioButtonId())).getText().toString();
        v7 = ((RadioButton)findViewById(heartAttack.getCheckedRadioButtonId())).getText().toString();
        v8 = ((RadioButton)findViewById(bp.getCheckedRadioButtonId())).getText().toString();
        v9 = ((RadioButton)findViewById(fhcvd.getCheckedRadioButtonId())).getText().toString();
        v10 = ((RadioButton)findViewById(af.getCheckedRadioButtonId())).getText().toString();


        final Bundle bundle = getIntent().getExtras();


        final int[] bool = {5,4,convert(v10),convert(v2),convert(v3),convert(v2),convert(v9),convert(v4),convert(v8)};
        if (bundle.getDouble("chl")!=0){
            final double[] continuous = {bundle.getDouble("age"),bundle.getDouble("sbp"),bundle.getDouble("chl"),bundle.getDouble("hdl"),bundle.getDouble("height")
            ,bundle.getDouble("weight")};

            go.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (v1.equals("Male")){
                        Qrisk2Male qrisk2m = new Qrisk2Male();
                        qrisk2m.setValue(continuous,bool);
                        double v = qrisk2m.getResult();
                        Bundle b = new Bundle();
                        b.putDouble("result",v);
                        Intent intent = new Intent(getApplicationContext(),Result.class);
                        intent.putExtras(bundle);

                        startActivity(intent);
                    }else
                        Toast.makeText(getApplicationContext(),"An error occurred!",Toast.LENGTH_SHORT);
                }
            });

        }


    }


    public int convert(String value){
        int val = 0;
        switch (value){
            case "Yes": val =1;return val;
            case "No": val = 0;return val;
        }
        return val;
    }
}
