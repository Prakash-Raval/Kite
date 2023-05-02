package com.example.kite.vehicleinspection

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.kite.R
import com.example.kite.databinding.DialogViResendCodeBinding
import com.example.kite.databinding.FragmentVehicleInspectionBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class VehicleInspectionFragment : Fragment() {

    /*
    * variables
    * */
    private lateinit var binding: FragmentVehicleInspectionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.fragment_vehicle_inspection,
            container,
            false
        )
        /*
        *
        * calling methods
        * */
        setUPToolbar()
        setNavigation()
        return binding.root
    }

    /*
    * setting up the navigation for fragments
    * */
    private fun setNavigation() {
        binding.btnVILSubmit.setOnClickListener {
            setDialog()
        }
        binding.btnVIUseCamera.setOnClickListener {

        }
    }

    /*
    * setting up the toolbar
    * */
    private fun setUPToolbar() {
        binding.inVIBar.imgBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.inVIBar.txtToolbarHeader.setText(R.string.vehicle_inspection)
    }

    /*
    * setting up dialog for confirmation
    * */
    private fun setDialog() {
        val builder = Dialog(requireContext())
        builder.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val bindingDialog: DialogViResendCodeBinding =
            DataBindingUtil.inflate(
                builder.layoutInflater,
                R.layout.dialog_vi_resend_code,
                null,
                false
            )

        bindingDialog.imgCloseDialog.setOnClickListener { builder.dismiss() }
        bindingDialog.txtDResendCode.setOnClickListener { builder.dismiss() }
        bindingDialog.btnGotIt.setOnClickListener { }
        builder.setContentView(bindingDialog.root)
        builder.setCanceledOnTouchOutside(false)
        builder.show()
    }

}