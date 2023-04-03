package com.example.kite.subscription

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
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.kite.R
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.ResponseData
import com.example.kite.basefragment.BaseFragment
import com.example.kite.databinding.DialogSubscriptionBottomSheetBinding
import com.example.kite.databinding.FragmentSubscriptionBinding
import com.example.kite.login.model.LoginResponse
import com.example.kite.selectpayment.model.GetCardRequest
import com.example.kite.selectpayment.model.GetCardResponse
import com.example.kite.selectpayment.viewmodel.GetCardViewModel
import com.example.kite.setting.SettingFragmentDirections
import com.example.kite.subscription.model.AddSubRequest
import com.example.kite.subscription.model.AddSubResponse
import com.example.kite.subscription.model.CancelSubRequest
import com.example.kite.subscription.model.CancelSubResponse
import com.example.kite.subscription.viewmodel.SubViewModel
import com.example.kite.utils.PrefManager
import com.google.android.material.bottomsheet.BottomSheetDialog


class SubscriptionFragment : BaseFragment() {

    private lateinit var binding: FragmentSubscriptionBinding
    private lateinit var viewModel: SubViewModel
    private lateinit var viewModelCard: GetCardViewModel
    val token = PrefManager.get<LoginResponse>("LOGIN_RESPONSE")?.accessToken
    val customerId = PrefManager.get<LoginResponse>("LOGIN_RESPONSE")?.customerId

    private var cardNumber = ""

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
        checkVisibility()
        getApiDataCard()
        return binding.root
    }

    private fun setNavigation() {
        binding.btnGetStarted.setOnClickListener {
            openBottomSheet()
        }
        binding.txtCancelSub.setOnClickListener {
            cancelSub()
        }
        binding.txtRestartSub.setOnClickListener {
            addSub()
        }
    }

    //getting the view model
    private fun getViewModelCard(): GetCardViewModel {
        viewModelCard = ViewModelProvider(this)[GetCardViewModel::class.java]
        return viewModelCard
    }

    //collecting request data
    private fun getApiDataCard() {
        viewModelCard = getViewModelCard()
        //get request data

        viewModelCard.getGetCardRequest(
            GetCardRequest(
                access_token = token,
                customer_id = customerId.toString()
            )
        )
        setObserverCard()
    }

    //setting up the observers
    private fun setObserverCard() {
        //handling error event in snack bar
        viewModelCard.liveData.observe(viewLifecycleOwner, Observer { state ->
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
                is ResponseHandler.OnSuccessResponse<ResponseData<GetCardResponse>?> -> {
                    hideProgressBar()
                    Log.d("ViewTripFragment", "setObserverData: ${state.response?.data}")
                    cardNumber = state.response?.data?.details?.get(0)?.cardNumber.toString()

                }
            }
        })
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
            PrefManager.get<LoginResponse>("LOGIN_RESPONSE")

        if (subPrice?.isDefaultCard == 1) {
            bindingDialog.gpDS.visibility = View.VISIBLE
            "....  ....  .... $cardNumber".also { bindingDialog.txtCardNumber.text = it }
        }

        bindingDialog.btnDSSubScribe.setOnClickListener {
            if (subPrice?.isDefaultCard == 0) {
                addCardDialog()
                dialog.dismiss()
            } else {
                addSub()
                dialog.dismiss()
            }

        }
        bindingDialog.txtDSAddCard.setOnClickListener {
            findNavController().navigate(SettingFragmentDirections.actionSettingFragmentToAddCardFragment())
            dialog.dismiss()
        }

        bindingDialog.txtDSPrice.text = buildString {
            append("$")
            append(subPrice?.subscription?.subscriptionPrice.toString())
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

    //if user subscribed or not
    private fun checkVisibility() {
        val sub =
            PrefManager.get<LoginResponse.Subscription>("SUBSCRIPTION-DATA")
        val token =
            PrefManager.get<LoginResponse>("LOGIN_RESPONSE")?.subscription?.subscriptionPrice
        if (sub?.isSubscribe == 0) {
            binding.subNest1.visibility = View.VISIBLE
            binding.subNest2.visibility = View.GONE
        } else {
            binding.subNest1.visibility = View.GONE
            binding.subNest2.visibility = View.VISIBLE
            "$${token.toString()}/mo".also {
                binding.txtRat2.text = it
            }
            binding.edtStartDate.setText(sub?.subscriptionStartDate)
            binding.edtEndDate.setText(sub?.subscriptionEndDate)
        }

        if (sub?.isSubscriptionAutorenewal == 0) {
            binding.txtRestartSub.visibility = View.VISIBLE
            binding.txtRestartSub2.visibility = View.VISIBLE
            binding.txtCancelSub.visibility = View.GONE
        } else {
            binding.txtRestartSub.visibility = View.GONE
            binding.txtRestartSub2.visibility = View.GONE
            binding.txtCancelSub.visibility = View.VISIBLE
        }

    }

    //cancel subscription
    private fun cancelSub() {
        binding.txtCancelSub.setOnClickListener {
            getApiDataCancel()
        }
    }

    //add subscription
    private fun addSub() {
        getApiData()
    }

    private fun getViewModel(): SubViewModel {
        viewModel = ViewModelProvider(this)[SubViewModel::class.java]
        return viewModel
    }

    private fun getApiData() {
        viewModel = getViewModel()
        //add subscription viw model data
        viewModel.getAddSubRequest(
            AddSubRequest(
                access_token = token
            )
        )
        setObserver()
    }

    private fun getApiDataCancel() {
        viewModel = getViewModel()
        //cancel subscription view model data
        viewModel.getCancelSubRequest(
            CancelSubRequest(
                access_token = token
            )
        )
        setObserverCancel()
    }

    private fun setObserver() {
        //handling error event in snack bar
        viewModel.liveDataADD.observe(viewLifecycleOwner, Observer { state ->
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
                is ResponseHandler.OnSuccessResponse<ResponseData<AddSubResponse>?> -> {
                    hideProgressBar()
                    Log.d("ViewTripFragment", "setObserverData: ${state.response?.data}")
                    if (state.response?.code == 200) {
                        PrefManager.put(state.response.data?.subscription, "SUBSCRIPTION-DATA")
                        checkVisibility()
                    }
                }
            }
        })
    }

    private fun setObserverCancel() {
        //handling error event in snack bar
        viewModel.liveDataCancel.observe(viewLifecycleOwner, Observer { state ->
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

                is ResponseHandler.OnSuccessResponse<ResponseData<CancelSubResponse>?> -> {
                    hideProgressBar()
                    Log.d("ViewTripFragment", "setObserverData: ${state.response?.data}")
                    if (state.response?.code == 200) {
                        PrefManager.put(state.response.data?.subscription, "SUBSCRIPTION-DATA")
                        checkVisibility()
                    }
                }
            }
        })
    }

}