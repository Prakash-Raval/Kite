package com.example.kite.endtrip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.kite.R
import com.example.kite.databinding.FragmentEndTripDialogBinding


class EndTripDialogFragment : Fragment() {
    private lateinit var binding: FragmentEndTripDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.fragment_end_trip_dialog,
            container,
            false
        )
        return binding.root
    }


}