package com.example.kite.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.kite.R
import com.example.kite.databinding.FragmentRideHistoryBinding

class RideHistoryFragment : Fragment() {
    private lateinit var binding: FragmentRideHistoryBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.fragment_ride_history,
            container,
            false
        )
        back()
        return binding.root
    }

    //back arrow
    private fun back() {
        binding.imgBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}