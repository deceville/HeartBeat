package capstone.heartbeat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.michaelmuenzer.android.scrollablennumberpicker.ScrollableNumberPicker;
import com.michaelmuenzer.android.scrollablennumberpicker.ScrollableNumberPickerListener;

public class HabitsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habits);


        final ScrollableNumberPicker verticalNumberPicker = (ScrollableNumberPicker) findViewById(R.id.np_freetime);
        verticalNumberPicker.setListener(new ScrollableNumberPickerListener() {
            @Override
            public void onNumberPicked(int value) {
                if(value == verticalNumberPicker.getMaxValue()) {
                    Toast.makeText(HabitsActivity.this, "max", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
