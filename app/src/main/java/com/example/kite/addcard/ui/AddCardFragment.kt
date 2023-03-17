package com.example.kite.addcard.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.NumberPicker
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.adapters.TextViewBindingAdapter.setText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.kite.R
import com.example.kite.addcard.model.AddCardRequest
import com.example.kite.addcard.model.AddCardResponse
import com.example.kite.addcard.viewmodel.AddCardViewModel
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.ResponseData
import com.example.kite.basefragment.BaseFragment
import com.example.kite.constants.Constants
import com.example.kite.databinding.FragmentAddCardBinding
import com.example.kite.login.model.LoginResponse
import com.example.kite.utils.PrefManager
import com.example.kite.utils.onTextChanged
import com.stripe.android.ApiResultCallback
import com.stripe.android.Stripe
import com.stripe.android.model.CardParams
import com.stripe.android.model.Token
import java.util.*


class AddCardFragment : BaseFragment() {
    private lateinit var binding: FragmentAddCardBinding
    private lateinit var viewModel: AddCardViewModel

    private var stripe: Stripe? = null
    private var isDefault = 1


    private fun initStripe() {
        val stripeKey: String = getString(R.string.stripe_pk)
        stripe = Stripe(requireContext(), stripeKey)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initStripe()
    }

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
        checkCardType()

        binding.btnAddCard.setOnClickListener {
            addCard()
        }

        return binding.root
    }

    fun addCard() {
        val cardParams = CardParams(
            binding.edtCardNumber.text.toString(),
            binding.edtMonth.text.toString().toInt(),
            binding.edtYear.text.toString().toInt(),
            binding.edtCVV.text.toString(),
            binding.edtCardHolderName.text.toString()
        )
        addStripeCard(cardParams)
    }


    private fun addStripeCard(cardParams: CardParams) {
        stripe?.createCardToken(cardParams, null, null,
            object : ApiResultCallback<Token> {
                override fun onError(e: Exception) {
                    e.printStackTrace()
                    Log.d("[FAIL] Payment Method: ", e.toString())

                }

                override fun onSuccess(result: Token) {
                    Log.d("[SUCCESS] Payment Method: ", result.toString())
                    saveNewCard(
                        result.id, result.card?.id,
                        result.card?.last4, result.card?.brand?.displayName
                    )
                }
            })
    }

    private fun saveNewCard(
        tokenID: String, stripeCardId: String?,
        last4: String?, displayName: String?
    ) {
        viewModel = getViewModel()

        val token = PrefManager.get<LoginResponse>("LOGIN_RESPONSE")?.data
        val expiryDate = String.format(
            Locale.getDefault(), "%02d",
            Integer.parseInt(binding.edtMonth.text.toString())
        ) + "/" + binding.edtYear.text.toString()


        viewModel.getAddCardRequest(
            AddCardRequest(
                access_token = token?.accessToken,
                stripe_card_id = stripeCardId,
                customer_id = token?.customerId.toString(),
                is_default = isDefault,
                card_token = tokenID,
                card_name = binding.edtCardHolderName.text.toString(),
                card_number = last4,
                card_type = displayName,
                expiry_date = expiryDate

            )
        )
        setObserver()
    }

    //setting up the navigation
    private fun setNavigation() {
        binding.inCardBar.imgBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.inCardBar.txtToolbarHeader.setText(R.string.add_card)
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
        var isChecked = true
        binding.btnPriority.setOnClickListener {
            if (isChecked) {
                isChecked = false
                isDefault = 0
                binding.btnPriority.alpha = 0.3f
                binding.btnPriority.setCompoundDrawablesWithIntrinsicBounds(
                    null,
                    null,
                    ContextCompat.getDrawable(requireContext(), R.drawable.ic_check),
                    null
                )
            } else {
                isDefault = 1
                binding.btnPriority.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
                isChecked = true
                binding.btnPriority.alpha = 1.0f
            }
        }
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

        }
        builder.apply {
            setPositiveButton("Ok") { dialog, _ ->
                binding.edtYear.apply {
                    setText(numberPicker.value.toString())
                }
                dialog.cancel()
            }
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

        }

        builder.apply {
            setPositiveButton("Ok") { dialog, _ ->
                binding.edtMonth.apply {
                    setText(numberPicker.value.toString())
                }
                dialog.cancel()
            }
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

    private fun getViewModel(): AddCardViewModel {
        viewModel = ViewModelProvider(this)[AddCardViewModel::class.java]
        return viewModel
    }


    private fun setObserver() {
        //handling error event in snack bar
        viewModel.liveData.observe(this, Observer { state ->
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
                    Log.d("ViewTripFragment", "setObserverData: $state")

                }
                is ResponseHandler.OnSuccessResponse<ResponseData<AddCardResponse>?> -> {
                    Log.d("ViewTripFragment", "setObserverData: ${state.response?.data}")
                    if (state.response?.code == 200) {
                        findNavController().navigateUp()
                    }
                    hideProgressBar()
                }
            }
        })
    }

}