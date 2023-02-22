package com.example.kite.changecontact

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.kite.R
import com.example.kite.databinding.FragmentChangeContactBinding


class ChangeContactFragment : Fragment() {
    private lateinit var binding: FragmentChangeContactBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.fragment_change_contact,
            container,
            false
        )
        setUPToolbar()
        return binding.root
    }

    private fun setUPToolbar(){
        binding.inContactBar.imgBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.inContactBar.txtToolbarHeader.setText(R.string.change_contact)
    }

}