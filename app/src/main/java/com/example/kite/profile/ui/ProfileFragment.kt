package com.example.kite.profile.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.kite.R
import com.example.kite.constants.Constants
import com.example.kite.databinding.FragmentProfileBinding
import com.example.kite.login.model.LoginResponse
import com.example.kite.network.ApiInterface
import com.example.kite.network.RetrofitHelper
import com.example.kite.profile.repository.ViewProfileRepository
import com.example.kite.profile.viewmodel.ViewProfileVMFactory
import com.example.kite.profile.viewmodel.ViewProfileViewModel
import com.example.kite.utils.PrefManager
import kotlinx.coroutines.launch


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: ViewProfileViewModel
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
}