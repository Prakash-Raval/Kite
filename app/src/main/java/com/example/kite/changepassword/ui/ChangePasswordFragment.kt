package com.example.kite.changepassword.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.kite.R
import com.example.kite.changepassword.repository.ChangePasswordRepository
import com.example.kite.changepassword.viewmodel.CPVMFactory
import com.example.kite.changepassword.viewmodel.ChangePasswordViewModel
import com.example.kite.constants.Constants
import com.example.kite.databinding.FragmentChangePasswordBinding
import com.example.kite.extensions.snackError
import com.example.kite.login.model.LoginResponse
import com.example.kite.network.ApiInterface
import com.example.kite.network.RetrofitHelper
import com.example.kite.utils.PrefManager
import com.google.android.material.snackbar.Snackbar

class ChangePasswordFragment : Fragment() {
    private lateinit var binding: FragmentChangePasswordBinding
    private lateinit var viewModel: ChangePasswordViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.fragment_change_password, container, false
        )
        // Inflate the layout for this fragment
        getApiData()
        setUpToolBar()
        return binding.root
    }

    private fun setUpToolBar() {
        binding.inChangePasswordBar.imgBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.inChangePasswordBar.txtToolbarHeader.setText(R.string.change_password)
    }

    //getting data from user to change password
    private fun getApiData() {
        val service =
            RetrofitHelper.getInstance(Constants.BASE_URL).create(ApiInterface::class.java)
        val repository = ChangePasswordRepository(service)
        viewModel =
            ViewModelProvider(this, CPVMFactory(repository))[ChangePasswordViewModel::class.java]
        binding.change = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        //snack bar with error message
        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()
                ?.let { it1 -> binding.btnLogin.snackError(it1, Snackbar.LENGTH_SHORT) }
        }

        viewModel.liveData.observe(viewLifecycleOwner) {
            binding.btnLogin.snackError(it.message, Snackbar.LENGTH_SHORT)
        }

        //getting access token
        val token = PrefManager.get<LoginResponse>("LOGIN_RESPONSE")
        if (token != null) {
            token.data?.accessToken?.let { viewModel.getToken(it) }
        }
    }
}