package capstone.heartbeat.assessment;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import capstone.heartbeat.controllers.PickerAdapter;
import capstone.heartbeat.R;
import capstone.heartbeat.models.ScaleView;
import capstone.heartbeat.onViewUpdateListener;
import travel.ithaka.android.horizontalpickerlib.PickerLayoutManager;

public class DemographicsActivity extends AppCompatActivity {
    public Button btn_birthdate, btn_female, btn_male;
    private TextView txtValue;
    public ScaleView rulerViewMm;
    public ImageView avatar;
    RecyclerView rv;
    PickerAdapter adapter;

    public float height;

    public int age, currYear, currMonth, currDay;
    boolean selected = false;
    int male = 0;
    int female = 0;
    int gender ;
    public String weight;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demographics);

        prefs = getSharedPreferences("values",MODE_PRIVATE);
        editor = prefs.edit();
        btn_birthdate = (Button)findViewById(R.id.btn_birthdate);
        btn_female = (Button)findViewById(R.id.btn_female);
        btn_male = (Button)findViewById(R.id.btn_male);

        btn_female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected = true;
                female = 1;
                male = 0;
                gender = 0;
                editor.putInt("Gender", gender);

                if(selected && (male == 1 || female == 0)){
                    btn_male.setBackgroundColor(getResources().getColor(R.color.bg_screen2));
                    btn_female.setBackgroundColor(getResources().getColor(R.color.progress_gray));
                }else if(selected && (female == 1 || male == 0)){
                    btn_female.setBackgroundColor(getResources().getColor(R.color.bg_screen3));
                    btn_male.setBackgroundColor(getResources().getColor(R.color.progress_gray));
                }

                setFemaleCharacter(age);
            }
        });

        btn_male.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                selected = true;
                female = 0;
                male = 1;
                gender = 1;
                editor.putInt("Gender", gender);

                if(selected && (male == 1 || female == 0)){
                    btn_male.setBackgroundColor(getResources().getColor(R.color.bg_screen2));
                    btn_female.setBackgroundColor(getResources().getColor(R.color.progress_gray));
                }else if(selected && (female == 1 || male == 0)){
                    btn_female.setBackgroundColor(getResources().getColor(R.color.bg_screen3));
                    btn_male.setBackgroundColor(getResources().getColor(R.color.progress_gray));
                }

                setMaleCharacter(age);
            }
        });


        btn_birthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                currYear = c.get(Calendar.YEAR);
                currMonth = c.get(Calendar.MONTH);
                currDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(DemographicsActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog,
                        new mDateSetListener(), currYear, currMonth, currDay){
                    @Override
                    public void onCreate(Bundle savedInstanceState)
                    {
                        super.onCreate(savedInstanceState);
                        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    }
                };
                dialog.show();
            }
        });



        final ScaleView rulerViewMm = (ScaleView) findViewById(R.id.my_scale);
        txtValue = (TextView) findViewById(R.id.txt_height);
        rulerViewMm.setStartingPoint(160f);
        rulerViewMm.setUpdateListener(new onViewUpdateListener() {

            @Override
            public void onViewUpdate(float result) {
                height = (float) Math.round(result * 10f) / 10f;
                editor.putInt("height",(int)height);
                txtValue.setText(height + " cm");
            }
        });

        rv = (RecyclerView) findViewById(R.id.rv);

        PickerLayoutManager pickerLayoutManager = new PickerLayoutManager(this, PickerLayoutManager.HORIZONTAL, false);
        pickerLayoutManager.setChangeAlpha(true);
        pickerLayoutManager.setScaleDownBy(0.99f);
        pickerLayoutManager.setScaleDownDistance(0.8f);

        adapter = new PickerAdapter(this, getData(200), rv);
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(rv);
        rv.setLayoutManager(pickerLayoutManager);
        rv.setAdapter(adapter);

        pickerLayoutManager.setOnScrollStopListener(new PickerLayoutManager.onScrollStopListener() {
            @Override
            public void selectedView(View view) {
                weight = ((TextView)view).getText().toString();
                weight = weight.substring(0,weight.length()-3);
                int weigh = Integer.parseInt(weight);
                editor.putInt("weight", weigh);

            }
        });
    }

    public List<String> getData(int count) {
        List<String> data = new ArrayList<>();
        for (int i = 30; i < count; ++i) {
            data.add(String.valueOf(i));
        }
        return data;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.next_button, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up next_button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.next) {

            editor.commit();
            startActivity(new Intent(getApplicationContext(),LaboratoryActivity.class));
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    class mDateSetListener implements DatePickerDialog.OnDateSetListener {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            // getCalender();
            int mYear = year;
            int mMonth = monthOfYear;
            int mDay = dayOfMonth;

            StringBuilder birth =  new StringBuilder();
            birth.append(mMonth + 1).append("/").append(mDay).append("/")
                    .append(mYear).append(" ");
            String birthday = birth.toString();
            btn_birthdate.setText(birth);
            age = currYear - mYear;
            editor.putString("birth",birthday);
            editor.putInt("age",age);
            editor.commit();
            System.out.println(btn_birthdate.getText().toString() + "Age: " + age);
            if(age > 84 || age < 25){
                alertAgeLimit();
            }

            if(male == 1 && female == 0){
                setMaleCharacter(age);
            }else if(female == 1 && male == 0){
                setFemaleCharacter(age);
            }
        }
    }

    public void setMaleCharacter (int age){

        avatar = (ImageView) findViewById(R.id.avatar);

        if(age >= 25 && age <= 35){
            avatar.setVisibility(View.VISIBLE);
            avatar.setImageResource(R.drawable.char_male_01);
        }else if (age <= 45 && age >= 36){
            avatar.setVisibility(View.VISIBLE);
            avatar.setImageResource(R.drawable.char_male_02);
        }else if (age <= 55 && age >= 46){
            avatar.setVisibility(View.VISIBLE);
            avatar.setImageResource(R.drawable.char_male_03);
        }else if (age <= 70 && age >= 56){
            avatar.setVisibility(View.VISIBLE);
            avatar.setImageResource(R.drawable.char_male_04);
        }else if (age <= 84 && age >= 71){
            avatar.setVisibility(View.VISIBLE);
            avatar.setImageResource(R.drawable.char_male_05);
        }else{
            avatar.setVisibility(View.INVISIBLE);
        }

    }

    public void setFemaleCharacter (int age){

        avatar = (ImageView) findViewById(R.id.avatar);
        if(age >= 25 && age <= 35){
            avatar.setVisibility(View.VISIBLE);
            avatar.setImageResource(R.drawable.char_female_01);
        }else if (age <= 45 && age >= 36){
            avatar.setVisibility(View.VISIBLE);
            avatar.setImageResource(R.drawable.char_female_02);
        }else if (age <= 55 && age >= 46){
            avatar.setVisibility(View.VISIBLE);
            avatar.setImageResource(R.drawable.char_female_03);
        }else if (age <= 70 && age >= 56){
            avatar.setVisibility(View.VISIBLE);
            avatar.setImageResource(R.drawable.char_female_04);
        }else if (age <= 84 && age >= 71){
            avatar.setVisibility(View.VISIBLE);
            avatar.setImageResource(R.drawable.char_female_03);
        }else{
            avatar.setVisibility(View.INVISIBLE);
        }

    }

    public void alertAgeLimit (){
        AlertDialog.Builder builder = new AlertDialog.Builder(DemographicsActivity.this, R.style.Theme_AppCompat_Dialog_Alert);

        builder.setTitle("Oh no!")
                .setMessage("Your current age is not allowed. Ages only between 25 and 84 are allowed to continue.")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        btn_birthdate.setText("MONTH / DAY / YEAR");
                        btn_birthdate.setTextColor(getResources().getColor(R.color.progress_gray_trans));
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert);

        AlertDialog alert = builder.create();
        alert.show();
    }

    public boolean alertHeightLimit (){
        AlertDialog.Builder builder = new AlertDialog.Builder(DemographicsActivity.this, R.style.Theme_AppCompat_Dialog_Alert);
        final ScaleView rulerViewM = (ScaleView) findViewById(R.id.my_scale);
        builder.setTitle("Oh no!")
                .setMessage("Your maximum height is 300cm.")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                       rulerViewM.setStartingPoint(160f);
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert);

        AlertDialog alert = builder.create();
        alert.show();
        return true;
    }
}
