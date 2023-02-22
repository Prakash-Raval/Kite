package com.example.kite.licenceagreement

import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.kite.R
import com.example.kite.databinding.FragmentLicenceAgreementBinding

class LicenceAgreementFragment : Fragment() {
    private lateinit var binding: FragmentLicenceAgreementBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.fragment_licence_agreement,
            container,
            false
        )
        setupToolbar()
        setUpNavigation()
        setSpannableText()
        return binding.root
    }

    private fun setupToolbar() {
        binding.inToolbarLicence.txtToolbarHeader.setText(R.string.driver_licence)
        binding.inToolbarLicence.imgBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setUpNavigation() {
        binding.btnContinue.setOnClickListener {
            findNavController().navigate(LicenceAgreementFragmentDirections.actionLicenceAgreementFragmentToScanLicenseFragment())
        }
    }

    private fun setSpannableText() {
        binding.txtAgreementText.movementMethod = LinkMovementMethod.getInstance()
        val signUp: ClickableSpan = object : ClickableSpan() {
            override fun updateDrawState(ds: TextPaint) {
                ds.isUnderlineText = false
            }

            override fun onClick(p0: View) {
                findNavController().navigate(R.id.action_licenceAgreementFragment_to_rentalAgreementFragment)
            }
        }


        val spannable =
            SpannableString(resources.getString(R.string.agreement_text))
        spannable.setSpan(
            signUp,
            30,
            56,
            Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )
        spannable.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.bg_button)),
            30,
            56,
            Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )

        binding.txtAgreementText.text = spannable
    }

}