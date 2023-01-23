package com.example.kite.selectpayment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.kite.R
import com.example.kite.databinding.FragmentSelectPaymentBinding


class SelectPaymentFragment : Fragment() {
    private lateinit var binding: FragmentSelectPaymentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.fragment_select_payment,
            container,
            false
        )
        setUpNavigation()
        return binding.root
    }


    private fun setUpNavigation() {
        binding.txtAddCard.setOnClickListener {
            findNavController().navigate(SelectPaymentFragmentDirections.actionSelectPaymentFragmentToAddCardFragment())
        }
        binding.imgBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

}