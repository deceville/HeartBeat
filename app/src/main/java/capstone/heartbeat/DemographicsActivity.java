package capstone.heartbeat;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import travel.ithaka.android.horizontalpickerlib.PickerLayoutManager;

public class DemographicsActivity extends AppCompatActivity {
    public Button btn_birthdate, btn_female, btn_male;
    private TextView txtValue;
    public ScaleView rulerViewMm;
    RecyclerView rv;
    PickerAdapter adapter;

    public float height;

    public int age, currYear, currMonth, currDay;
    boolean selected = false;
    int male = 0;
    int female = 0;
    public String weight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demographics1);

        btn_birthdate = (Button)findViewById(R.id.btn_birthdate);
        btn_female = (Button)findViewById(R.id.btn_female);
        btn_male = (Button)findViewById(R.id.btn_male);

        btn_female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected = true;
                female = 1;
                male = 0;

                if(selected && (male == 1 || female == 0)){
                    btn_male.setBackgroundColor(getResources().getColor(R.color.bg_screen2));
                    btn_female.setBackgroundColor(getResources().getColor(R.color.progress_gray));
                }else if(selected && (female == 1 || male == 0)){
                    btn_female.setBackgroundColor(getResources().getColor(R.color.bg_screen3));
                    btn_male.setBackgroundColor(getResources().getColor(R.color.progress_gray));
                }
            }
        });

        btn_male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected = true;
                female = 0;
                male = 1;

                if(selected && (male == 1 || female == 0)){
                    btn_male.setBackgroundColor(getResources().getColor(R.color.bg_screen2));
                    btn_female.setBackgroundColor(getResources().getColor(R.color.progress_gray));
                }else if(selected && (female == 1 || male == 0)){
                    btn_female.setBackgroundColor(getResources().getColor(R.color.bg_screen3));
                    btn_male.setBackgroundColor(getResources().getColor(R.color.progress_gray));
                }
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
                        new mDateSetListener(), currYear, currMonth, currDay);
                dialog.show();
            }
        });



        final ScaleView rulerViewMm = (ScaleView) findViewById(R.id.my_scale);
        txtValue = (TextView) findViewById(R.id.txt_height);
        rulerViewMm.setStartingPoint(160);
        rulerViewMm.setUpdateListener(new onViewUpdateListener() {

            @Override
            public void onViewUpdate(float result) {
                height = (float) Math.round(result * 10f) / 10f;
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

            SharedPreferences prefs = getSharedPreferences("values",MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("Age", age);
            editor.putBoolean("Gender", selected);
            editor.putFloat("Height", height);
            editor.putString("Weight",weight );
            startActivity(new Intent(getApplicationContext(),LaboratoryActivity.class));
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
            btn_birthdate.setText(new StringBuilder()
                    // Month is 0 based so add 1
                    .append(mMonth + 1).append("/").append(mDay).append("/")
                    .append(mYear).append(" "));
            age = currYear - mYear;
            System.out.println(btn_birthdate.getText().toString() + "Age: " + age);
        }
    }

}
