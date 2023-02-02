package com.example.kite.driverlicenceentry

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.kite.R
import com.example.kite.databinding.FragmentDriverLicenseEntryBinding

class DriverLicenseEntryFragment : Fragment() {
    private lateinit var binding: FragmentDriverLicenseEntryBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.fragment_driver_license_entry,
            container,
            false
        )
        setUpToolBar()
        return binding.root
    }

    private fun setUpToolBar(){
        binding.inDriverLicenseEntry.imgBack.setOnClickListener {
            findNavController().navigateUp()
            findNavController().navigateUp()
        }
        binding.inDriverLicenseEntry.txtToolbarHeader.setText(R.string.driver_license_entry)
    }

}