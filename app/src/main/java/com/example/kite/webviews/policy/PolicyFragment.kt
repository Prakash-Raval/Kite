package com.example.kite.webviews.policy

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
import com.example.kite.databinding.FragmentPolicyBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
@AndroidEntryPoint

class PolicyFragment : Fragment() {
    private lateinit var binding: FragmentPolicyBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.fragment_policy,
            container,
            false
        )
        loadData()
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
                loadUrl(Constants.POLICY_URL)

            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.wvPolicy.destroy()
    }
}