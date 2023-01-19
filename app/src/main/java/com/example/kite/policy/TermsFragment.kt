package com.example.kite.policy

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
import com.example.kite.databinding.FragmentTermsBinding
import kotlinx.coroutines.launch

class TermsFragment : Fragment() {
    private lateinit var binding: FragmentTermsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.fragment_terms,
            container,
            false
        )
        loadData()
        binding.progress.visibility = View.VISIBLE
        binding.imgBack.setOnClickListener {
            findNavController().navigateUp()
        }
        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun loadData() {
        lifecycleScope.launch {
            binding.wvPolicy.apply {
                webViewClient = WebViewClient()
                settings.javaScriptEnabled = true
                settings.setSupportZoom(true)
                loadUrl("https://admin.kitemobilitydev.com/#/terms-condition?lang=english")
                binding.progress.visibility = View.INVISIBLE
            }

        }
    }
}