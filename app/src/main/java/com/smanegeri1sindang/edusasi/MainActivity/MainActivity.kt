package com.smanegeri1sindang.edusasi.MainActivity

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.infideap.drawerbehavior.AdvanceDrawerLayout
import com.smanegeri1sindang.edusasi.MainActivity.Fragment.FeatureFragment
import com.smanegeri1sindang.edusasi.MainActivity.Fragment.HomeFragment
import com.smanegeri1sindang.edusasi.MainActivity.Fragment.MoreFragment
import com.smanegeri1sindang.edusasi.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    val manager = supportFragmentManager
    private var drawerLayout: AdvanceDrawerLayout? = null
    private var navigationView: NavigationView? = null

    companion object {
        private const val HOME = 1
        private const val FEATURE = 2
        private const val MORE = 3
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navbar.add(MeowBottomNavigation.Model(HOME, R.drawable.ic_home))
        navbar.add(MeowBottomNavigation.Model(FEATURE, R.drawable.ic_app))
        navbar.add(MeowBottomNavigation.Model(MORE, R.drawable.ic_more))

        drawerLayout = this@MainActivity.findViewById(R.id.DrawLayout)
        drawerLayout?.openDrawer(GravityCompat.START)
        drawerLayout?.closeDrawer(GravityCompat.START)


        navigationView = this@MainActivity.findViewById(R.id.nav_views)
        navigationView?.setNavigationItemSelectedListener(this)

        if (fragment_container != null) {
            createFragFeature()
        }

        navbar.setOnClickMenuListener {item ->
            when (item.id) {
                1 -> {
                    createFragHome()
                }
                2 -> {
                    createFragFeature()
                }
                3 -> {
                    createFragMore()
                }
            }
        }
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        //Handle click
        when (menuItem.itemId) {
            R.id.home -> {
                createFragHome()
            }
            R.id.features -> {
                createFragFeature()
            }
            R.id.more -> {
                createFragMore()
            }
        }
        drawerLayout?.closeDrawer(GravityCompat.START)
        return true
    }

    fun createFragHome() {
        val trans = manager.beginTransaction()
        val frag = HomeFragment()
        trans.replace(R.id.fragment_container, frag)
        trans.commit()

        navbar.show(HOME)
        navigationView?.setCheckedItem(R.id.home)
    }

    fun createFragFeature() {
        val trans = manager.beginTransaction()
        val frag = FeatureFragment()
        trans.replace(R.id.fragment_container, frag)
        trans.commit()

        navbar.show(FEATURE)
        navigationView?.setCheckedItem(R.id.features)
    }

    fun createFragMore() {
        val trans = manager.beginTransaction()
        val frag = MoreFragment()
        trans.replace(R.id.fragment_container, frag)
        trans.commit()

        navbar.show(MORE)
        navigationView?.setCheckedItem(R.id.more)
    }

    override fun onBackPressed() {
        if (drawerLayout?.isDrawerOpen(GravityCompat.START)!!) {
            drawerLayout?.closeDrawer(GravityCompat.START)
        } else {
            if (drawerLayout?.isDrawerOpen(GravityCompat.START)!!) {
                super.onBackPressed()
            } else {
                val mBuilder = AlertDialog.Builder(this@MainActivity)
                mBuilder.setTitle("Exit")
                mBuilder.setNeutralButton("Yes") {dialog, which ->
                    finish()
                }
                mBuilder.setNegativeButton("Cancel") {dialog, which ->

                }
                mBuilder.setMessage("Are you want to exit ?")
                mBuilder.setCancelable(false)
                mBuilder.show()

            }
        }

    }
}
