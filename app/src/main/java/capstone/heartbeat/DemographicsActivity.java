package capstone.heartbeat;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.tistory.dwfox.dwrulerviewlibrary.view.DWRulerSeekbar;

import java.util.Calendar;

public class DemographicsActivity extends AppCompatActivity {
    public Button btnBirthdate;

    private DWRulerSeekbar dwRulerSeekbar;

    private static final float MIN_VALUE = 5;
    private static final float MAX_VALUE = 33;
    private static final float LINE_RULER_MULTIPLE_SIZE = 3.5f;
    private TextView rulerText;
    private TextView seekbarText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demographics1);

        btnBirthdate = (Button)findViewById(R.id.btnBirthdate);

        btnBirthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(DemographicsActivity.this,
                        new mDateSetListener(), mYear, mMonth, mDay);
                dialog.show();
            }
        });
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
            btnBirthdate.setText(new StringBuilder()
                    // Month is 0 based so add 1
                    .append(mMonth + 1).append("/").append(mDay).append("/")
                    .append(mYear).append(" "));
            System.out.println(btnBirthdate.getText().toString());


        }
    }

    private void initDWSeekbar() {
        dwRulerSeekbar = (DWRulerSeekbar) findViewById(R.id.dwRulerSeekbar);
        dwRulerSeekbar
                .setMinMaxValue((int) MIN_VALUE, (int) MAX_VALUE)
                .setDWRulerSeekbarListener(new DWRulerSeekbar.OnDWSeekBarListener() {
                    @Override
                    public void onStopSeekbarValue(int value) {
                        seekbarText.setText("DWSeekBar Value : " + value);
                    }
                });
    }
}
