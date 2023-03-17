package com.example.kite.history

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
import com.example.kite.databinding.FragmentRideHistoryBinding
import com.example.kite.history.adapter.RideHistoryAdapter
import com.example.kite.history.model.RideHistoryRequest
import com.example.kite.history.model.RideHistoryResponse
import com.example.kite.history.viewmodel.RideHistoryViewModel
import com.example.kite.login.model.LoginResponse
import com.example.kite.utils.PrefManager

class RideHistoryFragment : BaseFragment() {

    private lateinit var binding: FragmentRideHistoryBinding
    private lateinit var viewModel: RideHistoryViewModel
    private lateinit var adapter: RideHistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.fragment_ride_history,
            container,
            false
        )
        setUPToolbar()
        getApiRequest()
        setRideObserver()
        setAdapter()
        return binding.root
    }

    //toolbar
    private fun setUPToolbar() {
        binding.inHistoryBar.txtToolbarHeader.setText(R.string.ride_history)
        binding.inHistoryBar.imgBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setAdapter() {
        adapter = RideHistoryAdapter()
        binding.rvRideHistory.adapter = adapter
    }

    private fun getViewModel(): RideHistoryViewModel {
        viewModel = ViewModelProvider(this)[RideHistoryViewModel::class.java]
        return viewModel
    }

    private fun getApiRequest() {
        viewModel = getViewModel()

        //getting data for request class
        val token = PrefManager.get<LoginResponse>("LOGIN_RESPONSE")?.data?.accessToken
        val cusID = PrefManager.get<LoginResponse>("LOGIN_RESPONSE")?.data?.customerId

        viewModel.getHistoryRequest(
            RideHistoryRequest(
                access_token = token,
                customer_id = cusID
            )
        )
    }

    private fun setRideObserver() {
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
                is ResponseHandler.OnSuccessResponse<ResponseData<RideHistoryResponse>?> -> {
                    Log.d("ViewTripFragment", "setObserverData: ${state.response?.data}")
                    adapter.setList(state.response?.data?.rideHistory as ArrayList<RideHistoryResponse.RideHistory>)
                    adapter.notifyDataSetChanged()
                    binding.rvRideHistory.visibility = View.VISIBLE
                    binding.txtRHTripText.visibility = View.VISIBLE
                    binding.centerText.visibility = View.GONE
                    hideProgressBar()
                }
            }
        })
    }
}