package com.example.kite

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.OnBackPressedCallback
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
import com.example.kite.extensions.DialogExtensions
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class MainActivity : AppCompatActivity() {

    /*
    * variables
    * */
    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var pNavController: NavController
    private lateinit var pDrawerLayout: DrawerLayout
    var isFirst = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setUpNavigation()
        setNavigationDirection()
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

    }

    /*
    * set up navigation graph and nav controller
    * */
    private fun setUpNavigation() {
        pDrawerLayout = binding.drawerLayout
        val pNavigationView = binding.navView
        pNavController = findNavController(R.id.nav_host_fragment)
        /*
        * setOf() contains top level destination
        * */
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
                    if (isFirst) {
                        isFirst = false
                        openDialog()
                    }
                }
                else -> {
                    hideDrawer()
                    window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
                }

            }
        }
    }

    /*
    *  dialog shown when app open first time
    * */
    private fun openDialog() {
        DialogExtensions.showDialog(
            this, R.layout.dialog_show_home, R.id.imgClose, R.id.btnOk
        ).show()

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

    /*
    * handle the back navigation
    * if appBarConfiguration has id
    * else
    * direct back navigate
    * */
    override fun onSupportNavigateUp(): Boolean {
        return pNavController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    //changing color of status bar
    private fun statusBar() {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.white_status_bar))

    }


    /*
    * handling onBackPress() method with drawer
    * */
    private val onBackPressedCallback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (pDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                pDrawerLayout.closeDrawer(GravityCompat.START)
            } else {
                when(pNavController.currentDestination?.id){
                    R.id.homeFragment -> {
                        showAppClosingDialog()
                    }
                    R.id.selectProgramFragment -> {
                        showAppClosingDialog()
                    }
                    else -> {
                        pNavController.navigateUp()
                    }
                }
            }
        }
    }
    /*
    * close app dialog
    * */
    private fun showAppClosingDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.app_name)
            .setMessage("Do you really want to close the app?")
            .setPositiveButton("Yes") { _, _ -> finish() }
            .setNegativeButton("No", null)
            .show()
    }


    //managing navigation
    private fun setNavigationDirection() {
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