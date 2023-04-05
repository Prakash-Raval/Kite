package com.example.kite.profile.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context.MODE_PRIVATE
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.kite.R
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.ResponseData
import com.example.kite.base.network.model.ResponseListData
import com.example.kite.basefragment.BaseFragment
import com.example.kite.countrylisting.CountryListingAdapter
import com.example.kite.countrylisting.CountryResponse
import com.example.kite.countrylisting.OnCellClickedRegion
import com.example.kite.countrylisting.RegionViewModel
import com.example.kite.countrylisting.statelisting.StateListingAdapter
import com.example.kite.countrylisting.statelisting.StateRequest
import com.example.kite.countrylisting.statelisting.StateResponse
import com.example.kite.databinding.FragmentProfileBinding
import com.example.kite.login.model.LoginResponse
import com.example.kite.profile.model.ViewProfileRequest
import com.example.kite.profile.model.ViewProfileResponse
import com.example.kite.profile.viewmodel.ViewProfileViewModel
import com.example.kite.setting.SettingFragmentDirections
import com.example.kite.utils.PrefManager


class ProfileFragment : BaseFragment(), OnCellClickedRegion {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModelProfile: ViewProfileViewModel
    private lateinit var regionViewModel: RegionViewModel
    private lateinit var countryListingAdapter: CountryListingAdapter
    private lateinit var stateListingAdapter: StateListingAdapter
    private lateinit var builder: AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.fragment_profile, container, false
        )
        getCustomerProfile()
        changeData()
        getTermsPage()
        setUpDialogs()
        return binding.root
    }

    //getting data from api
    private fun getCustomerProfile() {
        viewModelProfile = getViewModelProfile()

        /*
        * getting data for profile view model request
        * */
        val token = PrefManager.get<LoginResponse>("LOGIN_RESPONSE")?.accessToken
        val thirdPartyID = PrefManager.get<String>("ThirdPartyID")

        viewModelProfile.getViewProfileRequest(
            ViewProfileRequest(
                access_token = token,
                third_party_id = thirdPartyID
            )
        )

        viewModelProfile.responseDataProfile.observe(viewLifecycleOwner, Observer { state ->
            if (state == null) {
                return@Observer
            }
            when (state) {
                is ResponseHandler.Loading -> {
                    showProgressDialog()
                    Log.d("ViewTripFragment", "setObserverData: $state")
                }
                is ResponseHandler.OnFailed -> {
                    hideProgressBar()
                    Log.d("ViewTripFragment", "setObserverData: $state")

                }
                is ResponseHandler.OnSuccessResponse<ResponseData<ViewProfileResponse>?> -> {
                    hideProgressBar()
                    Log.d("ViewTripFragment", "setObserverData: ${state.response?.data}")
                    if (state.response?.code == 200) {
                        binding.viewProfile = state.response.data
                        val sh =
                            activity?.getSharedPreferences("MySharedPref", MODE_PRIVATE)?.edit()
                        state.response.data?.subscription?.isSubscribe?.let {
                            sh?.putInt(
                                "name",
                                it
                            )?.apply()
                        }
                    }
                }
            }
        })
    }

    //navigation to policy abd terms pages
    private fun getTermsPage() {
        binding.txtPolicy.setOnClickListener {
            findNavController().navigate(R.id.policyFragment)
        }
        binding.txtTerms.setOnClickListener {
            findNavController().navigate(R.id.termsFragment)
        }
        binding.btnSignOut.setOnClickListener {
            logout()
        }
    }

    //opening change password and contact page
    private fun changeData() {
        binding.edtMobile.setOnClickListener {
            findNavController().navigate(R.id.changeContactFragment)
        }
        binding.edtPassword.setOnClickListener {
            findNavController().navigate(R.id.changePasswordFragment)

        }
    }

    //opening dialogs for country and state listing
    private fun setUpDialogs() {
        binding.edtCountry.setOnClickListener {
            openDialogCountry(binding.edtCountry.text.toString())
        }
        binding.edtState.setOnClickListener {
            openDialogState(binding.edtState.text.toString())
        }

    }

    //opening dialog for state list
    private fun openDialogState(mState: String) {
        builder = AlertDialog.Builder(requireContext())
            .create()
        builder.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val view = layoutInflater.inflate(R.layout.dialog_state_listing, null)
        builder.setView(view)
        stateListingAdapter = StateListingAdapter(mState, this)
        val recycler = view.findViewById<RecyclerView>(R.id.rvStateList)
        recycler.adapter = stateListingAdapter
        builder.setCanceledOnTouchOutside(false)
        builder.show()
        getStateData()
    }

    //opening dialog for country list
    private fun openDialogCountry(mCountry: String) {
        builder = AlertDialog.Builder(requireContext())
            .create()
        builder.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val view = layoutInflater.inflate(R.layout.dialog_country_picker, null)
        builder.setView(view)
        countryListingAdapter = CountryListingAdapter(this, mCountry)

        val recycler = view.findViewById<RecyclerView>(R.id.rvCountryList)
        recycler.adapter = countryListingAdapter

        builder.setCanceledOnTouchOutside(false)
        builder.show()
        getCountryData()
    }

    private fun getViewModel(): RegionViewModel {
        regionViewModel = ViewModelProvider(this)[RegionViewModel::class.java]
        return regionViewModel
    }

    private fun getViewModelProfile(): ViewProfileViewModel {
        viewModelProfile = ViewModelProvider(this)[ViewProfileViewModel::class.java]
        return viewModelProfile
    }

    // getting country data from the api
    @SuppressLint("NotifyDataSetChanged")
    private fun getCountryData() {
        regionViewModel = getViewModel()
        regionViewModel.getCountryRequest()
        regionViewModel.responseLiveDataCountry.observe(viewLifecycleOwner, Observer { state ->
            if (state == null) {
                return@Observer
            }
            when (state) {
                is ResponseHandler.Loading -> {
                    Log.d("ProfileFragment", "setObserverData: $state")
                }
                is ResponseHandler.OnFailed -> {
                    Log.d("ProfileFragment", "setObserverData: $state")

                }
                is ResponseHandler.OnSuccessResponse<ResponseListData<CountryResponse>?> -> {
                    Log.d(
                        "ProfileFragment",
                        "setObserverData: ${state.response?.data}"
                    )
                    if (state.response?.code == 200) {
                        countryListingAdapter.setList(state.response.data as ArrayList<CountryResponse>)
                        countryListingAdapter.notifyDataSetChanged()
                    }
                }
            }
        })
    }

    // getting country data from the api
    @SuppressLint("NotifyDataSetChanged")
    private fun getStateData() {
        regionViewModel = getViewModel()

        regionViewModel.getStateRequest(StateRequest(binding.edtCountry.text.toString()))
        regionViewModel.responseLiveDataState.observe(viewLifecycleOwner, Observer { state ->
            if (state == null) {
                return@Observer
            }
            when (state) {
                is ResponseHandler.Loading -> {
                    Log.d("ProfileFragment", "setObserverData: $state")
                }
                is ResponseHandler.OnFailed -> {
                    Log.d("ProfileFragment", "setObserverData: $state")

                }
                is ResponseHandler.OnSuccessResponse<ResponseListData<StateResponse>?> -> {
                    Log.d("ProfileFragment", "setObserverData: ${state.response?.data}")
                    if (state.response?.code == 200) {
                        stateListingAdapter.setList(
                            state.response.data?.getOrNull(0)?.stateList?.getOrNull(
                                0
                            )?.states as ArrayList<String?>
                        )
                        stateListingAdapter.notifyDataSetChanged()
                    }
                }
            }
        })

    }

    //country dialog click
    override fun isClickedCountry(data: String, position: Int) {
        binding.edtCountry.setText(data)
        binding.edtState.setText("")
        builder.dismiss()
    }

    private fun logout() {
        PrefManager.remove()
        findNavController().navigate(SettingFragmentDirections.actionSettingFragmentToWelcomeFragment())
    }

    //state dialog click
    override fun isClickedState(data: String, position: Int) {
        binding.edtState.setText(data)
        builder.dismiss()
    }
}