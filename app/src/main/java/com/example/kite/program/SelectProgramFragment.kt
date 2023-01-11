package com.example.kite.program

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.kite.R
import com.example.kite.databinding.FragmentSelectProgramBinding


class SelectProgramFragment : Fragment() {
    private lateinit var binding: FragmentSelectProgramBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.fragment_select_program,
            container,
            false
        )
        // Inflate the layout for this fragment
        return binding.root
    }


}