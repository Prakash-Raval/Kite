package com.example.kite.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.kite.R
import com.example.kite.databinding.FragmentSplashBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SplashFragment : Fragment() {
    private lateinit var binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.fragment_splash,
            container,
            false
        )
        lifecycleScope.launch {
            delay(3000)
            loadFragment()
        }
        return binding.root
    }

    private fun loadFragment() {
        val action = SplashFragmentDirections.actionSplashFragmentToWelcomeFragment()
        findNavController().navigate(action)
    }

}