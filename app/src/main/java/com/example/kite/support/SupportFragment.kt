package com.example.kite.support

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.kite.R
import com.example.kite.databinding.FragmentSupportBinding

class SupportFragment : Fragment() {
    private lateinit var binding: FragmentSupportBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.fragment_support,
            container,
            false
        )
        getSupportData()
        back()
        return binding.root
    }

    //back from support
    private fun back() {
        binding.imgBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    //opening web view for support data
    @SuppressLint("SetJavaScriptEnabled")
    private fun getSupportData() {
        binding.wvSupport.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            settings.setSupportZoom(true)
            loadUrl("https://admin.kitemobilitydev.com/#/privacy-policy?lang=english")
        }
    }
}