package com.example.kite.otpverification.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.kite.R
import com.example.kite.constants.Constants
import com.example.kite.databinding.FragmentOtpBinding
import com.example.kite.network.ApiInterface
import com.example.kite.network.RetrofitHelper
import com.example.kite.otpverification.repository.OtpRepository
import com.example.kite.otpverification.viewmodel.OtpVMFactory
import com.example.kite.otpverification.viewmodel.OtpViewModel
import com.example.kite.utils.PrefManager

class OtpFragment : Fragment() {


    private lateinit var binding: FragmentOtpBinding
    private lateinit var viewModel: OtpViewModel


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
        getOtp()
        setUpToolBar()
        return binding.root
    }

    private fun getOtp() {
        val otpService =
            RetrofitHelper.getInstance(Constants.BASE_URL).create(ApiInterface::class.java)
        val repository = OtpRepository(otpService)
        viewModel =
            ViewModelProvider(this, OtpVMFactory(repository))[OtpViewModel::class.java]
        binding.otpData = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { it1 ->
                Toast.makeText(requireContext(), it1, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.otpLiveData.observe(viewLifecycleOwner) {
            if (it.code == 200) {
                Toast.makeText(requireContext(), "Otp Verified", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.loginFragment)
            } else {
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
            }
        }

        val token = PrefManager.get<String>("Token")
        if (token != null) {
            viewModel.otpCheck(token)
        }
        if (viewModel.isCheck) {
            findNavController().navigate(R.id.loginFragment)
        }
    }

    /*
    * setting up the toolbar
    * */
    private fun setUpToolBar() {
        binding.inOTPBar.imgBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.inOTPBar.txtToolbarHeader.setText(R.string.otp)
    }
}