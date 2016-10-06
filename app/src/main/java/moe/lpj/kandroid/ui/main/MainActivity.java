package moe.lpj.kandroid.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import moe.lpj.kandroid.R;
import moe.lpj.kandroid.ui.settings.SettingsActivity;
import moe.lpj.kandroid.utils.UtilsA;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TabLayout mTabLayout;

    public TabLayout getTabLayout() {
        return mTabLayout;
    }

    private void setupTabLayout() {
        if (mTabLayout != null) return;
        AppBarLayout appBarLayout = UtilsA.findViewById(this, R.id.app_bar_layout);
        mTabLayout = new TabLayout(appBarLayout.getContext());
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        appBarLayout.addView(mTabLayout);
    }

    private void removeTabLayout() {
        AppBarLayout appBarLayout = UtilsA.findViewById(this, R.id.app_bar_layout);
        appBarLayout.removeView(mTabLayout);
        mTabLayout = null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = UtilsA.findViewById(this, R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = UtilsA.findViewById(this, R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "这只是个装饰~", Snackbar.LENGTH_LONG)
                        .setAction("嗯呢~", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            }
                        }).show();
            }
        });

        DrawerLayout drawer = UtilsA.findViewById(this, R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = UtilsA.findViewById(this, R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState != null) return;
        navigationView.setCheckedItem(R.id.nav_home);

        if (findViewById(R.id.fragment_container) != null) {
            setupTabLayout();
            HomeFragment homeFragment = new HomeFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, homeFragment).commit();
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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_home:
                showHomeFragment();
                break;
            case R.id.nav_girls:
                showGirlsFragment();
                break;
            case R.id.nav_equipments:
                showEquipmentsFragment();
                break;
            case R.id.nav_book:
                showBookFragment();
                break;
            case R.id.nav_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showHomeFragment() {
        if (findViewById(R.id.fragment_container) != null) {
            setupTabLayout();
            HomeFragment homeFragment = new HomeFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, homeFragment).commit();
        }
    }

    private void showGirlsFragment() {
        if (findViewById(R.id.fragment_container) != null) {
            removeTabLayout();
            HomeOverviewFragment homeOverviewFragment = new HomeOverviewFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, homeOverviewFragment).commit();
        }
    }

    private void showEquipmentsFragment() {
    }

    private void showBookFragment() {
    }
}
