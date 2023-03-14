package com.example.kite.forgetpassword.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.kite.R
import com.example.kite.constants.Constants
import com.example.kite.databinding.FragmentForgetPasswordBinding
import com.example.kite.forgetpassword.repository.ForgotPasswordRepository
import com.example.kite.forgetpassword.viewmodel.FPVMFactory
import com.example.kite.forgetpassword.viewmodel.ForgotPasswordViewModel
import com.example.kite.network.ApiInterface
import com.example.kite.network.RetrofitHelper

class ForgetPasswordFragment : Fragment() {
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
        setUpToolBar()
        getApiData()
        return binding.root
    }

    private fun setUpToolBar() {
        binding.inForgotBar.imgBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.inForgotBar.txtToolbarHeader.setText(R.string.forget_password)
    }

    //getting api data
    private fun getApiData() {
        val service =
            RetrofitHelper.getInstance(Constants.BASE_URL).create(ApiInterface::class.java)
        val repository = ForgotPasswordRepository(service)
        viewModel =
            ViewModelProvider(this, FPVMFactory(repository))[ForgotPasswordViewModel::class.java]
        binding.data = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }
}