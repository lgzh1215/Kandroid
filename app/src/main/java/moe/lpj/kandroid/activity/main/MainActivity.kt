package moe.lpj.kandroid.activity.main

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kandroid.KandroidMain
import kandroid.thread.Threads
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import moe.lpj.kandroid.R
import moe.lpj.kandroid.activity.setting.SettingsActivity
import moe.lpj.kandroid.databinding.ActivityMainBinding
import moe.lpj.kandroid.kandroid.ConfigA
import moe.lpj.kandroid.service.DetectionService
import moe.lpj.kandroid.service.ProxyService
import moe.lpj.kandroid.viewmodel.viewUpdater
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import android.databinding.DataBindingUtil.getBinding



class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    val log: Logger = LoggerFactory.getLogger(MainActivity::class.java)

    var toggle: ActionBarDrawerToggle? = null

    var menuItem: MenuItem? = null

    lateinit var binding: ActivityMainBinding private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        log.debug("onCreate")

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle!!)

        nav_view.setNavigationItemSelectedListener(this)

        if (savedInstanceState != null)
            return

        if (fragment_container != null) {
            supportFragmentManager.beginTransaction()
                    .add(R.id.fragment_container, HomeFragment())
                    .commit()
            nav_view.setCheckedItem(R.id.nav_home)
        }
        log.info("dsafas fwsfwsefsfawe")

        Threads.runTickTack(viewUpdater)
    }

//    private fun initActionBar() {
//        setSupportActionBar(getBinding().toolbar)
//
//        val drawer = getBinding().drawLayout
//        val toggle = ActionBarDrawerToggle(this, drawer, getBinding().toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
//        drawer.setDrawerListener(toggle)
//        toggle.syncState()
//
//        getBinding().navigationView.setNavigationItemSelectedListener(this)
//    }

    override fun onStart() {
        super.onStart()
        log.info("onStart")
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toggle!!.syncState()
    }

    override fun onResume() {
        super.onResume()
        updateMenu()
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
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            moveTaskToBack(true)
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        val menuItem = menu.findItem(R.id.action_start)
        this.menuItem = menuItem
        updateMenu()
        return true
    }

    private fun updateMenu() {
        menuItem?.title = if (ProxyService.isRunning) {
            "正在运行"
        } else {
            "已停止"
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_start) {
            if (DetectionService.isAccessibilitySettingsOn(context = this)) {
                val intent: Intent = Intent(this, ProxyService::class.java)
                if (ProxyService.isRunning) {
                    stopService(intent)
                    KandroidMain.stop()
                    menuItem?.title = "已停止"
                } else {
                    startService(intent)
                    KandroidMain.updateConfig(ConfigA.get(this))
                    KandroidMain.start()
                    menuItem?.title = "正在运行"
                }
                return true
            } else {
                DetectionService.startAccessibilitySettings(context = this)
            }
        }

        return super.onOptionsItemSelected(item)
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
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
