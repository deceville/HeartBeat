package capstone.heartbeat.assessment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import abak.tr.com.boxedverticalseekbar.BoxedVertical;
import capstone.heartbeat.controllers.PreferenceManager;
import capstone.heartbeat.R;
import capstone.heartbeat.models.ScaleView;

public class LaboratoryActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;
    private Button btnPrev, btnNext;
    private PreferenceManager prefManager;
    private int sbp,dbp,chl,hdl;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private AlertDialog alert;
    private BoxedVertical chol_total, chol_hdl, bp_systolic;
    private boolean complete = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laboratory);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
        btnPrev = (Button) findViewById(R.id.btn_prev);
        btnNext = (Button) findViewById(R.id.btn_next);

        // layouts of all welcome sliders
        // add few more layouts if you want
        layouts = new int[]{
                R.layout.fragment_cholesterol,
                R.layout.fragment_blood_pressure
        };
        // adding bottom dots
        addBottomDots(0);

        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checking for last page
                // if last page home screen will be launched
                int current = getItem(+1);
                if (current < layouts.length) {
                    // move to next screen
                    viewPager.setCurrentItem(current);
                } else {

                }
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
            prefs = getSharedPreferences("values", MODE_PRIVATE);
            System.out.println("CHL: " + prefs.getInt("chl",chl));
            System.out.println("SBP: " + prefs.getInt("sbp",sbp));
            System.out.println("DBP: " + prefs.getInt("dbp",dbp));
            System.out.println("HDL: " + prefs.getInt("hdl",hdl));
            startActivity(new Intent(getApplicationContext(),HabitsActivity.class));
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        if(updateProceedButton()){
            menu.getItem(0).setEnabled(true);
        }else{
            menu.getItem(0).setEnabled(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    public boolean updateProceedButton(){
        if(complete){
            return true;
        }else{
            return false;
        }
    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    //  viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);

            // changing the next to prev
            if (position == layouts.length - 1) {
                // last page. make next_button text to prev
                complete = true;
                invalidateOptionsMenu();
                btnPrev.setText(getString(R.string.prev));
                btnNext.setVisibility(View.GONE);
                btnPrev.setVisibility(View.VISIBLE);

                btnPrev.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(layouts.length - 2);
                    }
                });


            } else {
                // still pages are left
                btnNext.setText(getString(R.string.next));
                btnPrev.setVisibility(View.GONE);
                btnNext.setVisibility(View.VISIBLE);
            }


        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };


    /**
     * View pager adapter
     */
    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);


            chol_total = (BoxedVertical)view.findViewById(R.id.chol_total);
            chol_hdl = (BoxedVertical)view.findViewById(R.id.chol_hdl);
            bp_systolic = (BoxedVertical)view.findViewById(R.id.bp_systolic);


            prefs = getSharedPreferences("values",MODE_PRIVATE);
            editor = prefs.edit();

            if(position == layouts.length - 1){
                bp_systolic.setOnBoxedPointsChangeListener(new BoxedVertical.OnValuesChangeListener() {
                    @Override
                    public void onPointsChanged(BoxedVertical boxedPoints, int value) {
                        sbp = value;
                        editor.putInt("sbp", sbp);
                        editor.commit();
                    }

                    @Override
                    public void onStartTrackingTouch(BoxedVertical boxedPoints) {

                    }

                    @Override
                    public void onStopTrackingTouch(BoxedVertical boxedPoints) {

                    }
                });
            }else{
                chol_total.setOnBoxedPointsChangeListener(new BoxedVertical.OnValuesChangeListener() {
                    @Override
                    public void onPointsChanged(BoxedVertical boxedPoints, int value) {
                        if(value < 130){
                            alertCHOLLimit();
                        }else{
                            chl = value;
                        }
                        editor.putInt("chl", chl);
                        editor.commit();
                    }

                    @Override
                    public void onStartTrackingTouch(BoxedVertical boxedPoints) {
                    }


                    @Override
                    public void onStopTrackingTouch(BoxedVertical boxedPoints) {
                    }
                });

                chol_hdl.setOnBoxedPointsChangeListener(new BoxedVertical.OnValuesChangeListener() {
                    @Override
                    public void onPointsChanged(BoxedVertical boxedPoints, int value) {
                        if(value > chl){
                            alertHDLLimit(chl);
                        }else{
                            hdl = value;
                        }
                        editor.putInt("hdl", hdl);
                        editor.commit();
                    }

                    @Override
                    public void onStartTrackingTouch(BoxedVertical boxedPoints) {
                    }

                    @Override
                    public void onStopTrackingTouch(BoxedVertical boxedPoints) {
                    }
                });
            }

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }

        public boolean alertHDLLimit (final int chol){
            AlertDialog.Builder builder = new AlertDialog.Builder(LaboratoryActivity.this, R.style.Theme_AppCompat_Dialog_Alert);
            final ScaleView rulerViewM = (ScaleView) findViewById(R.id.my_scale);
            if (alert != null && alert.isShowing()) {
                // A dialog is already open, wait for it to be dismissed, do nothing
            } else {
                builder.setTitle("Oops!")
                        .setMessage("HDL value should not be more than Total Cholesterol value.")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                chol_hdl.setValue(chol/2);

                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert);
            }

            alert = builder.create();
            alert.show();
            return true;
        }

        public boolean alertCHOLLimit (){
            AlertDialog.Builder builder = new AlertDialog.Builder(LaboratoryActivity.this, R.style.Theme_AppCompat_Dialog_Alert);
            final ScaleView rulerViewM = (ScaleView) findViewById(R.id.my_scale);
            if (alert != null && alert.isShowing()) {
                // A dialog is already open, wait for it to be dismissed, do nothing
            } else {
                builder.setTitle("Oops!")
                        .setMessage("Total Cholesterol value should not be less than 130.")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                chol_total.setValue(140);

                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert);
            }

            alert = builder.create();
            alert.show();
            return true;
        }
    }
}
