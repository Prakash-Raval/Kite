package com.example.kite.signup.ui

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.kite.R
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.ResponseData
import com.example.kite.basefragment.BaseFragment
import com.example.kite.databinding.FragmentSignUpBinding
import com.example.kite.signup.model.SignUpResponse
import com.example.kite.signup.viewmodel.SignUpViewModel
import com.example.kite.utils.PrefManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint

class SignUpFragment : BaseFragment() {

    private lateinit var binding: FragmentSignUpBinding
    @Inject
    lateinit var viewModel: SignUpViewModel


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
        setUpToolBar()
        getSignUpData()
        spannableText()

        return binding.root
    }



    /*
    * country listing for spinner
    * */
    private fun countryList() {
        val feelings = resources.getStringArray(R.array.country_list)
        binding.edtCountry.adapter =
            activity?.let { ArrayAdapter(it, R.layout.item_dropdown_spinner, feelings) }
    }

    /*
    * setting up the toolbar
    * */
    private fun setUpToolBar() {
        binding.inSignupBar.imgBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.inSignupBar.txtToolbarHeader.setText(R.string.sign_up)
    }

    /*
    *
    * calling api for sign and setting up the observer
    * */
    private fun getSignUpData() {
        binding.signUpData = viewModel
        binding.lifecycleOwner = this

        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { it1 ->
                Toast.makeText(requireContext(), it1, Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.responseLiveData.observe(viewLifecycleOwner, Observer { state ->
            if (state == null) {
                return@Observer
            }
            when (state) {
                is ResponseHandler.Loading -> {
                    showProgressDialog()
                    Log.d("ViewTripFragment", "setObserverData: $state")
                }
                is ResponseHandler.OnFailed -> {
                    hideProgressBar()
                    Log.d("ViewTripFragment", "setObserverData: $state")

                }
                is ResponseHandler.OnSuccessResponse<ResponseData<SignUpResponse>?> -> {
                    hideProgressBar()
                    if (state.response?.code == 200) {
                        val action =
                            SignUpFragmentDirections.actionSignUpFragmentToOtpFragment()
                        findNavController().navigate(action)
                        PrefManager.put(state.response.data?.accessToken , "Token")
                    } else {
                        Toast.makeText(
                            requireContext(),
                            state.response?.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        })

    }

    /*
    * setting up the spannable text for term and policy
    * */
    private fun spannableText() {
        binding.txtTerms.movementMethod = LinkMovementMethod.getInstance()
        val privacy: ClickableSpan = object : ClickableSpan() {
            override fun updateDrawState(ds: TextPaint) {
                ds.isUnderlineText = true
            }

            override fun onClick(p0: View) {
                val action = SignUpFragmentDirections.actionSignUpFragmentToPolicyFragment()
                findNavController().navigate(action)
            }
        }

        val terms: ClickableSpan = object : ClickableSpan() {
            override fun updateDrawState(ds: TextPaint) {
                ds.isUnderlineText = true
            }

            override fun onClick(p0: View) {
                val action = SignUpFragmentDirections.actionSignUpFragmentToTermsFragment()
                findNavController().navigate(action)
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
            terms,
            29,
            49,
            Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )
        spannable.setSpan(
            privacy,
            54,
            68,
            Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )
        binding.txtTerms.text = spannable
    }
}

