package com.example.kite.verifiedinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.kite.R
import com.example.kite.databinding.FragmentVerifyInfoBinding
import com.example.kite.login.model.LoginResponse
import com.example.kite.setting.SettingFragmentDirections
import com.example.kite.utils.PrefManager

class VerifyInfoFragment : Fragment() {
    private lateinit var binding: FragmentVerifyInfoBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.fragment_verify_info,
            container,
            false
        )
        getCustomerData()
        setNavigation()
        return binding.root
    }

    //using shared pref to get user info
    private fun getCustomerData() {
        val customerData = PrefManager.get<LoginResponse>("LOGIN_RESPONSE")
        binding.txtVerifyName.text = customerData?.customerFirstName

        val isVerified = customerData?.isTruliooVerified
        if (isVerified == 1) {
            binding.txtVerifyStatus.setText(R.string.verified)
            binding.btnUpdate.visibility = View.GONE
            binding.edtVerificationExpiry.setText(customerData.subscription?.subscriptionEndDate.toString())
            binding.edtCountryCod.setText(customerData.country)
        } else {
            binding.txtVerifyStatus.setText(R.string.not_verified)
            binding.btnUpdate.visibility = View.VISIBLE
            binding.edtVerificationExpiry.setText("-")
            binding.edtCountryCod.setText("-")
        }
    }


    private fun setNavigation() {
        binding.btnUpdate.setOnClickListener {
            findNavController().navigate(SettingFragmentDirections.actionSettingFragmentToLicenceAgreementFragment())
        }
    }
}