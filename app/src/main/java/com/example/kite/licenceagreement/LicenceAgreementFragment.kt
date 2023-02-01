package com.example.kite.licenceagreement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.kite.R
import com.example.kite.databinding.FragmentLicenceAgreementBinding

class LicenceAgreementFragment : Fragment() {
    private lateinit var binding: FragmentLicenceAgreementBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.fragment_licence_agreement,
            container,
            false
        )
        setupToolbar()
        return binding.root
    }

    private fun setupToolbar() {
        binding.inToolbarLicence.txtToolbarHeader.setText(R.string.driver_licence)
        binding.inToolbarLicence.imgBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

}