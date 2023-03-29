package com.example.kite.paymentsummary

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.util.Log
import android.view.Display.Mode
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.kite.R
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.ResponseData
import com.example.kite.basefragment.BaseFragment
import com.example.kite.constants.Constants
import com.example.kite.databinding.FragmentPaymentSummaryBinding
import com.example.kite.dateandtime.model.PromoCodeResponse
import com.example.kite.dateandtime.viewmodel.PromoCodeViewModel
import com.example.kite.login.model.LoginResponse
import com.example.kite.paymentsummary.model.AddSessionResponse
import com.example.kite.paymentsummary.viewmodel.AddSessionViewModel
import com.example.kite.scanqr.model.ScanQRResponse
import com.example.kite.utils.PrefManager
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody


class PaymentSummaryFragment : BaseFragment() {

    /*
    * variables
    * */
    private lateinit var binding: FragmentPaymentSummaryBinding
    private lateinit var viewModelPromoCode: PromoCodeViewModel
    val token = PrefManager.get<LoginResponse>("LOGIN_RESPONSE")?.data?.accessToken
    private lateinit var viewModelSession: AddSessionViewModel
    val bundle = Bundle()


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
        /*
        * methods calls
        * */
        getArgs()
        setUPToolbar()
        applyPromoCode()
        return binding.root
    }

    /*
    * setting toolbar
    * */
    private fun setUPToolbar() {
        binding.paymentSummaryBar.txtToolbarHeader.setText(R.string.payment_summary)
        binding.paymentSummaryBar.imgBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    /*
    * getting response of scan qr
    * to display data in page
    * changes in args according to vehicles
    * */
    private fun getArgs() {
        val args = this.arguments
        val responseData: ScanQRResponse = args?.get("ScanResponse") as ScanQRResponse
        val qrString = args.getString("QRString")

        val sh = activity?.getSharedPreferences("PaymentResponse",MODE_PRIVATE)?.edit()
        binding.paymentData = responseData

        sh?.putString("QRString", qrString)
        binding.btnPSNext.setOnClickListener {
            when (responseData.vehicleTypeSlug) {
                Constants.CAR -> {
                    getStartSessionData()
                    sh?.putString("VehicleImage", responseData.vehicleTypeImage)
                    sh?.putString("PricingValue", responseData.pricingValue)
                }
                Constants.BIKE -> {
                    getStartSessionData()
                    sh?.putString("VehicleImage", responseData.vehicleTypeImage)
                    sh?.putString("PricingValue", responseData.vehicleType)
                }
                Constants.SCOOTER -> {
                    getStartSessionData()
                    sh?.putString("VehicleImage", responseData.vehicleTypeImage)
                    sh?.putString("PricingValue", responseData.vehicleType)
                }
                else -> {

                }
            }
            sh?.apply()
        }
    }

    /*
    * apply prom code
    * */
    private fun applyPromoCode() {
        binding.btnPSApply.setOnClickListener {
            if (binding.edtPSPromoCode.text.isNullOrEmpty()) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.please_enter_promo_code),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                setObserversPromoCode()
            }
        }
    }

    /*
    * view model for promo code api
    * */

    private fun getViewModel(): PromoCodeViewModel {
        viewModelPromoCode = ViewModelProvider(this)[PromoCodeViewModel::class.java]
        return viewModelPromoCode
    }

    /*
    * api call and observer for promo code
    * */
    private fun setObserversPromoCode() {
        viewModelPromoCode = getViewModel()
        binding.viewModelPromoCode = viewModelPromoCode

        if (token != null) {
            viewModelPromoCode.checkPromoCode(token = token)
        }

        viewModelPromoCode.responseLiveData.observe(viewLifecycleOwner, Observer { state ->
            if (state == null) {
                return@Observer
            }
            when (state) {
                is ResponseHandler.Loading -> {
                    showProgressDialog()
                    Log.d("PromoCodeResponse", "setLiveDataObservers: $state")
                }
                is ResponseHandler.OnFailed -> {
                    hideProgressBar()
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.lbl_invalid_promocode),
                        Toast.LENGTH_SHORT
                    ).show()

                    Log.d("PromoCodeResponse", "setLiveDataObservers: $state")
                }
                is ResponseHandler.OnSuccessResponse<ResponseData<PromoCodeResponse>?> -> {
                    hideProgressBar()
                    Toast.makeText(requireContext(), state.response?.message, Toast.LENGTH_SHORT)
                        .show()
                    Log.d("PromoCodeResponse", "setLiveDataObservers: $state")
                }
            }
        })
    }

    /*
    * view model for starting session
    * */

    private fun getViewModelSession(): AddSessionViewModel {
        viewModelSession = ViewModelProvider(this)[AddSessionViewModel::class.java]
        return viewModelSession
    }


    private fun getStartSessionData() {
        viewModelSession = getViewModelSession()

        val args = this.arguments
        val responseData: ScanQRResponse = args?.get("ScanResponse") as ScanQRResponse
        val accessToken =
            token?.toRequestBody("text/plain".toMediaTypeOrNull())
        val bookingId =
            "${responseData.bookingId}".toRequestBody("text/plain".toMediaTypeOrNull())
        val promoCodeID =
            "".toRequestBody("text/plain".toMediaTypeOrNull())

        viewModelSession.getAddCardRequest(
            access_token = accessToken,
            booking_id = bookingId,
            promoCode_id = promoCodeID,
            battery = "40".toRequestBody("text/plain".toMediaTypeOrNull())

        )
        setObserverSession()
    }

    /*
    * setting up observer for session
    *
    * */
    private fun setObserverSession() {
        viewModelSession.liveData.observe(viewLifecycleOwner, Observer { state ->
            if (state == null) {
                return@Observer
            }
            when (state) {
                is ResponseHandler.Loading -> {
                    showProgressDialog()
                    Log.d("ADDTRIPRESPONSE", "setLiveDataObservers: $state")
                }
                is ResponseHandler.OnFailed -> {
                    hideProgressBar()
                    Log.d("ADDTRIPRESPONSE", "setLiveDataObservers: ${state}")
                }
                is ResponseHandler.OnSuccessResponse<ResponseData<AddSessionResponse>?> -> {
                    hideProgressBar()
                    if (state.response?.code == 200)
                        findNavController().navigate(
                            R.id.action_paymentSummaryFragment_to_homeFragment,
                            bundle
                        )
                    Log.d("ADDTRIPRESPONSE", "setLiveDataObservers: $state")
                }
            }
        })
    }
}