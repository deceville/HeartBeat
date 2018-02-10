package capstone.heartbeat.assessment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NavUtils;
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
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import abak.tr.com.boxedverticalseekbar.BoxedVertical;
import capstone.heartbeat.controllers.PreferenceManager;
import capstone.heartbeat.R;

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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up next_button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch(id){
            case R.id.next:
                prefs = getSharedPreferences("values", MODE_PRIVATE);
                startActivity(new Intent(getApplicationContext(),HabitsActivity.class));
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        /*if (id == R.id.next) {
            prefs = getSharedPreferences("values", MODE_PRIVATE);
            startActivity(new Intent(getApplicationContext(),HabitsActivity.class));
            finish();
            return true;
        }*/

        return super.onOptionsItemSelected(item);
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


            BoxedVertical chol_total = (BoxedVertical)view.findViewById(R.id.chol_total);
            BoxedVertical chol_hdl = (BoxedVertical)view.findViewById(R.id.chol_hdl);
            BoxedVertical bp_systolic = (BoxedVertical)view.findViewById(R.id.bp_systolic);
            BoxedVertical bp_diastolic = (BoxedVertical)view.findViewById(R.id.bp_diastolic);


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
                bp_diastolic.setOnBoxedPointsChangeListener(new BoxedVertical.OnValuesChangeListener() {
                    @Override
                    public void onPointsChanged(BoxedVertical boxedPoints, int value) {
                        dbp = value;
                        editor.putInt("dbp", dbp);
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
                        chl = value;
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
                        hdl = value;
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
    }
}
