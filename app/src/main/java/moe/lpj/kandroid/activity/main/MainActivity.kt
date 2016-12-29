package moe.lpj.kandroid.activity.main

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import moe.lpj.kandroid.R
import moe.lpj.kandroid.activity.setting.SettingsActivity
import moe.lpj.kandroid.databinding.ActivityMainDrawerBinding
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    val log: Logger = LoggerFactory.getLogger(MainActivity::class.java)

    lateinit var mDrawerToggle: ActionBarDrawerToggle private set

    lateinit var binding: ActivityMainDrawerBinding private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        log.info("onCreate")

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_drawer)

        initActionBar()

        if (savedInstanceState != null)
            return

        if (binding.activityMainContent.fragmentContainer != null) {
            supportFragmentManager.beginTransaction()
                    .add(R.id.fragment_container, HomeFragment())
                    .commit()
            binding.navigationView.setCheckedItem(R.id.nav_home)
        }
    }

    private fun initActionBar() {
        setSupportActionBar(binding.activityMainContent.toolbar)

        val toggle = ActionBarDrawerToggle(this,
                binding.drawerLayout, binding.activityMainContent.toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        binding.drawerLayout.addDrawerListener(toggle)
        mDrawerToggle = toggle

        binding.navigationView.setNavigationItemSelectedListener(this)
    }

    override fun onStart() {
        super.onStart()
        log.info("onStart")
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        mDrawerToggle.syncState()
    }

    override fun onResume() {
        super.onResume()
        log.info("onResume")
    }

    override fun onPause() {
        super.onPause()
        log.info("onPause")
    }

    override fun onStop() {
        super.onStop()
        log.info("onStop")
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            moveTaskToBack(true)
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            R.id.nav_home -> {
            }
            R.id.nav_girls -> {
            }
            R.id.nav_equipments -> {
            }
            R.id.nav_book -> {
            }
            R.id.nav_settings -> {
                val intent: Intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
            }
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    fun showTabLayout() {
        binding.activityMainContent.tabLayout.visibility = View.VISIBLE
    }

    fun hideTabLayout() {
        binding.activityMainContent.tabLayout.visibility = View.GONE
    }
}
