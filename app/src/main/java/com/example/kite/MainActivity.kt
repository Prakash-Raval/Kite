package com.example.kite

import android.os.Bundle
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
import com.example.kite.base.network.ApiClient
import com.example.kite.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var pNavController: NavController
    private lateinit var pDrawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setUpNavigation()
        setNavigation()
        ApiClient.initRetrofit()
    }

    private fun setUpNavigation() {
        pDrawerLayout = binding.drawerLayout
        val pNavigationView = binding.navView
        pNavController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration =
            AppBarConfiguration(setOf(), pDrawerLayout)
        pNavigationView.setupWithNavController(pNavController)
        pNavController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.splashFragment -> {
                    window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
                    hideDrawer()
                }
                R.id.homeFragment -> {
                    window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
                    showDrawer()
                }
                else -> {
                    hideDrawer()
                    window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
                }

            }
        }
    }

    //close drawer(unlock)
    private fun hideDrawer() {
        pDrawerLayout.closeDrawer(GravityCompat.START)
        pDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }

    //open drawer (lock)
    private fun showDrawer() {
        pDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        if (pDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            pDrawerLayout.openDrawer(GravityCompat.START)

        } else {
            pDrawerLayout.closeDrawer(GravityCompat.START)
            statusBar()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return pNavController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    //changing color of status bar
    private fun statusBar() {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.white_status_bar))

    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (pDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            pDrawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    //managing navigation
    private fun setNavigation() {
        binding.menuContainer.txtCustomerSupport.setOnClickListener {
            pNavController.navigate(R.id.supportFragment)
        }
        binding.menuContainer.txtCustomerReservation.setOnClickListener {
            pNavController.navigate(R.id.reservationFragment)
        }
        binding.menuContainer.txtCustomerWallet.setOnClickListener {
            pNavController.navigate(R.id.selectPaymentFragment)
        }
        binding.menuContainer.txtCustomerSettings.setOnClickListener {
            pNavController.navigate(R.id.settingFragment)
        }
        binding.menuContainer.txtCustomerRideHistory.setOnClickListener {
            pNavController.navigate(R.id.rideHistoryFragment)
        }
        binding.menuContainer.txtCustomerPropertySelection.setOnClickListener {
            pNavController.navigate(R.id.selectProgramFragment)
        }
        binding.menuContainer.txtUpdateNotification.setOnClickListener {
            pNavController.navigate(R.id.notificationFragment)
        }
    }
}