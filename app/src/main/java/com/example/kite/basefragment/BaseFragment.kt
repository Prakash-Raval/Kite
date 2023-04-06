package com.example.kite.basefragment

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.kite.MainActivity
import com.example.kite.R
import com.example.kite.databinding.FragmentBaseBinding

open class BaseFragment : Fragment() {
    private lateinit var binding: FragmentBaseBinding
    private lateinit var mProgressDialog: Dialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.fragment_base,
            container,
            false
        )
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mProgressDialog = Dialog(requireContext())
    }

    fun showProgressDialog() {
        mProgressDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        mProgressDialog.setContentView(R.layout.dialog_progressbar)
        mProgressDialog.setCancelable(false)
        mProgressDialog.show()
    }

    fun hideProgressBar() {
        mProgressDialog.dismiss()
    }


}