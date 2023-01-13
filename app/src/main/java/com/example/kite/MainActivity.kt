package com.example.kite

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.example.kite.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var pNavController: NavController
    private lateinit var pDrawerLayout: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      /*  window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.transparent)
        window.navigationBarColor = ContextCompat.getColor(this, R.color.transparent)*/
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setUpNavigation()
    }

    private fun setUpNavigation() {
        pDrawerLayout = binding.drawerLayout
        val pNavigationView = binding.navView
        pNavController = findNavController(R.id.nav_host_fragment)
//        appBarConfiguration =
//            AppBarConfiguration(setOf(R.id.nav_home, R.id.nav_forecast), pDrawerLayout)
        pNavigationView.setupWithNavController(pNavController)
//        pNavController.addOnDestinationChangedListener { _, destination, _ ->
//            when (destination.id) {
//                R.id.nav_home -> {
//                    showDrawer()
//
//                }
//                R.id.nav_forecast -> showDrawer()
//                else -> hideDrawer()
//            }
//        }
    }

    private fun hideDrawer() {
        pDrawerLayout.closeDrawer(GravityCompat.START)
        pDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }

    private fun showDrawer() {
        pDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        if (pDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            pDrawerLayout.openDrawer(GravityCompat.START)

        } else {
            pDrawerLayout.closeDrawer(GravityCompat.START)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return pNavController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}