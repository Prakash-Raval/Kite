package com.example.kite.selectpayment.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.kite.R
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.ResponseData
import com.example.kite.basefragment.BaseFragment
import com.example.kite.constants.GenericAdapter
import com.example.kite.databinding.FragmentSelectPaymentBinding
import com.example.kite.databinding.ItemCardDesignBinding
import com.example.kite.login.model.LoginResponse
import com.example.kite.selectpayment.model.GetCardRequest
import com.example.kite.selectpayment.model.GetCardResponse
import com.example.kite.selectpayment.viewmodel.GetCardViewModel
import com.example.kite.utils.PrefManager


class SelectPaymentFragment : BaseFragment() {
    private lateinit var binding: FragmentSelectPaymentBinding
    private lateinit var viewModel: GetCardViewModel
    var cardList = ArrayList<GetCardResponse.Detail>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.fragment_select_payment,
            container,
            false
        )
        setUpNavigation()
        getApiData()
        return binding.root
    }

    //setting recycler view adapter
    private fun setUPAdapter() {
        val cardAdapter = object :
            GenericAdapter<GetCardResponse.Detail, ItemCardDesignBinding>(
                requireContext(),
                cardList
            ) {
            override val layoutResId: Int
                get() = R.layout.item_card_design

            override fun onBindData(
                model: GetCardResponse.Detail,
                position: Int,
                dataBinding: ItemCardDesignBinding
            ) {
                dataBinding.getCard = model
                dataBinding.executePendingBindings()
            }

            override fun onItemClick(model: GetCardResponse.Detail, position: Int) {

            }

        }
        binding.rvGetCard.adapter = cardAdapter
    }

    //setting up the toolbar
    private fun setUpNavigation() {
        binding.txtAddCard.setOnClickListener {
            findNavController().navigate(SelectPaymentFragmentDirections.actionSelectPaymentFragmentToAddCardFragment())
        }
        binding.inSelectPaymentBar.imgBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.inSelectPaymentBar.txtToolbarHeader.setText(R.string.select_payment)
    }

    //getting the view model
    private fun getViewModel(): GetCardViewModel {
        viewModel = ViewModelProvider(this)[GetCardViewModel::class.java]
        return viewModel
    }

    //collecting request data
    private fun getApiData() {
        viewModel = getViewModel()
        //get request data
        val token = PrefManager.get<LoginResponse>("LOGIN_RESPONSE")

        viewModel.getGetCardRequest(
            GetCardRequest(
                access_token = token?.accessToken,
                customer_id = token?.customerId.toString()
            )
        )
        setObserver()
    }

    //setting up the observers
    private fun setObserver() {
        //handling error event in snack bar
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
                    Log.d("ViewTripFragment", "setObserverData: $state")

                }
                is ResponseHandler.OnSuccessResponse<ResponseData<GetCardResponse>?> -> {
                    Log.d("ViewTripFragment", "setObserverData: ${state.response?.data}")
                    hideProgressBar()
                    binding.isSelectedCard = state.response?.data?.details == null
                    cardList = state.response?.data?.details as ArrayList<GetCardResponse.Detail>
                    setUPAdapter()
                }
            }
        })
    }
}