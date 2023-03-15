package com.example.kite.rateus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.kite.R
import com.example.kite.databinding.FragmentRateUsBinding


class RateUsFragment : Fragment() {

    private lateinit var binding: FragmentRateUsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.fragment_rate_us,
            container,
            false
        )
        return binding.root
    }


}