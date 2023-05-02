package com.example.kite.basefragment

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.kite.MainActivity
import com.example.kite.R
import com.example.kite.base.ViewModelBase
import com.example.kite.databinding.FragmentBaseBinding
import com.example.kite.extensions.hideKeyboard
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
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

    /*
       * method to make snack bar
       * */
    private fun showSnackBar(message: String) {
        val snackBar = Snackbar.make(
            (activity as MainActivity).findViewById(android.R.id.content)!!,
            message,
            Snackbar.LENGTH_SHORT
        )
        val view = snackBar.view
        val snackTV = view.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        snackTV.maxLines = 5
        snackBar.setBackgroundTint(Color.RED)
        snackBar.show()
    }

    /*
     * creating method for showing snack bar
     * */
    fun setUpSnackBar(viewModel: ViewModelBase) {
        viewModel.getSnakeBarMessage().observe(viewLifecycleOwner) { o: Any ->
            if (o is Int) {
                hideKeyboard()
                (activity as MainActivity).resources?.getString(o)?.let { showSnackBar(it) }!!
            } else if (o is String) {
                hideKeyboard()
                showSnackBar(o)
            }
        }
    }
}