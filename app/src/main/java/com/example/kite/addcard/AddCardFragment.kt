package com.example.kite.addcard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.kite.R
import com.example.kite.databinding.FragmentAddCardBinding

class AddCardFragment : Fragment() {
    private lateinit var binding: FragmentAddCardBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.fragment_add_card,
            container,
            false
        )
        setNavigation()
        return binding.root
    }

    private fun setNavigation() {
        binding.imgBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }


}