package com.example.kite.otpverification.ui

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
import com.example.kite.MainActivity
import com.example.kite.R
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.EmptyResponse
import com.example.kite.base.network.model.ResponseData
import com.example.kite.basefragment.BaseFragment
import com.example.kite.databinding.FragmentOtpBinding
import com.example.kite.extensions.hideKeyboard
import com.example.kite.login.model.LoginResponse
import com.example.kite.otpverification.model.PhoneRequest
import com.example.kite.otpverification.viewmodel.OtpViewModel
import com.example.kite.otpverification.viewmodel.PhoneViewModel
import com.example.kite.utils.PrefManager

class OtpFragment : BaseFragment() {

    /*
    * variables
    * */
    private lateinit var binding: FragmentOtpBinding
    private lateinit var viewModel: OtpViewModel
    private lateinit var viewModelPhone: PhoneViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.fragment_otp,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*
        * method calls
        * */
        init()
        getOtp()
        setUpToolBar()
        setUpSnackBar()
    }

    /*
    * creating view model for otp
    * */
    fun getViewModel(): OtpViewModel {
        viewModel = ViewModelProvider(this)[OtpViewModel::class.java]
        return viewModel
    }

    /*
   * creating view model for change contact
   * */
    private fun getViewModelPhone(): PhoneViewModel {
        viewModelPhone = ViewModelProvider(this)[PhoneViewModel::class.java]
        return viewModelPhone
    }

    /*
    * init view model
    * */
    fun init() {
        viewModel = getViewModel()
        viewModelPhone = getViewModelPhone()
        binding.otpData = viewModel
        var token = PrefManager.get<String>("Token")
        val args = this.arguments
        val isContact = args?.getBoolean("OTP")

        if (isContact == true) {
            token = PrefManager.get<LoginResponse>("LOGIN_RESPONSE")?.accessToken
        }
        if (token != null) {
            viewModel.getToken(token)
        }
    }

    private fun makeApiCallPhone() {
        val token = PrefManager.get<LoginResponse>("LOGIN_RESPONSE")?.accessToken
        val args = this.arguments
        viewModelPhone.getPhone(
            PhoneRequest(
                accessToken = token,
                otpCode = binding.edtOTPVerify.text.toString().trim(),
                phoneNumber = args?.getString("MOBILE")
            )
        )
        setObserver()
    }

    /*
    * setting up observer for otp verification
    * */
    private fun getOtp() {

        viewModel.responseLiveData.observe(viewLifecycleOwner, Observer { state ->
            if (state == null) {
                return@Observer
            }
            when (state) {
                is ResponseHandler.Loading -> {
                    showProgressDialog()
                    Log.d("change_password", "setObserverData: $state")
                }
                is ResponseHandler.OnFailed -> {
                    hideProgressBar()
                    Toast.makeText(requireContext(), "Wrong OTP Entered", Toast.LENGTH_SHORT).show()
                    findNavController().navigateUp()
                    Log.d("change_password", "setObserverData: $state")

                }
                is ResponseHandler.OnSuccessResponse<ResponseData<EmptyResponse>?> -> {
                    hideProgressBar()
                    val args = this.arguments
                    val isContact = args?.getBoolean("OTP")
                    Log.d("change_password", "setObserverData: ${state.response?.data}")
                    if (state.response?.code == 200) {
                        if (isContact == true) {
                            makeApiCallPhone()
                        } else {
                            findNavController().navigate(OtpFragmentDirections.actionOtpFragmentToLoginFragment())
                        }
                    }
                }
            }
        })

    }

    /*
    * setting up the toolbar
    * */
    private fun setUpToolBar() {
        binding.inOTPBar.imgBack.setOnClickListener {
            val args = this.arguments
            val isContact = args?.getBoolean("OTP")
            if (isContact == true) {
                findNavController().navigate(OtpFragmentDirections.actionOtpFragmentToChangeContactFragment())
            } else {
                findNavController().navigate(OtpFragmentDirections.actionOtpFragmentToLoginFragment())
            }
        }
        binding.inOTPBar.txtToolbarHeader.setText(R.string.otp)
    }

    /*
  * creating method for showing snack bar
  * */
    private fun setUpSnackBar() {
        viewModel.getSnakeBarMessage().observe(viewLifecycleOwner) { o: Any ->
            if (o is Int) {
                hideKeyboard()
                (activity as MainActivity).resources?.getString(o)?.let { showSnackBar(it) }!!
            } else if (o is String) {
                hideKeyboard()
                showSnackBar(o)
            }
        }
    }

    /*
    * api call observer for phone change
    * */
    fun setObserver() {
        viewModelPhone.responseLiveData.observe(viewLifecycleOwner, Observer { state ->
            if (state == null) {
                return@Observer
            }
            when (state) {
                is ResponseHandler.Loading -> {
                    showProgressDialog()
                    Log.d("otp_Verification", "setObserverData: $state")
                }
                is ResponseHandler.OnFailed -> {
                    hideProgressBar()
                    Log.d("otp_Verification", "setObserverData: $state")

                }
                is ResponseHandler.OnSuccessResponse<ResponseData<EmptyResponse>?> -> {
                    hideProgressBar()
                    Log.d("otp_Verification", "setObserverData: ${state.response?.data}")
                    if (state.response?.code == 200) {
                        findNavController().navigate(OtpFragmentDirections.actionOtpFragmentToChangeContactFragment())

                    }
                }
            }
        })

    }
}