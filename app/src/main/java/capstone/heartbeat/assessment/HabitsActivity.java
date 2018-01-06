package capstone.heartbeat.assessment;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.NumberPicker;

import capstone.heartbeat.R;

public class HabitsActivity extends AppCompatActivity implements NumberPicker.OnValueChangeListener {
    ImageButton btn_smoking_yes, btn_smoking_no, btn_smoker, btn_nonsmoker;
    Button btn_bptreat_no, btn_bptreat_yes, btn_sedentary, btn_light, btn_moderate, btn_very, btn_extreme;

    public boolean selected = false, selectNew = true;
    public int smoker = 0, non_smoker = 0;


    private boolean viewGroupIsVisible = false;
    private View viewGroup_notsmoking, viewGroup_sticks;
    public String smoke,smk_quantity,non_smkr,bptr,activ,sedentary,light,moderate,very,extreme;

    public String freetime;

    SharedPreferences prefs;
    SharedPreferences.Editor editor ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habits);

        prefs = getSharedPreferences("values",MODE_PRIVATE);
        editor = prefs.edit();

        viewGroup_notsmoking = findViewById(R.id.viewGroup_notsmoking);

        btn_smoking_yes = (ImageButton) findViewById(R.id.btn_smoking_yes);
        btn_smoking_no = (ImageButton) findViewById(R.id.btn_smoking_no);
        btn_smoker = (ImageButton)findViewById(R.id.btn_smoker);
        btn_nonsmoker = (ImageButton)findViewById(R.id.btn_nonsmoker);
        btn_bptreat_no = (Button)findViewById(R.id.btn_bptreat_no);
        btn_bptreat_yes =(Button)findViewById(R.id.btn_bptreat_yes);

        btn_sedentary = (Button) findViewById(R.id.btn_sedentary);
        btn_light = (Button) findViewById(R.id.btn_light);
        btn_moderate = (Button) findViewById(R.id.btn_moderate);
        btn_very = (Button) findViewById(R.id.btn_very);
        btn_extreme = (Button) findViewById(R.id.btn_extreme);

        btn_bptreat_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bptr = "0";
                btn_bptreat_no.setSelected(true);
                btn_bptreat_yes.setSelected(false);
                int bptrs = Integer.parseInt(bptr);
                editor.putInt("bptr",bptrs);
                editor.commit();
            }
        });

        btn_bptreat_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bptr = "1";
                btn_bptreat_no.setSelected(false);
                btn_bptreat_yes.setSelected(true);
                int bptrs = Integer.parseInt(bptr);
                editor.putInt("bptr",bptrs);
                editor.commit();
            }
        });

        btn_smoking_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smoke = "0";
                btn_smoking_no.setSelected(true);
                btn_smoking_yes.setSelected(false);
                if(viewGroupIsVisible || (viewGroup_notsmoking.getVisibility() == View.GONE)){
                    viewGroup_notsmoking.setVisibility(View.VISIBLE);
                    viewGroup_sticks.setVisibility(View.GONE);
                }else if(viewGroup_sticks.getVisibility() == View.VISIBLE){
                    viewGroup_notsmoking.setVisibility(View.GONE);
                    btn_smoking_no.setSelected(false);
                }
            }
        });

        btn_smoking_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_smoking_no.setSelected(false);
                btn_smoking_yes.setSelected(true);
                if(viewGroupIsVisible || (viewGroup_sticks.getVisibility() == View.GONE)){
                    viewGroup_sticks.setVisibility(View.VISIBLE);
                    viewGroup_notsmoking.setVisibility(View.GONE);
                }else if(viewGroup_notsmoking.getVisibility() == View.VISIBLE){
                    viewGroup_sticks.setVisibility(View.GONE);
                    btn_smoking_yes.setSelected(false);
                }
            }
        });
        btn_smoker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                non_smkr = "0";
                btn_smoker.setSelected(true);
                btn_nonsmoker.setSelected(false);
                editor.putInt("smoke_type",1);
                editor.commit();
            }
        });
        btn_nonsmoker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                non_smkr = "1";
                btn_nonsmoker.setSelected(true);
                btn_smoker.setSelected(false);
                editor.putInt("smoke_type",0);
                editor.commit();

            }
        });

        Button numOfSticks = (Button) findViewById(R.id.numOfSticks);
        viewGroup_sticks = findViewById(R.id.viewGroup_sticks);

        numOfSticks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNumOfSticks();
                smoke = "1";
            }
        });

        btn_sedentary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_sedentary.setSelected(true);
                btn_light.setSelected(false);
                btn_moderate.setSelected(false);
                btn_very.setSelected(false);
                btn_extreme.setSelected(false);
                sedentary = "1";
                editor.putInt("physical_type",1);
                editor.commit();
            }
        });

        btn_light.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_sedentary.setSelected(false);
                btn_light.setSelected(true);
                btn_moderate.setSelected(false);
                btn_very.setSelected(false);
                btn_extreme.setSelected(false);
                light = "2";
                editor.putInt("physical_type",2);
                editor.commit();
            }
        });

        btn_moderate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_sedentary.setSelected(false);
                btn_light.setSelected(false);
                btn_moderate.setSelected(true);
                btn_very.setSelected(false);
                btn_extreme.setSelected(false);
                moderate = "3";
                editor.putInt("physical_type",3);
                editor.commit();
            }
        });

        btn_very.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_sedentary.setSelected(false);
                btn_light.setSelected(false);
                btn_moderate.setSelected(false);
                btn_very.setSelected(true);
                btn_extreme.setSelected(false);
                very = "4";
                editor.putInt("physical_type",4);
                editor.commit();
            }
        });

        btn_extreme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_sedentary.setSelected(false);
                btn_light.setSelected(false);
                btn_moderate.setSelected(false);
                btn_very.setSelected(false);
                btn_extreme.setSelected(true);
                extreme = "5";
                editor.putInt("physical_type",5);
                editor.commit();
            }
        });

        Button btn_freetime = (Button) findViewById(R.id.freetime);
        btn_freetime.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                showFreeTime();
            }
        });

        Button btn_sleeptime = (Button) findViewById(R.id.sleeptime);
        btn_sleeptime.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                showSleepTime();
            }
        });
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

        Log.i("value is",""+newVal);
    }

    public void showFreeTime() {

        final Dialog d = new Dialog(HabitsActivity.this);
        d.setTitle("Set your free time");
        d.setContentView(R.layout.ft_dialog);
        Button b1 = (Button) d.findViewById(R.id.ft_set);
        Button b2 = (Button) d.findViewById(R.id.ft_cancel);
        final NumberPicker np = (NumberPicker) d.findViewById(R.id.freetime_hr);
        np.setMaxValue(4);
        np.setMinValue(1);
        np.setWrapSelectorWheel(false);
        np.setOnValueChangedListener(this);

        final NumberPicker np1 = (NumberPicker) d.findViewById(R.id.freetime_min);
        np1.setMaxValue(59);
        np1.setMinValue(0);
        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if(newVal == 4){
                    np1.setMaxValue(0);
                }else{
                    np1.setMaxValue(59);
                }
            }
        });
        np1.setWrapSelectorWheel(false);
        np1.setOnValueChangedListener(this);
        np1.setFormatter(new NumberPicker.Formatter() {
            public String format(int value) {
                return String.format("%02d", value);
            }
        });
        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Button btn_freetime = (Button) findViewById(R.id.freetime);
                if(np.getValue() == 1){
                    btn_freetime.setText(String.format("%02d:%02d", np.getValue(), np1.getValue()) + " hour");
                    freetime = String.format("%02d:%02d", np.getValue(), np1.getValue()) + " hour";
                    editor.putString("freetime", freetime);
                    editor.commit();
                }else{
                    btn_freetime.setText(String.format("%02d:%02d", np.getValue(), np1.getValue()) + " hours");
                    freetime = String.format("%02d:%02d", np.getValue(), np1.getValue()) + " hours";
                    editor.putString("freetime", freetime);
                    editor.commit();
                }
                d.dismiss();
            }
        });
        b2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
        d.show();

    }

    public void showSleepTime() {

        final Dialog d = new Dialog(HabitsActivity.this);
        d.setTitle("Set your sleeping time");
        d.setContentView(R.layout.st_dialog);
        Button b1 = (Button) d.findViewById(R.id.st_set);
        Button b2 = (Button) d.findViewById(R.id.st_cancel);
        final NumberPicker np = (NumberPicker) d.findViewById(R.id.sleeptime_hr);
        np.setMaxValue(11);
        np.setMinValue(7);
        np.setWrapSelectorWheel(false);
        np.setOnValueChangedListener(this);

        final NumberPicker np1 = (NumberPicker) d.findViewById(R.id.sleeptime_min);
        np1.setMaxValue(59);
        np1.setMinValue(0);
        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if(newVal == 11){
                    np1.setMaxValue(0);
                }else{
                    np1.setMaxValue(59);
                }
            }
        });
        np1.setWrapSelectorWheel(false);
        np1.setOnValueChangedListener(this);
        np1.setFormatter(new NumberPicker.Formatter() {
            public String format(int value) {
                return String.format("%02d", value);
            }
        });
        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Button btn_freetime = (Button) findViewById(R.id.sleeptime);

                btn_freetime.setText(String.format("%02d:%02d", np.getValue(), np1.getValue()) + " PM");
                d.dismiss();
            }
        });
        b2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
        d.show();
    }


    public void showNumOfSticks() {

        final Dialog d = new Dialog(HabitsActivity.this);
        d.setTitle("Sticks per day");
        d.setContentView(R.layout.sticks_dialog);
        Button b1 = (Button) d.findViewById(R.id.sticks_set);
        Button b2 = (Button) d.findViewById(R.id.sticks_cancel);
        final NumberPicker np = (NumberPicker) d.findViewById(R.id.sticks);
        np.setMaxValue(30);
        np.setMinValue(1);
        np.setWrapSelectorWheel(false);
        np.setOnValueChangedListener(this);

        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Button numOfSticks = (Button) findViewById(R.id.numOfSticks);
                smk_quantity = String.valueOf(np.getValue());
                int quantity = np.getValue();
                int smoker_type;
                numOfSticks.setText(String.valueOf(np.getValue()) + " sticks per day");
                if(quantity > 0 && quantity <= 10){
                    smoker_type = 2;
                    editor.putInt("smoke_type",smoker_type);
                    editor.commit();
                }else if(quantity >=11 && quantity <20){
                    smoker_type = 3;
                    editor.putInt("smoke_type",smoker_type);
                    editor.commit();
                }else if (quantity>20){
                    smoker_type = 4;
                    editor.putInt("smoke_type",smoker_type);
                    editor.commit();
                }
                d.dismiss();
            }
        });
        b2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
        d.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.next_button, menu);
        return true;
    }

    public void saveTemp(String v1,String v2, String v3, String v4){
        int smoke = Integer.parseInt(v1);
        int non_smkr = Integer.parseInt(v2);
        int quantity = Integer.parseInt(v3);
        int bptr = Integer.parseInt(v4);
        int smoker_type = 0;

        SharedPreferences prefs = getSharedPreferences("values",MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();


        if(smoke == 0){
            if(non_smkr == 1){
                smoker_type = 0;
                editor.putInt("smoke_type",smoker_type);
                editor.commit();
            }else if(non_smkr == 0){
                smoker_type = 1;
                editor.putInt("smoke_type",smoker_type);
                editor.commit();
            }

        }else if (smoke == 1){
            if(quantity > 0 && quantity <= 10){
                smoker_type = 3;
                editor.putInt("smoke_type",smoker_type);
                editor.commit();
            }else if(quantity >=11 && quantity <20){
                smoker_type = 4;
                editor.putInt("smoke_type",smoker_type);
                editor.commit();
            }else if (quantity>20){
                smoker_type = 5;
                editor.putInt("smoke_type",smoker_type);
                editor.commit();
            }
        }

        editor.putInt("bptr",bptr);
        editor.commit();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up next_button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.next) {

            startActivity(new Intent(getApplicationContext(),HistoryActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
