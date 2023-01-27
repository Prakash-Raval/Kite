package com.example.kite.setting.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.kite.profile.ui.ProfileFragment
import com.example.kite.subscription.SubscriptionFragment
import com.example.kite.verifiedinfo.VerifyInfoFragment

class SettingsAdapter(fragmentActivity: FragmentActivity, private var totalCount: Int) :
    FragmentStateAdapter(fragmentActivity) {


    override fun getItemCount(): Int {
        return totalCount
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {

                ProfileFragment()
            }
            1 -> {

                VerifyInfoFragment()
            }
            2 -> {

                SubscriptionFragment()
            }
            else -> {

                ProfileFragment()
            }
        }

    }

}