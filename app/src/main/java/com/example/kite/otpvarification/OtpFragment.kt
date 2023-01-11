package com.example.kite.otpvarification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.kite.R
import com.example.kite.databinding.FragmentOtpBinding

class OtpFragment : Fragment() {

    private lateinit var binding: FragmentOtpBinding
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


}