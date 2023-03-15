package com.example.kite.subscription

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.kite.R
import com.example.kite.databinding.DialogSubscriptionBottomSheetBinding
import com.example.kite.databinding.FragmentSubscriptionBinding
import com.example.kite.login.model.LoginResponse
import com.example.kite.setting.SettingFragmentDirections
import com.example.kite.utils.PrefManager
import com.google.android.material.bottomsheet.BottomSheetDialog


class SubscriptionFragment : Fragment() {

    private lateinit var binding: FragmentSubscriptionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.fragment_subscription,
            container,
            false
        )
        setNavigation()
        return binding.root
    }

    private fun setNavigation() {
        binding.btnGetStarted.setOnClickListener {
            openBottomSheet()
        }
    }

    private fun openBottomSheet() {
        val dialog = BottomSheetDialog(requireContext())
        val bindingDialog: DialogSubscriptionBottomSheetBinding =
            DataBindingUtil.inflate(
                dialog.layoutInflater,
                R.layout.dialog_subscription_bottom_sheet,
                null,
                false
            )
        val subPrice =
            PrefManager.get<LoginResponse>("LOGIN_RESPONSE")?.data?.subscription?.subscriptionPrice

        bindingDialog.btnDSSubScribe.setOnClickListener {
            addCardDialog()
            dialog.dismiss()
        }
        bindingDialog.txtDSAddCard.setOnClickListener {
            findNavController().navigate(SettingFragmentDirections.actionSettingFragmentToAddCardFragment())
            dialog.dismiss()
        }

        bindingDialog.txtDSPrice.text = buildString {
            append("$")
            append(subPrice.toString())
            append("/mo")
        }

        //setting up span text for terms and condition
        bindingDialog.txtDSAgreement.movementMethod = LinkMovementMethod.getInstance()
        val terms: ClickableSpan = object : ClickableSpan() {
            override fun updateDrawState(ds: TextPaint) {
                ds.isUnderlineText = false
            }

            override fun onClick(p0: View) {
                findNavController().navigate(SettingFragmentDirections.actionSettingFragmentToTermsFragment())
                dialog.dismiss()
            }
        }
        val spannable =
            SpannableString(resources.getString(R.string.bs_agreement_subscribe))
        spannable.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.bg_button)),
            32, 52,
            Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )
        spannable.setSpan(
            terms,
            32,
            52,
            Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )
        bindingDialog.txtDSAgreement.text = spannable
        dialog.setContentView(bindingDialog.root)
        dialog.show()
    }


    //add card dialog
    private fun addCardDialog() {
        val builder: android.app.AlertDialog.Builder =
            android.app.AlertDialog.Builder(requireContext())
        builder.setTitle(R.string.app_name)
        builder.setMessage(R.string.add_card_dialog)
            .setCancelable(false)
            .setPositiveButton("OK") { _, _ ->
                findNavController().navigate(SettingFragmentDirections.actionSettingFragmentToAddCardFragment())
            }
        val alert: android.app.AlertDialog? = builder.create()
        alert?.show()
    }

}