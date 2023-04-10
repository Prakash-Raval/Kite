package com.example.kite.setting

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.kite.R
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.ResponseData
import com.example.kite.basefragment.BaseFragment
import com.example.kite.databinding.FragmentSettingBinding
import com.example.kite.login.model.LoginResponse
import com.example.kite.profile.model.UpdateProfileResponse
import com.example.kite.profile.model.ViewProfileResponse
import com.example.kite.profile.viewmodel.ViewProfileViewModel
import com.example.kite.setting.adapter.SettingsAdapter
import com.example.kite.utils.PrefManager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator


class SettingFragment : BaseFragment() {

    /*
    * variables
    * */
    private lateinit var binding: FragmentSettingBinding
    private lateinit var viewModelProfile: ViewProfileViewModel


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


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
        getApiData()
        setupTabLayout()
        setUpToolBar()
        setUpSnackBar(viewModelProfile)
    }

    /*
    * setting up toolbar
    * save button visibility
    * */
    private fun setUpToolBar() {
        binding.isVisible = binding.viewPager.currentItem == 0

        binding.tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                binding.isVisible = tab.position == 0
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
        binding.imgBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }


    /*
    * setting value to the tab by position
    * */
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

    /*
    * setting up the adapter for view pager
    * */
    private fun setupViewPager() {
        val adapter = activity?.let { SettingsAdapter(it, 3) }
        binding.viewPager.adapter = adapter
    }

    /*
    * getting view model
    * */
    private fun getViewModel(): ViewProfileViewModel {
        viewModelProfile = ViewModelProvider(this)[ViewProfileViewModel::class.java]
        return viewModelProfile
    }


    private fun getApiData() {

        viewModelProfile = getViewModel()
        binding.updateProfileViewModel = viewModelProfile
        val token = PrefManager.get<LoginResponse>("LOGIN_RESPONSE")
        token?.accessToken?.let { viewModelProfile.getToken(it) }
        setObserver()
    }

    /*
    setting up observer for update profile
    */
    private fun setObserver() {

        viewModelProfile.responseDataUpdateProfile.observe(viewLifecycleOwner, Observer { state ->
            if (state == null) {
                return@Observer
            }
            when (state) {
                is ResponseHandler.Loading -> {
                    showProgressDialog()
                    Log.d("SettingFragment", "setObserverData: $state")
                }
                is ResponseHandler.OnFailed -> {
                    hideProgressBar()
                    Log.d("SettingFragment", "setObserverData: $state")

                }
                is ResponseHandler.OnSuccessResponse<ResponseData<UpdateProfileResponse>?> -> {
                    hideProgressBar()
                    Log.d("SettingFragment", "setObserverData: ${state.response?.data}")
                    if (state.response?.code == 200) {
                        Toast.makeText(
                            requireContext(),
                            "Data Updated Successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        })
    }

}