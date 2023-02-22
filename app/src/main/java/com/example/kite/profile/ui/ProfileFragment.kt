package com.example.kite.profile.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.kite.R
import com.example.kite.constants.Constants
import com.example.kite.countrylisting.*
import com.example.kite.databinding.FragmentProfileBinding
import com.example.kite.login.model.LoginResponse
import com.example.kite.network.ApiInterface
import com.example.kite.network.RetrofitHelper
import com.example.kite.profile.repository.ViewProfileRepository
import com.example.kite.profile.viewmodel.ViewProfileVMFactory
import com.example.kite.profile.viewmodel.ViewProfileViewModel
import com.example.kite.statelisting.StateListingAdapter
import com.example.kite.utils.PrefManager
import kotlinx.coroutines.launch


class ProfileFragment : Fragment(){

    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: ViewProfileViewModel
    private lateinit var countryViewModel: CountryViewModel
    private lateinit var countryListingAdapter: CountryListingAdapter
    private lateinit var stateListingAdapter: StateListingAdapter

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
        val service =
            RetrofitHelper.getInstance(Constants.BASE_URL).create(ApiInterface::class.java)
        val repository = ViewProfileRepository(service)
        viewModel = ViewModelProvider(
            this, ViewProfileVMFactory(repository)
        )[ViewProfileViewModel::class.java]

        viewModel.profileLiveData.observe(viewLifecycleOwner) {
            binding.viewProfile = it.data
        }
        binding.lifecycleOwner = this

        //giving access token to get particular user
        val token = PrefManager.get<LoginResponse>("LOGIN_RESPONSE")
        lifecycleScope.launch {
            if (token != null) {
                token.data?.accessToken?.let { viewModel.getToken(it) }
            }
        }
    }

    //navigation to policy abd terms pages
    private fun getTermsPage() {
        binding.txtPolicy.setOnClickListener {
            findNavController().navigate(R.id.policyFragment)
        }
        binding.txtTerms.setOnClickListener {
            findNavController().navigate(R.id.termsFragment)
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
            openDialogCountry()
        }
        binding.edtState.setOnClickListener {
            openDialogState()
        }

    }
    //opening dialog for state list
    @SuppressLint("MissingInflatedId")
    private fun openDialogState() {
        val builder = AlertDialog.Builder(requireContext())
            .create()
        builder.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val view = layoutInflater.inflate(R.layout.dialog_state_listing, null)
        builder.setView(view)
        countryListingAdapter = CountryListingAdapter()

        val recycler = view.findViewById<RecyclerView>(R.id.rvStateList)
        recycler.adapter = countryListingAdapter

        builder.setCanceledOnTouchOutside(false)
        builder.show()
    }
    //opening dialog for country list
    private fun openDialogCountry() {
        val builder = AlertDialog.Builder(requireContext())
            .create()
        builder.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val view = layoutInflater.inflate(R.layout.dialog_country_picker, null)
        builder.setView(view)
        countryListingAdapter = CountryListingAdapter()

        val recycler = view.findViewById<RecyclerView>(R.id.rvCountryList)
        recycler.adapter = countryListingAdapter

        builder.setCanceledOnTouchOutside(false)
        builder.show()
        getCountryData()
    }

    // getting country data from the api
    @SuppressLint("NotifyDataSetChanged")
    private fun getCountryData() {
        val service =
            RetrofitHelper.getInstance(Constants.BASE_URL).create(ApiInterface::class.java)
        val repository = CountryRepository(service)
        countryViewModel = ViewModelProvider(
            this, CountryVMFactory(repository)
        )[CountryViewModel::class.java]
        countryViewModel.getCountryList()
        countryViewModel.profileLiveData.observe(viewLifecycleOwner) {
            countryListingAdapter.setList(it.countryList as ArrayList<CountryResponse.Country>)
            countryListingAdapter.notifyDataSetChanged()
        }
    }


}