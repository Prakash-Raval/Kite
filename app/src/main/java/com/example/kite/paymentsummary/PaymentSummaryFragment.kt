package com.example.kite.paymentsummary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.kite.R
import com.example.kite.databinding.FragmentPaymentSummaryBinding


class PaymentSummaryFragment : Fragment() {
    private lateinit var binding: FragmentPaymentSummaryBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.fragment_payment_summary,
            container,
            false
        )
        setUPToolbar()
        return binding.root
    }

    private fun setUPToolbar() {
        binding.paymentSummaryBar.txtToolbarHeader.setText(R.string.payment_summary)
        binding.paymentSummaryBar.imgBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

}