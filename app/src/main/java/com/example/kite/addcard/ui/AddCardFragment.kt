package com.example.kite.addcard.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.kite.R
import com.example.kite.addcard.model.AddCardRequest
import com.example.kite.addcard.model.AddCardResponse
import com.example.kite.addcard.viewmodel.AddCardViewModel
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.ResponseData
import com.example.kite.basefragment.BaseFragment
import com.example.kite.constants.Constants
import com.example.kite.databinding.DialogDatePickerBinding
import com.example.kite.databinding.FragmentAddCardBinding
import com.example.kite.extensions.DialogExtensions
import com.example.kite.login.model.LoginResponse
import com.example.kite.utils.PrefManager
import com.example.kite.extensions.onTextChanged
import com.stripe.android.ApiResultCallback
import com.stripe.android.Stripe
import com.stripe.android.model.CardParams
import com.stripe.android.model.Token
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class AddCardFragment : BaseFragment() {
    private lateinit var binding: FragmentAddCardBinding

    @Inject
    lateinit var viewModel: AddCardViewModel

    private var stripe: Stripe? = null
    private var isDefault = 1


    /*
    * init the stripe with api key
    * */
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
            checkValidation()
        }

        return binding.root
    }

    private fun checkValidation() {
        if (binding.edtCardHolderName.text.toString().isEmpty()) {
            Toast.makeText(requireContext(), getString(R.string.lbl_enter_name), Toast.LENGTH_SHORT)
                .show()
        } else if (binding.edtCardNumber.text.toString().isEmpty()) {
            Toast.makeText(
                requireContext(),
                getString(R.string.lbl_enter_card_number),
                Toast.LENGTH_SHORT
            ).show()
        } else if (binding.edtCardNumber.text.toString().length != 16) {
            Toast.makeText(
                requireContext(),
                getString(R.string.lbl_enter_valid_card_number),
                Toast.LENGTH_SHORT
            ).show()
        } else if (binding.edtYear.text.toString().isEmpty()) {
            Toast.makeText(
                requireContext(),
                getString(R.string.lbl_select_year),
                Toast.LENGTH_SHORT
            ).show()
        } else if (binding.edtMonth.text.toString().isEmpty()) {
            Toast.makeText(
                requireContext(),
                getString(R.string.lbl_select_month),
                Toast.LENGTH_SHORT
            ).show()
        } else if (binding.edtCVV.text.toString().isEmpty()) {
            Toast.makeText(requireContext(), getString(R.string.lbl_enter_cvv), Toast.LENGTH_SHORT)
                .show()
        } else if (binding.edtCVV.text.toString().length != 3) {
            Toast.makeText(
                requireContext(),
                getString(R.string.lbl_enter_valid_cvv),
                Toast.LENGTH_SHORT
            ).show()
        } else {
            addCard()
        }
    }

    /*
    * getting the required param for card
    * */
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


    /*
    * checking the validation with stripe of entered card data
    * */
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

    /*
    * saving the new card in to the api
    * */
    private fun saveNewCard(
        tokenID: String, stripeCardId: String?,
        last4: String?, displayName: String?
    ) {
        // viewModel = getViewModel()

        val token = PrefManager.get<LoginResponse>("LOGIN_RESPONSE")
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
            setNumberPickerDialog(2023, 2045, binding.edtYear)
        }
        binding.edtMonth.setOnClickListener {
            if (binding.edtYear.text?.isEmpty() == true) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.lbl_select_year_first),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                setNumberPickerDialog(1, 12, binding.edtMonth)
            }
        }

        /*
        * checking the priority of card with button
        * @param isDefault is for card type
        * making the priority card
        * */
        var isChecked = true
        binding.isButtonClick = isChecked
        binding.btnPriority.setOnClickListener {
            if (isChecked) {
                isChecked = false
                isDefault = 0
            } else {
                isDefault = 1
                isChecked = true
            }
            binding.isButtonClick = isChecked
        }
    }

    /*
    * custom dialog for CVV info
    * */
    private fun showCVVDialog() {
        DialogExtensions.showDialog(
            requireContext(), R.layout.dialog_cvv, R.id.imgCloseDialog, R.id.btnGotIt
        ).show()
    }

    /*
    * common dialog for year and month of card expiry
    * */
    private fun setNumberPickerDialog(min: Int, max: Int, editText: EditText) {
        val builder = AlertDialog.Builder(requireContext())
        val bind: DialogDatePickerBinding =
            DialogDatePickerBinding.inflate(LayoutInflater.from(context))
        bind.numberPicker.apply {
            minValue = min
            maxValue = max
            wrapSelectorWheel = true
        }
        builder.apply {
            setPositiveButton("Ok") { dialog, _ ->
                editText.apply {
                    setText(bind.numberPicker.value.toString())
                }
                dialog.cancel()
            }
            setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
            setView(view)
            create()
            show()
        }
    }


    /*
    * checking entered card type visa or master
    * */
    private fun checkCardType() {
        binding.edtCardNumber.onTextChanged {
            if (Constants.VISA.matcher(it).matches()) {
                binding.isVisa = true
            } else if (Constants.MASTER.matcher(it).matches()) {
                binding.isMaster = true
            } else {
                binding.isVisa = false
                binding.isMaster = false
            }
        }
    }

    /* *//*
    * init view model for add card api
    * *//*
    private fun getViewModel(): AddCardViewModel {
        viewModel = ViewModelProvider(this)[AddCardViewModel::class.java]
        return viewModel
    }*/

    /*
    * setting up the observer for add card api
    * */
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