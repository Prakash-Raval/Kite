package com.example.kite.ridedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.kite.R
import com.example.kite.databinding.FragmentRideDetailsBinding


class RideDetailsFragment : Fragment() {

    private lateinit var binding: FragmentRideDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.fragment_ride_details,
            container,
            false
        )
        return binding.root
    }

    fun setUPToolbar() {
        binding.inRideBar.txtToolbarHeader.setText(R.string.ride_details)
        binding.inRideBar.imgBack.visibility = View.GONE
        binding.inRideBar.imgClose.visibility = View.VISIBLE
    }


}