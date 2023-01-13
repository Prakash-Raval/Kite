package com.example.kite.signup.ui

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
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.kite.R
import com.example.kite.constants.Constants
import com.example.kite.databinding.FragmentSignUpBinding
import com.example.kite.network.ApiInterface
import com.example.kite.network.RetrofitHelper
import com.example.kite.signup.repository.SignUpRepository
import com.example.kite.signup.viewmodel.SignUpVMFFactory
import com.example.kite.signup.viewmodel.SignUpViewModel


class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    private lateinit var viewModel: SignUpViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.fragment_sign_up,
            container,
            false
        )
        // Inflate the layout for this fragment
        countryList()
        loadFragment()
        getSignUpData()
        return binding.root
    }

    private fun countryList() {
        val feelings = resources.getStringArray(R.array.country_list)
        binding.edtCountry.adapter =
            activity?.let { ArrayAdapter(it, R.layout.dropdown_item, feelings) }
    }

    private fun loadFragment() {
        binding.imgBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun getSignUpData() {
        val signUpService =
            RetrofitHelper.getInstance(Constants.BASE_URL).create(ApiInterface::class.java)
        val repository = SignUpRepository(signUpService)
        viewModel =
            ViewModelProvider(this, SignUpVMFFactory(repository))[SignUpViewModel::class.java]
        binding.signUpData = viewModel

        binding.lifecycleOwner = this
        spannableText()
        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { it1 ->
                Toast.makeText(requireContext(), it1, Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun spannableText() {
        binding.txtTerms.movementMethod = LinkMovementMethod.getInstance()
        val privacy: ClickableSpan = object : ClickableSpan() {
            override fun updateDrawState(ds: TextPaint) {
                ds.isUnderlineText = true
            }
            override fun onClick(p0: View) {

            }
        }

        val terms: ClickableSpan = object : ClickableSpan() {
            override fun updateDrawState(ds: TextPaint) {
                ds.isUnderlineText = true
            }
            override fun onClick(p0: View) {
            }
        }

        val spannable =
            SpannableString(resources.getString(R.string.terms))
        //val boldSpan = StyleSpan(Typeface.BOLD)
        spannable.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.bg_button)),
            29,
            49,
            Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )

        spannable.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.bg_button)),
            54,
            68,
            Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )
        spannable.setSpan(
            privacy,
            29,
            49,
            Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )
        spannable.setSpan(
            terms,
            54,
            68,
            Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )
        binding.txtTerms.text = spannable
    }
}

