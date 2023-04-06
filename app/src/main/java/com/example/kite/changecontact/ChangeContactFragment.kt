package com.example.kite.changecontact

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.kite.MainActivity
import com.example.kite.R
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.ResponseData
import com.example.kite.basefragment.BaseFragment
import com.example.kite.changecontact.model.ChangeContactResponse
import com.example.kite.changecontact.viewmodel.ChangeContactViewModel
import com.example.kite.databinding.FragmentChangeContactBinding
import com.example.kite.extensions.hideKeyboard
import com.example.kite.login.model.LoginResponse
import com.example.kite.utils.PrefManager
import com.google.android.material.snackbar.Snackbar


class ChangeContactFragment : BaseFragment() {

    private lateinit var binding: FragmentChangeContactBinding
    private lateinit var viewModel: ChangeContactViewModel
    val token = PrefManager.get<LoginResponse>("LOGIN_RESPONSE")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.fragment_change_contact,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        setUpSnackBar()
        setUPToolbar()
        getApiData()
    }

    /*
    * init view model and request token
    * */
    fun init() {
        viewModel = getViewModel()
        binding.data = viewModel
        if (token != null) {
            token.accessToken?.let {
                token.customerPhoneNumber?.let { it1 ->
                    viewModel.getToken(
                        it,
                        it1
                    )
                }
            }
        }

    }

    /*
    * creating view model
    * */
    private fun getViewModel(): ChangeContactViewModel {
        viewModel = ViewModelProvider(this)[ChangeContactViewModel::class.java]
        return viewModel
    }

    /*
    * setting up toolbar
    * */
    private fun setUPToolbar() {
        binding.inContactBar.imgBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.inContactBar.txtToolbarHeader.setText(R.string.change_contact)
    }

    /*
    * observing api response data
    * */
    private fun getApiData() {

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
                    Log.d("change_password", "setObserverData: $state")

                }
                is ResponseHandler.OnSuccessResponse<ResponseData<ChangeContactResponse>?> -> {
                    hideProgressBar()
                    Log.d("change_password", "setObserverData: ${state.response?.data}")
                    if (state.response?.code == 200) {
                        val bundle = Bundle()
                        bundle.putBoolean("OTP", true)
                        bundle.putString("MOBILE",binding.edtMobile.text.toString())
                        findNavController().navigate(
                            R.id.action_changeContactFragment_to_otpFragment,
                            bundle
                        )
                    }
                }
            }
        })
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


}