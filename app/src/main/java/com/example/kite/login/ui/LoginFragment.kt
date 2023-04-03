package com.example.kite.login.ui

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
import com.example.kite.databinding.FragmentLoginBinding
import com.example.kite.login.model.LoginResponse
import com.example.kite.login.viewmodel.LoginViewModel
import com.example.kite.utils.PrefManager


class LoginFragment : BaseFragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.fragment_login,
            container,
            false
        )
        //pref
        activity?.application?.let { PrefManager.with(it) }

        // Inflate the layout for this fragment
        loadFragment()
        getRetrofitData()
        setUpToolBar()
        return binding.root
    }

    private fun setUpToolBar() {
        binding.inLoginBar.imgBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.inLoginBar.txtToolbarHeader.setText(R.string.login)
    }

    //navigation from fragment
    private fun loadFragment() {
        binding.txtForgetPassword.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgetPasswordFragment)
        }
        binding.txtAccountCreate.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }
    }

    private fun getViewModel(): LoginViewModel {
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        return viewModel
    }


    //getting data from api
    private fun getRetrofitData() {
        viewModel = getViewModel()

        //binding variable from xml
        binding.loginData = viewModel

        //getting error msg from view model and displaying it to related textInputLayout
        viewModel.errorMessage.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { it1 ->
                when (it1.fromWhere) {
                    "Email" -> {
                        binding.txtLoginEmail.error = it1.errorMessage
                    }
                    "Password" -> {
                        binding.txtLoginPassword.error = it1.errorMessage
                    }
                }
            }
        }
        viewModel.responseLiveData.observe(viewLifecycleOwner, Observer { state ->
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
                    Toast.makeText(
                        requireContext(),
                        "Incorrect password",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is ResponseHandler.OnSuccessResponse<ResponseData<LoginResponse>?> -> {
                    hideProgressBar()
                    Log.d("ViewTripFragment", "setObserverData: ${state.response?.data}")
                    if (state.response?.code == 200) {
                        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToSelectProgramFragment())
                        PrefManager.put(state.response.data, "LOGIN_RESPONSE")
                        PrefManager.put(state.response.data?.subscription, "SUBSCRIPTION-DATA")
                    } else {
                        Toast.makeText(
                            requireContext(),
                            state.response?.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        })
    }
}