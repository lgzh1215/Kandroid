package moe.lpj.kandroid.activity.main

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import kandroid.KandroidMain
import moe.lpj.kandroid.R
import moe.lpj.kandroid.activity.setting.SettingsActivity
import moe.lpj.kandroid.kandroid.ConfigA
import moe.lpj.kandroid.service.ProxyService
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    val log: Logger = LoggerFactory.getLogger(javaClass)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        log.debug("onCreate")

        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val drawer: DrawerLayout = findViewById(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        val navigationView: NavigationView = findViewById(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)

        if (savedInstanceState != null) return
        navigationView.setCheckedItem(R.id.nav_home)
    }

    override fun onPause() {
        super.onPause()
        log.info("onPause")
    }

    override fun onStop() {
        super.onStop()
        log.info("onStop")
    }

    override fun onResume() {
        super.onResume()
        log.info("onResume")
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
        val id = item.itemId
        if (id == R.id.action_proxy) {
            val intent: Intent = Intent(this, ProxyService::class.java)
            if (ProxyService.isRunning) {
                stopService(intent)
                KandroidMain.stop()
                item.title = "已停止"
            } else {
                startService(intent)
                KandroidMain.updateConfig(ConfigA.get(this))
                KandroidMain.start()
                item.title = "正在运行"
            }
            return true
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
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }
}
