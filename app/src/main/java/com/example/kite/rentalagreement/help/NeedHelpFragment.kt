package com.example.kite.rentalagreement.help

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.kite.R
import com.example.kite.databinding.FragmentNeedHelpBinding

class NeedHelpFragment : Fragment() {
    private lateinit var binding : FragmentNeedHelpBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.fragment_need_help, container, false)
        setUpToolBar()
        return binding.root
    }

    private fun setUpToolBar(){
        binding.inToolbarHelp.imgBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.inToolbarHelp.txtToolbarHeader.setText(R.string.need_help)
    }

}