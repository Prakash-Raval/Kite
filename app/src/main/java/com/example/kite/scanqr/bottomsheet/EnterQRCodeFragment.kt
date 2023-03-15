package com.example.kite.scanqr.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.kite.R
import com.example.kite.databinding.FragmentEnterQRCodeBinding
import com.example.kite.utils.onTextChanged

class EnterQRCodeFragment : Fragment() {
    private lateinit var binding: FragmentEnterQRCodeBinding

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
            }
        }
    }

    fun setButtonColor() {
        binding.edtACCode.onTextChanged {
            binding.btnACUnlockVehicle.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.black
                )
            )
        }
    }

}