package com.example.kite.forgetpassword.ui

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
import com.example.kite.base.network.model.EmptyResponse
import com.example.kite.R
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.ResponseData
import com.example.kite.basefragment.BaseFragment
import com.example.kite.databinding.FragmentForgetPasswordBinding
import com.example.kite.forgetpassword.viewmodel.ForgotPasswordViewModel

class ForgetPasswordFragment : BaseFragment() {

    private lateinit var binding: FragmentForgetPasswordBinding
    private lateinit var viewModel: ForgotPasswordViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.fragment_forget_password,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolBar()
        getApiData()
    }

    /*
    * setting up toolbar
    * */
    private fun setUpToolBar() {
        binding.inForgotBar.imgBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.inForgotBar.txtToolbarHeader.setText(R.string.forgot_password)
    }

    /*
    * api call for forgot password
    * */
    private fun getApiData() {
        viewModel = getViewModel()
        binding.data = viewModel
        /*
        * observing error messages
        * */
        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            binding.txtForgetEmail.error = it.getContentIfNotHandled()
        }
        viewModel.responseLiveData.observe(viewLifecycleOwner, Observer { state ->
            if (state == null) {
                return@Observer
            }
            when (state) {
                is ResponseHandler.Loading -> {
                    showProgressDialog()
                    Log.d("ForgotPasswordFragment", "setObserverData: $state")
                }
                is ResponseHandler.OnFailed -> {
                    hideProgressBar()
                    Toast.makeText(requireContext(), "Please try again later", Toast.LENGTH_SHORT)
                        .show()
                    Log.d("ForgotPasswordFragment", "setObserverData: $state")
                }
                is ResponseHandler.OnSuccessResponse<ResponseData<EmptyResponse>?> -> {
                    hideProgressBar()
                    if (state.response?.code == 200) {
                        Toast.makeText(
                            requireContext(),
                            "${state.response.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                        findNavController().navigateUp()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "${state.response?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    Log.d("ForgotPasswordFragment", "setObserverData: ${state.response?.data}")
                }
            }
        })
    }

    /*
    * init view model
    * */
    private fun getViewModel(): ForgotPasswordViewModel {
        viewModel = ViewModelProvider(this)[ForgotPasswordViewModel::class.java]
        return viewModel
    }
}