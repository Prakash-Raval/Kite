package com.example.kite.addcard.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.NumberPicker
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.kite.R
import com.example.kite.addcard.repository.AddCardRepository
import com.example.kite.addcard.viewmodel.AddCardVMFFactory
import com.example.kite.addcard.viewmodel.AddCardViewModel
import com.example.kite.constants.Constants
import com.example.kite.databinding.FragmentAddCardBinding
import com.example.kite.network.ApiInterface
import com.example.kite.network.RetrofitHelper
import com.example.kite.utils.onTextChanged

class AddCardFragment : Fragment() {
    private lateinit var binding: FragmentAddCardBinding
    private lateinit var viewModel: AddCardViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.fragment_add_card,
            container,
            false
        )
        setNavigation()
        addCardData()
        checkCardType()
        return binding.root
    }

    //setting up the navigation
    private fun setNavigation() {
        binding.imgBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.imgError.setOnClickListener {
            showCVVDialog()
        }
        binding.edtYear.setOnClickListener {
            selectYearDialog()
        }
        binding.edtMonth.setOnClickListener {
            if (binding.edtYear.text?.isEmpty() == true) {
                Toast.makeText(requireContext(), "Select Year First", Toast.LENGTH_SHORT).show()
            } else {
                selectMonthDialog()
            }
        }
    }

    //getting data from api
    private fun addCardData() {
        val service =
            RetrofitHelper.getInstance(Constants.BASE_URL).create(ApiInterface::class.java)
        val repository = AddCardRepository(service)
        viewModel =
            ViewModelProvider(this, AddCardVMFFactory(repository))[AddCardViewModel::class.java]

        //binding variable from xml
        binding.addCardData = viewModel
        binding.lifecycleOwner = this
    }

    //creating custom dialog to show CVV info
    private fun showCVVDialog() {
        val builder = AlertDialog.Builder(requireContext())
            .create()
        builder.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val view = layoutInflater.inflate(R.layout.dialog_cvv, null)
        builder.setView(view)
        val close = view.findViewById<ImageView>(R.id.imgCloseDialog)
        val btnClose: Button = view.findViewById(R.id.btnGotIt)

        btnClose.setOnClickListener {
            builder.dismiss()
        }
        close.setOnClickListener {
            builder.dismiss()
        }
        builder.setCanceledOnTouchOutside(false)
        builder.show()
    }

    //dialog to select expiry year
    @SuppressLint("SetTextI18n", "InflateParams")
    private fun selectYearDialog() {
        val builder = AlertDialog.Builder(requireContext())
        val view = layoutInflater.inflate(R.layout.dialog_date_picker, null)
        val numberPicker = view.findViewById<NumberPicker>(R.id.numberPicker)
        numberPicker.apply {
            minValue = 2023
            maxValue = 2045
            wrapSelectorWheel = true
            setOnValueChangedListener { picker, _, _ ->
                binding.edtYear.apply {
                    setText(picker.value.toString())
                }
            }
        }
        builder.apply {
            setPositiveButton("Ok") { dialog, _ -> dialog.cancel() }
            setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
            setView(view)
            create()
            show()
        }
    }

    //dialog to select expiry Month
    @SuppressLint("InflateParams")
    private fun selectMonthDialog() {
        val builder = AlertDialog.Builder(requireContext())
        val view = layoutInflater.inflate(R.layout.dialog_date_picker, null)
        val numberPicker = view.findViewById<NumberPicker>(R.id.numberPicker)
        numberPicker.apply {
            minValue = 1
            maxValue = 12
            wrapSelectorWheel = true
            setOnValueChangedListener { picker, _, _ ->
                binding.edtMonth.apply {
                    setText(picker.value.toString())
                }
            }
        }

        builder.apply {
            setPositiveButton("Ok") { dialog, _ -> dialog.cancel() }
            setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
            setView(view)
            create()
            show()
        }
    }

    //checking entered card type
    private fun checkCardType() {
        binding.edtCardNumber.onTextChanged {
            if (Constants.VISA.matcher(it).matches()) {
                binding.visaCard.apply {
                    strokeWidth = 5
                    strokeColor = ContextCompat.getColor(context, R.color.bg_main)
                }
            } else if (Constants.MASTER.matcher(it).matches()) {
                binding.masterCard.apply {
                    strokeWidth = 5
                    strokeColor = ContextCompat.getColor(context, R.color.bg_main)
                }
            } else {
                binding.visaCard.apply {
                    strokeWidth = 0
                    strokeColor = ContextCompat.getColor(context, R.color.white)
                }
                binding.masterCard.apply {
                    strokeWidth = 0
                    strokeColor = ContextCompat.getColor(context, R.color.white)
                }
            }
        }
    }


}