package com.example.kite.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.kite.R
import com.example.kite.databinding.FragmentSettingBinding
import com.example.kite.setting.adapter.SettingsAdapter
import com.google.android.material.tabs.TabLayoutMediator

class SettingFragment : Fragment() {
    private lateinit var binding: FragmentSettingBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.fragment_setting,
            container,
            false
        )
        setupViewPager()
        setupTabLayout()
        navigation()
        return binding.root
    }

    //managing navigation
    private fun navigation() {
        binding.imgBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupTabLayout() {
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Personal Setting"
                }
                1 -> {
                    tab.text = "Verified Info"
                }
                2 -> {
                    tab.text = "Subscriptions"
                }
            }
        }.attach()
    }

    //adapter for viewpager2
    private fun setupViewPager() {
        val adapter = activity?.let { SettingsAdapter(it, 3) }
        binding.viewPager.adapter = adapter

    }


}