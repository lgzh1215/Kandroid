package moe.lpj.kandroid.ui.main

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.*
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import moe.lpj.kandroid.R
import moe.lpj.kandroid.ui.settings.SettingsActivity

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    var tabLayout: TabLayout? = null
        internal set

    private fun setupTabLayout() {
        if (tabLayout != null) return
        val appBarLayout: AppBarLayout = findViewById(R.id.app_bar_layout) as AppBarLayout
        tabLayout = TabLayout(appBarLayout.context)
        tabLayout!!.tabMode = TabLayout.MODE_SCROLLABLE
        tabLayout!!.tabGravity = TabLayout.GRAVITY_CENTER
        appBarLayout.addView(tabLayout)
    }

    private fun removeTabLayout() {
        val appBarLayout: AppBarLayout = findViewById(R.id.app_bar_layout) as AppBarLayout
        appBarLayout.removeView(tabLayout)
        tabLayout = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view -> Snackbar.make(view, "这只是个装饰~", Snackbar.LENGTH_LONG).setAction("嗯呢~") { }.show() }

        val drawer: DrawerLayout = findViewById(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        val navigationView: NavigationView = findViewById(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)

        if (savedInstanceState != null) return
        navigationView.setCheckedItem(R.id.nav_home)

        if (findViewById(R.id.fragment_container) != null) {
            setupTabLayout()
            val homeFragment = HomeFragment()
            supportFragmentManager.beginTransaction().add(R.id.fragment_container, homeFragment).commit()
        }
    }

    override fun onBackPressed() {
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            R.id.nav_home -> showHomeFragment()
            R.id.nav_girls -> showGirlsFragment()
            R.id.nav_equipments -> showEquipmentsFragment()
            R.id.nav_book -> showBookFragment()
            R.id.nav_settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
            }
        }
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    private fun showHomeFragment() {
        if (findViewById(R.id.fragment_container) != null) {
            setupTabLayout()
            val homeFragment = HomeFragment()
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, homeFragment).commit()
        }
    }

    private fun showGirlsFragment() {
        if (findViewById(R.id.fragment_container) != null) {
            removeTabLayout()
            val homeOverviewFragment = HomeOverviewFragment()
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, homeOverviewFragment).commit()
        }
    }

    private fun showEquipmentsFragment() {
    }

    private fun showBookFragment() {
    }
}
