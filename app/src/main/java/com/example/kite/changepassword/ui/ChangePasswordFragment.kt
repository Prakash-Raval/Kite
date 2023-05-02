package com.example.kite.changepassword.ui

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
import com.example.kite.base.network.model.EmptyResponse
import com.example.kite.base.network.model.ResponseData
import com.example.kite.basefragment.BaseFragment
import com.example.kite.changepassword.viewmodel.ChangePasswordViewModel
import com.example.kite.databinding.FragmentChangePasswordBinding
import com.example.kite.login.model.LoginResponse
import com.example.kite.utils.PrefManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ChangePasswordFragment : BaseFragment() {

    /*
    variables
    * */
    private lateinit var binding: FragmentChangePasswordBinding
    @Inject
    lateinit var viewModel: ChangePasswordViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.fragment_change_password, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        setUpSnackBar(viewModel)
        getApiData()
        setUpToolBar()
    }

    /*
    * init view model
    * */
    private fun init() {
        val token = PrefManager.get<LoginResponse>("LOGIN_RESPONSE")?.accessToken
        if (token != null) {
            viewModel.getToken(token)
        }
    }

    /*
    * getting data from api
    * */
    private fun getApiData() {
        binding.change = viewModel


        /*
        * observing data from api
        * */
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
                    Toast.makeText(requireContext(), "Please try again later", Toast.LENGTH_SHORT)
                        .show()
                    findNavController().navigateUp()
                    Log.d("change_password", "setObserverData: $state")

                }
                is ResponseHandler.OnSuccessResponse<ResponseData<EmptyResponse>?> -> {
                    hideProgressBar()
                    Log.d("change_password", "setObserverData: ${state.response?.data}")
                    if (state.response?.code == 200) {
                        Toast.makeText(
                            requireContext(),
                            "Password changed Successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                        findNavController().navigateUp()
                    }
                }
            }
        })
    }

    /*
    * setting up the toolbar
    *
    * */
    private fun setUpToolBar() {
        binding.inChangePasswordBar.imgBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.inChangePasswordBar.txtToolbarHeader.setText(R.string.change_password)
    }

}