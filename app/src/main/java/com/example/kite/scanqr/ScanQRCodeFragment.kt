package com.example.kite.scanqr

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.budiyev.android.codescanner.*
import com.example.kite.R
import com.example.kite.databinding.FragmentScanQRCodeBinding


class ScanQRCodeFragment : Fragment() {

    private lateinit var binding: FragmentScanQRCodeBinding
    private lateinit var codeScanner: CodeScanner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.fragment_scan_q_r_code,
            container,
            false
        )


        initCodeScanner()
        setUPToolBar()
        setUPNavigation()
        scanCallBacks()


        return binding.root
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
            startPreview() // for preview of scanner
            camera = CodeScanner.CAMERA_BACK // or CAMERA_FRONT or specific camera id
            formats = CodeScanner.ALL_FORMATS // list of type BarcodeFormat,
            // ex. listOf(BarcodeFormat.QR_CODE)
            autoFocusMode = AutoFocusMode.SAFE // or CONTINUOUS
            scanMode = ScanMode.SINGLE // or CONTINUOUS or PREVIEW
            isAutoFocusEnabled = true // Whether to enable auto focus or not
        }

    }

    //callback method for scanner
    private fun scanCallBacks() {
        // Callbacks
        codeScanner.decodeCallback = DecodeCallback {
            Log.d("ACCC", "Scan result: ${it.text}")
        }
        codeScanner.errorCallback = ErrorCallback { // or ErrorCallback.SUPPRESS
            Log.d("ACCC", "Camera initialization error: ${it.message}")
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


}