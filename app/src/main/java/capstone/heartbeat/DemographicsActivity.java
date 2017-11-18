package capstone.heartbeat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.VerticalSeekBar;
import android.widget.VerticalSeekBar_Reverse;

public class DemographicsActivity extends AppCompatActivity {
    VerticalSeekBar verticalSeekBar=null;
    VerticalSeekBar_Reverse verticalSeekBar_Reverse=null;
    TextView vsProgress,vs_reverseProgress=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demographics1);

        verticalSeekBar = (VerticalSeekBar)findViewById(R.id.vertical_Seekbar);
        verticalSeekBar_Reverse = (VerticalSeekBar_Reverse)findViewById(R.id.seekbar_reverse);
        vsProgress = (TextView)findViewById(R.id.vertical_sb_progresstext);
        vs_reverseProgress = (TextView)findViewById(R.id.reverse_sb_progresstext);


        verticalSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                vsProgress.setText(progress+"");

            }
        });

        verticalSeekBar_Reverse.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                vs_reverseProgress.setText(progress+"");

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.button, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.next) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
