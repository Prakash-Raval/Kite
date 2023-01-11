package com.example.kite.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.kite.R
import com.example.kite.databinding.FragmentSplashBinding
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

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
        Handler(Looper.getMainLooper()).postDelayed({
            loadFragment()
        }, 3000)

        return binding.root
    }

    private fun loadFragment() {
        findNavController().navigate(R.id.action_splashFragment_to_welcomeFragment)
    }

}