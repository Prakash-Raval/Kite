package com.example.kite.endridechecklist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.kite.R
import com.example.kite.databinding.FragmentEndRideCheckListBinding


class EndRideCheckListFragment : Fragment() {
    private lateinit var binding: FragmentEndRideCheckListBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.fragment_end_ride_check_list,
            container,
            false
        )
        return binding.root
    }

    fun setupToolbar() {
        binding.inETCBar.imgBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.inETCBar.txtToolbarHeader.setText(R.string.end_ride_check_List)
    }


}