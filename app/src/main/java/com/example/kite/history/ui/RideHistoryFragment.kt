package com.example.kite.history.ui

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
import com.example.kite.history.lis.OnRideClick
import com.example.kite.history.model.RideHistoryRequest
import com.example.kite.history.model.RideHistoryResponse
import com.example.kite.history.viewmodel.RideHistoryViewModel
import com.example.kite.login.model.LoginResponse
import com.example.kite.utils.PrefManager

class RideHistoryFragment : BaseFragment(), OnRideClick {

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
        setAdapter()
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getApiRequest()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRideObserver()

    }

    //toolbar
    private fun setUPToolbar() {
        binding.inHistoryBar.txtToolbarHeader.setText(R.string.ride_history)
        binding.inHistoryBar.imgBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setAdapter() {
        adapter = RideHistoryAdapter(this)
        binding.rvRideHistory.adapter = adapter
    }

    private fun getViewModel(): RideHistoryViewModel {
        viewModel = ViewModelProvider(this)[RideHistoryViewModel::class.java]
        return viewModel
    }

    private fun getApiRequest() {
        viewModel = getViewModel()

        //getting data for request class
        val token = PrefManager.get<LoginResponse>("LOGIN_RESPONSE")?.accessToken
        val cusID = PrefManager.get<LoginResponse>("LOGIN_RESPONSE")?.customerId

        viewModel.getHistoryRequest(
            RideHistoryRequest(
                access_token = token,
                customer_id = cusID
            )
        )
    }

    private fun setRideObserver() {
        viewModel.responseLiveData.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                is ResponseHandler.Loading -> {
                    showProgressDialog()
                    Log.d("RideHistoryFragment", "setObserverData: $state")
                }
                is ResponseHandler.OnFailed -> {
                    hideProgressBar()
                    Log.d("RideHistoryFragment", "setObserverData: $state")
                }
                is ResponseHandler.OnSuccessResponse<ResponseData<RideHistoryResponse>?> -> {
                    Log.d("RideHistoryFragment", "setObserverData: ${state.response?.data}")
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

    override fun onClick(bookingID: String) {
        val bundle = Bundle()
        bundle.putString("BookingID", bookingID)
        findNavController().navigate(R.id.action_rideHistoryFragment_to_rideDetailsFragment, bundle)
    }
}