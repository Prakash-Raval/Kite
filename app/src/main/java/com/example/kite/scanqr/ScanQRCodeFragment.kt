package com.example.kite.scanqr

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
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
    /*
    * variables
    * */

    private lateinit var binding: FragmentScanQRCodeBinding
    private lateinit var codeScanner: CodeScanner
    private lateinit var viewModel: ScanQRViewModel
    private var qrCodeString = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.fragment_scan_q_r_code,
            container,
            false
        )
        /*
        * methods toolbar and back navigation
        * */
        setUPToolBar()
        setUPNavigation()
        /*
        * initializing qr scanner view
        * */
        initCodeScanner()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*
        * call back methods for scanner view
        *
        * */
        scanCallBacks()
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


    /*
    *
    * setting up toolbar
    * */
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

    /*
    * creating code scanner
    * code scanner property and attributes
    *
    * */
    private fun initCodeScanner() {
        codeScanner = CodeScanner(requireContext(), binding.scannerView)
        codeScanner.apply {
            startPreview()// for preview of scanner
            camera = CodeScanner.CAMERA_BACK // or CAMERA_FRONT or specific camera id
            formats = CodeScanner.ALL_FORMATS // list of type BarcodeFormat,
            // ex. listOf(BarcodeFormat.QR_CODE)
            autoFocusMode = AutoFocusMode.SAFE // or CONTINUOUS
            scanMode = ScanMode.SINGLE // or CONTINUOUS or PREVIEW
            isAutoFocusEnabled = true // Whether to enable auto focus or not
        }
    }

    /*
    *
    * call back method for scanner view
    *
    *
    * */
    private fun scanCallBacks() {
        // Callbacks
        codeScanner.decodeCallback = DecodeCallback {
            activity?.runOnUiThread {
                //getting the qr string for api call
                qrCodeString = it.text
                getApiData()
            }
        }
        codeScanner.errorCallback = ErrorCallback { // or ErrorCallback.SUPPRESS
            activity?.runOnUiThread {
                Toast.makeText(
                    requireContext(), "Camera initialization error: ${it.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
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


    /*
    * method for showing dialog
    * if vehicle type is car
    *
    * */
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


    /*
    * if first dialog shown for car
    * second dialog for helping qr scanner
    *
    * */
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
                qr_code = qrCodeString,
                vehicle_type_slug = vehicleSlug,

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
                }
                is ResponseHandler.OnFailed -> {
                    hideProgressBar()
                    Toast.makeText(requireContext(), "Please try again later", Toast.LENGTH_SHORT)
                        .show()
                    findNavController().navigateUp()
                }
                is ResponseHandler.OnSuccessResponse<ResponseData<ScanQRResponse>?> -> {
                    hideProgressBar()
                    if (state.response?.code == 200) {
                        val bundle = Bundle()
                        bundle.putParcelable("ScanResponse", state.response.data)
                        bundle.putString("QRString",qrCodeString)
                        findNavController().navigate(
                            R.id.action_scanQRCodeFragment_to_paymentSummaryFragment,
                            bundle
                        )
                    } else {
                        Toast.makeText(
                            requireContext(),
                            state.response?.message,
                            Toast.LENGTH_LONG
                        )
                            .show()
                        codeScanner.startPreview()
                    }
                }
            }
        })
    }
}