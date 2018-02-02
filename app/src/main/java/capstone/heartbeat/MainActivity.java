package capstone.heartbeat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import capstone.heartbeat.account.LoginActivity;
import capstone.heartbeat.account.ProfileActivity;
import capstone.heartbeat.controllers.HeartBeatDB;
import capstone.heartbeat.fragments.GoalsFragment;
import capstone.heartbeat.fragments.PlansFragment;
import capstone.heartbeat.fragments.ResultsFragment;
import capstone.heartbeat.fragments.SuggestionsFragment;
import capstone.heartbeat.models.User;
import capstone.heartbeat.others.AboutActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private SharedPreferences prefs;
    public SharedPreferences user;
    RelativeLayout coinCount, heartCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        user = getSharedPreferences("login",MODE_PRIVATE);

        int id = user.getInt("id", 1);
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
        adapter.addFragment(new ResultsFragment(prefs), "Results");
        adapter.addFragment(new SuggestionsFragment(), "Suggestions");
        adapter.addFragment(new PlansFragment(), "Plans");
        adapter.addFragment(new GoalsFragment(user), "Goals & Quests");
        viewPager.setAdapter(adapter);
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
        MenuItem item2 = menu.findItem(R.id.action_hearts);
        MenuItemCompat.setActionView(item1, R.layout.badge_layout);
        coinCount = (RelativeLayout) MenuItemCompat.getActionView(item1);
        heartCount = (RelativeLayout) MenuItemCompat.getActionView(item2);
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


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

        } else if (id == R.id.nav_plans) {
            /*FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_plans, new PlansFragment());
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack(null);
            ft.commit();*/

        } else if (id == R.id.nav_suggestions) {

        } else if (id == R.id.nav_goals) {

        } else if (id == R.id.nav_help) {

        } else if (id == R.id.nav_about) {
            Intent i = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_logout) {
            SharedPreferences values = getSharedPreferences("values",MODE_PRIVATE);
            SharedPreferences.Editor ed = values.edit();
            ed.clear();
            ed.commit();
            prefs = getSharedPreferences("login",MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.clear();
            editor.putInt("session",0);
            editor.commit();
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
            finish();


        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

}
