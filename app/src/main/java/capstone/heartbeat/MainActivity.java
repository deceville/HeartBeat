package capstone.heartbeat;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;

import java.util.ArrayList;
import java.util.List;

import capstone.heartbeat.account.LoginActivity;
import capstone.heartbeat.account.ProfileActivity;
import capstone.heartbeat.controllers.HeartBeatDB;
import capstone.heartbeat.fragments.GoalsFragment;
import capstone.heartbeat.fragments.PlansFragment;
import capstone.heartbeat.fragments.ResultsFragment;
import capstone.heartbeat.fragments.SuggestionsFragment;
import capstone.heartbeat.fragments.tabfragments.BMIFragment;
import capstone.heartbeat.fragments.tabfragments.BloodPressureFragment;
import capstone.heartbeat.fragments.tabfragments.CholesterolFragment;
import capstone.heartbeat.models.Bank;
import capstone.heartbeat.others.AboutActivity;
import capstone.heartbeat.sidebar.FAQ_Activity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, BMIFragment.OnFragmentInteractionListener,
        CholesterolFragment.OnFragmentInteractionListener, BloodPressureFragment.OnFragmentInteractionListener {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private SharedPreferences prefs;
    public SharedPreferences user;
    RelativeLayout coinCount, heartCount;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        user = getSharedPreferences("login",MODE_PRIVATE);

        id = user.getInt("id", 1);
        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        coinCount = (RelativeLayout) findViewById(R.id.badge_coin);
        heartCount = (RelativeLayout) findViewById(R.id.badge_heart);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /*View headerView = findViewById(R.id.header_menu);
        TextView header_name = (TextView) findViewById(R.id.header_name);
        TextView header_email = (TextView) findViewById(R.id.header_email);

        HeartBeatDB heartBeatDB = new HeartBeatDB(getApplicationContext());
        heartBeatDB.open();
        User markeh = heartBeatDB.getUserAssessData(id);
        header_name.setText(markeh.getName());
        header_email.setText(markeh.getEmail());
        heartBeatDB.close();
*/
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


    }

    private void setupViewPager(ViewPager viewPager) {
        prefs = getSharedPreferences("values",MODE_PRIVATE);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ResultsFragment(prefs,id), "Results");
        adapter.addFragment(new SuggestionsFragment(prefs), "Suggestions");
        adapter.addFragment(new PlansFragment(user), "Plans");
        adapter.addFragment(new GoalsFragment(user,id), "Goals & Quests");
        viewPager.setAdapter(adapter);
    }

    public void displayTutorial(Activity act, View view){
        TapTargetView.showFor(act,
                TapTarget.forView(view, "Add selected suggestions", "Select and add suggestions to a new plan")
                        .outerCircleColor(R.color.bg_screen2)      // Specify a color for the outer circle
                        .outerCircleAlpha(0.96f)
                        .titleTextSize(20)
                        .descriptionTextSize(16)
                        .textColor(R.color.standardWhite) // Specify a color for both the title and description text
                        .textTypeface(Typeface.SANS_SERIF)  // Specify a typeface for the text
                        .dimColor(R.color.standardBlack)            // If set, will dim behind the view with 30% opacity of the given color
                        .drawShadow(true)                   // Whether to draw a drop shadow or not
                        .cancelable(false)                  // Whether tapping outside the outer circle dismisses the view
                        .tintTarget(false)                   // Whether to tint the target view's color
                        .transparentTarget(false)            // Specify a custom drawable to draw as the target
                        .targetRadius(60)
        );
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem item1 = menu.findItem(R.id.action_coins);
        MenuItemCompat.setActionView(item1, R.layout.badge_coin);
        coinCount = (RelativeLayout) MenuItemCompat.getActionView(item1);
        TextView coin = (TextView)coinCount.findViewById(R.id.badge_coin_text);
        HeartBeatDB db = new HeartBeatDB(getApplicationContext());
        db.open();
        Bank b = new Bank();
        b= db.getPoints(id);

        int coins = b.getCoins();
        System.out.println(coins);
        coin.setText(coins+"");

        item1.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                MainActivity.this.displayShop();
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    public void displayShop(){
        final Dialog dialog = new Dialog(MainActivity.this, android.R.style.Theme_Material_Light_Dialog);
        dialog.setTitle("Buy Time");
        dialog.setContentView(R.layout.shop_dialog);
        dialog.create();

        dialog.show();

        Button shop_done = (Button) dialog.findViewById(R.id.shop_done);
        Button shop_cancel = (Button) dialog.findViewById(R.id.shop_cancel);

        shop_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        shop_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;

        if (id == R.id.nav_home) {

        } else if (id == R.id.nav_profile) {
            Intent i = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_results) {
            /*FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_results, new ResultsFragment());
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack(null);
            ft.commit();*/

            fragment = new ResultsFragment();
        } else if (id == R.id.nav_plans) {
            /*FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_plans, new PlansFragment());
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack(null);
            ft.commit();*/

            PlansFragment plansFragment = new PlansFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, plansFragment)
                    .addToBackStack(null)
                    .commit();
        } else if (id == R.id.nav_suggestions) {
            fragment = new SuggestionsFragment(prefs);
        } else if (id == R.id.nav_goals) {
            fragment = new GoalsFragment();
        } else if (id == R.id.nav_help) {
            Intent i = new Intent(MainActivity.this, FAQ_Activity.class);
            startActivity(i);
        } else if (id == R.id.nav_about) {
            Intent i = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_logout) {
            SharedPreferences values = getSharedPreferences("values", MODE_PRIVATE);
            SharedPreferences.Editor ed = values.edit();
            ed.clear();
            ed.commit();
            prefs = getSharedPreferences("login", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.clear();
            editor.putInt("session", 0);
            editor.commit();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();


        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

}
