package com.example.kite.scanqr.bottomsheet

import android.os.Bundle
import android.util.Log
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
import com.example.kite.databinding.FragmentEnterQRCodeBinding
import com.example.kite.login.model.LoginResponse
import com.example.kite.scanqr.model.ScanQRRequest
import com.example.kite.scanqr.model.ScanQRResponse
import com.example.kite.scanqr.viewmodel.ScanQRViewModel
import com.example.kite.utils.PrefManager

class EnterQRCodeFragment : BaseFragment() {
    private lateinit var binding: FragmentEnterQRCodeBinding
    private lateinit var viewModel: ScanQRViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.fragment_enter_q_r_code,
            container,
            false
        )
        setUPToolBar()
        setValidation()
        return binding.root
    }

    //setting up the toolbar
    private fun setUPToolBar() {
        binding.inEnterQRBar.imgBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.inEnterQRBar.imgBack.setImageResource(R.drawable.ic_close)
        binding.inEnterQRBar.txtToolbarHeader.setText(R.string.enter_access_code)
    }

    //validation on access code feed
    private fun setValidation() {
        binding.btnACUnlockVehicle.setOnClickListener {
            if (binding.edtACCode.text.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "Please enter access code", Toast.LENGTH_SHORT)
                    .show()
            } else {
                getApiData()
            }
        }
    }

    /*
    * getting view model
    * */
    private fun getViewModel(): ScanQRViewModel {
        viewModel = ViewModelProvider(this)[ScanQRViewModel::class.java]
        return viewModel
    }

    /*
    *
    * collecting request class data
    * */
    private fun getApiData() {
        viewModel = getViewModel()
        val token = PrefManager.get<LoginResponse>("LOGIN_RESPONSE")?.data?.accessToken
        val args = this.arguments
        val vehicleSlug = args?.getString("VehicleSlug")

        viewModel.getScanQRRequest(
            ScanQRRequest(
                access_token = token,
                qr_code = binding.edtACCode.text.toString(),
                vehicle_type_slug = "eCar",

                )
        )
        setObserver()
    }

    /*
    * setting up the observables
    * */
    private fun setObserver() {
        viewModel = getViewModel()
        viewModel.liveData.observe(viewLifecycleOwner, Observer { state ->
            if (state == null) {
                return@Observer
            }
            when (state) {
                is ResponseHandler.Loading -> {
                    showProgressDialog()
                    Log.d("ScanQRCode", "setObserverData: $state")
                }
                is ResponseHandler.OnFailed -> {
                    hideProgressBar()
                }

                is ResponseHandler.OnSuccessResponse<ResponseData<ScanQRResponse>?> -> {
                    hideProgressBar()
                    Log.d("ScanQRCode", "setObserverData: ${state.response?.data}")
                    val bundle = Bundle()
                    bundle.putParcelable("ScanResponse", state.response?.data)
                    findNavController().navigate(
                        R.id.action_enterQRCodeFragment_to_paymentSummaryFragment,
                        bundle
                    )
                }
            }
        })
    }

}