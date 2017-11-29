package capstone.heartbeat;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.SeekBar;
import android.widget.ToggleButton;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by torre on 10/24/2017.
 */

public class Demographics extends AppCompatActivity {

    private DatePicker date;
    private ToggleButton male,female;
    private SeekBar weight,height;
    private Button next,changeDate;

    private int year,month,day;

    private List<String> demographicsValues;

    static final int DATE_DIALOG_ID = 999;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demographics2);
           
        date = (DatePicker)findViewById(R.id.date);
        male = (ToggleButton)findViewById(R.id.male);
        female = (ToggleButton)findViewById(R.id.female);
        weight = (SeekBar)findViewById(R.id.Weight);
        height = (SeekBar)findViewById(R.id.height);
        next = (Button)findViewById(R.id.next);
        changeDate = (Button)findViewById(R.id.changeDate);

        demographicsValues = new ArrayList<String>();


        changeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DATE_DIALOG_ID);
            }
        });

    }

    public void setCurrentDate(){
        changeDate = (Button)findViewById(R.id.changeDate);
        date = (DatePicker)findViewById(R.id.date);

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        changeDate.setText(new StringBuilder().append(day).append("/").append(month).append("/").append(year));

        date.init(year,month,day,null);
    }

    @Override
    protected Dialog onCreateDialog(int id){
        switch (id){
            case DATE_DIALOG_ID:
                return  new DatePickerDialog(this,datePickerListener,year,month,day);
        }
        return  null;
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener(){

        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            year = i;
            month = i1+1;
            day = i2;

            changeDate.setText(new StringBuilder().append(day).append("/").append(month).append("/").append(year));

            date.init(year,month,day,null);
        }
    };
}
