package com.example.kite.rentalagreement

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.kite.R
import com.example.kite.constants.Constants
import com.example.kite.databinding.FragmentRentalAgreementBinding
import kotlinx.coroutines.launch


class RentalAgreementFragment : Fragment() {
    private lateinit var binding: FragmentRentalAgreementBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.fragment_rental_agreement,
            container,
            false
        )
        setUpToolbar()
        loadUrl()
        return binding.root
    }

    private fun setUpToolbar() {
        binding.inToolbarRental.imgBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.inToolbarRental.txtToolbarHeader.setText(R.string.rental_agreement)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun loadUrl() {
        lifecycleScope.launch {
            binding.wvRental.apply {
                webViewClient = WebViewClient()
                settings.javaScriptEnabled = true
                settings.setSupportZoom(true)
                loadUrl(Constants.RENTAL_AGREEMENT)

            }
        }
    }


}