package com.example.kite.scanqr

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.budiyev.android.codescanner.*
import com.example.kite.R
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.ResponseData
import com.example.kite.basefragment.BaseFragment
import com.example.kite.databinding.DialogBottomQrHelperBinding
import com.example.kite.databinding.DialogQrOpenBinding
import com.example.kite.databinding.FragmentScanQRCodeBinding
import com.example.kite.login.model.LoginResponse
import com.example.kite.scanqr.model.ScanQRRequest
import com.example.kite.scanqr.model.ScanQRResponse
import com.example.kite.scanqr.viewmodel.ScanQRViewModel
import com.example.kite.utils.PrefManager


class ScanQRCodeFragment : BaseFragment() {

    private lateinit var binding: FragmentScanQRCodeBinding
    private lateinit var codeScanner: CodeScanner
    private lateinit var viewModel: ScanQRViewModel
    var qrCodeString = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.fragment_scan_q_r_code,
            container,
            false
        )
        setUPToolBar()
        setUPNavigation()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        codeScanner = CodeScanner(requireActivity(), binding.scannerView)
        codeScanner.apply {
            startPreview()
            camera = CodeScanner.CAMERA_BACK // or CAMERA_FRONT or specific camera id
            formats = CodeScanner.ALL_FORMATS // list of type BarcodeFormat,
            // ex. listOf(BarcodeFormat.QR_CODE)
            autoFocusMode = AutoFocusMode.SAFE // or CONTINUOUS
            scanMode = ScanMode.SINGLE // or CONTINUOUS or PREVIEW or SINGLE
            isAutoFocusEnabled = true // Whether to enable auto focus or not
        }
        codeScanner.decodeCallback = DecodeCallback {
            activity?.runOnUiThread {
                qrCodeString = it.text
                getApiData()
                Toast.makeText(
                    requireContext(), "Camera initialization error: ${it.text}",
                    Toast.LENGTH_LONG
                ).show()
                Log.d("TAG", "onViewCreated: ${it.text}")
            }
        }
        codeScanner.errorCallback = ErrorCallback { // or ErrorCallback.SUPPRESS
            activity?.runOnUiThread {
                Toast.makeText(
                    requireContext(), "Camera initialization error: ${it.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
            Log.d("TAG1111", "scanCallBacks: ${it.message}")
        }
        /*binding.scannerView.setOnClickListener {
            codeScanner.startPreview()
        }*/

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUPCarDialog()

    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }

    //setting up toolbar
    private fun setUPToolBar() {
        binding.inScanBar.txtToolbarHeader.apply {
            setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            setText(R.string.scan_qr_code)
        }
        binding.inScanBar.imgBack.setImageResource(R.drawable.ic_back_white)
        binding.inScanBar.imgBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    //code scanner default values
    private fun initCodeScanner() {
        codeScanner = CodeScanner(requireContext(), binding.scannerView)
        codeScanner.apply {
            camera = CodeScanner.CAMERA_BACK // or CAMERA_FRONT or specific camera id
            formats = CodeScanner.ALL_FORMATS // list of type BarcodeFormat,
            // ex. listOf(BarcodeFormat.QR_CODE)
            autoFocusMode = AutoFocusMode.SAFE // or CONTINUOUS
            scanMode = ScanMode.SINGLE // or CONTINUOUS or PREVIEW
            isAutoFocusEnabled = true // Whether to enable auto focus or not
            startPreview()// for preview of scanner
        }

    }

    //callback method for scanner
    private fun scanCallBacks() {
        // Callbacks
        codeScanner.decodeCallback = DecodeCallback {
            /*activity?.runOnUiThread {
                qrCodeString = it.text
                getApiData()
                Toast.makeText(
                    requireContext(), "Camera initialization error: ${it.text}",
                    Toast.LENGTH_LONG
                ).show()
                findNavController().navigateUp()
            }*/
            Log.d("TAG1111", "scanCallBacks: ${it.text}")
        }
        codeScanner.errorCallback = ErrorCallback { // or ErrorCallback.SUPPRESS
            /*activity?.runOnUiThread {
                Toast.makeText(
                    requireContext(), "Camera initialization error: ${it.message}",
                    Toast.LENGTH_LONG
                ).show()
            }*/
            Log.d("TAG1111", "scanCallBacks: ${it.message}")
        }
    }

    //navigation buttons
    private fun setUPNavigation() {
        //enter qr code bottom sheet
        binding.imgQREntry.setOnClickListener {
            findNavController().navigate(R.id.action_scanQRCodeFragment_to_enterQRCodeFragment)
        }
        //flash light
        binding.imgQrFlash.setOnClickListener {
            codeScanner.isFlashEnabled = !codeScanner.isFlashEnabled
        }
    }

    private fun setUPCarDialog() {
        val args = this.arguments
        val vehicleSlug = args?.getString("VehicleSlug")

        if (vehicleSlug == "eCar") {
            val builder = Dialog(requireContext())
            builder.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val bindingDialog: DialogQrOpenBinding =
                DataBindingUtil.inflate(
                    builder.layoutInflater,
                    R.layout.dialog_qr_open,
                    null,
                    false
                )
            bindingDialog.imgClose.setOnClickListener { builder.dismiss() }
            bindingDialog.btnIMReady.setOnClickListener { builder.dismiss() }
            bindingDialog.txtNeedHelp.setOnClickListener {
                openCarDialog()
                builder.dismiss()
            }
            builder.setContentView(bindingDialog.root)
            builder.setCanceledOnTouchOutside(false)
            builder.show()
        }
    }

    private fun openCarDialog() {
        val builder = Dialog(requireContext(), R.style.DialogTheme)
        val bindingDialog: DialogBottomQrHelperBinding =
            DataBindingUtil.inflate(
                builder.layoutInflater,
                R.layout.dialog_bottom_qr_helper,
                null,
                false
            )
        bindingDialog.btnScanQRCode.setOnClickListener { builder.dismiss() }
        bindingDialog.imgBack.setOnClickListener { builder.dismiss() }
        builder.setContentView(bindingDialog.root)
        builder.setCanceledOnTouchOutside(false)
        builder.show()
    }

    private fun getViewModel(): ScanQRViewModel {
        viewModel = ViewModelProvider(this)[ScanQRViewModel::class.java]
        return viewModel
    }

    private fun getApiData() {
        viewModel = getViewModel()
        val token = PrefManager.get<LoginResponse>("LOGIN_RESPONSE")?.data?.accessToken
        viewModel.getScanQRRequest(
            ScanQRRequest(
                access_token = token,
                qr_code = qrCodeString
            )
        )
        setObserver()
    }

    private fun setObserver() {
        viewModel = getViewModel()
        viewModel.liveData.observe(viewLifecycleOwner, Observer { state ->
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
                    Log.d("ViewTripFragment", "setObserverData: ${state.message}")

                }
                is ResponseHandler.OnSuccessResponse<ResponseData<ScanQRResponse>?> -> {
                    Log.d("ViewTripFragment", "setObserverData: ${state.response?.data}")
                    hideProgressBar()
                }
            }
        })
    }

}