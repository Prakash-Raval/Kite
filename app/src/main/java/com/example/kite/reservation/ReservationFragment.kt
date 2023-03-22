package com.example.kite.reservation

import android.os.Bundle
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
import com.example.kite.databinding.FragmentReservationBinding
import com.example.kite.home.viewmodel.ViewTripViewModel
import com.example.kite.login.model.LoginResponse
import com.example.kite.reservation.adapter.ReservationAdapter
import com.example.kite.reservation.listner.OnReservationViewClick
import com.example.kite.reservation.model.ListReservationRequest
import com.example.kite.reservation.model.ListReservationResponse
import com.example.kite.utils.PrefManager
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ReservationFragment : BaseFragment(), OnReservationViewClick {

    private lateinit var binding: FragmentReservationBinding
    private lateinit var viewModel: ViewTripViewModel
    private lateinit var adapter: ReservationAdapter

    private var reservationId = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.fragment_reservation, container, false
        )
        setUPAdapter()
        getData()
        navigation()
        setObserver()
        return binding.root
    }

    private fun setUPAdapter() {
        adapter = ReservationAdapter(this)
        binding.rvReservationData.adapter = adapter
    }

    private fun getViewModel(): ViewTripViewModel {
        viewModel = ViewModelProvider(this)[ViewTripViewModel::class.java]
        return viewModel
    }

    private fun navigation() {
        binding.reservationBar.imgBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.reservationBar.txtToolbarHeader.setText(R.string.reservation)
        binding.btnScheduleTrip.setOnClickListener {
            findNavController().navigate(R.id.bikeListingFragment)
        }
    }

    //getting api data
    private fun getData() {

        viewModel = getViewModel()

        //getting required data to generate response
        val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")
        val currentDate = LocalDateTime.now().format(dateFormatter)
        val currentTime = LocalDateTime.now().format(timeFormatter)
        val token = PrefManager.get<LoginResponse>("LOGIN_RESPONSE")

        if (token != null) {
            viewModel.getViewTripRequest(
                ListReservationRequest(
                    token.data?.accessToken,
                    currentDate,
                    currentTime,
                    currentDate,
                    currentTime
                )
            )
        }

    }

    private fun viewTripDetails() {
        val action = R.id.action_reservationFragment_to_viewTripFragment
        val bundle = Bundle()
        bundle.putString("ReservationID", reservationId)
        findNavController().navigate(action, bundle)

    }

    private fun setObserver() {
        //observing view model response data
        viewModel.liveData.observe(viewLifecycleOwner, Observer { state ->
            if (state == null) {
                return@Observer
            }
            when (state) {
                is ResponseHandler.Loading -> {
                    showProgressDialog()
                }
                is ResponseHandler.OnFailed -> {
                    hideProgressBar()
                }
                is ResponseHandler.OnSuccessResponse<ResponseData<ListReservationResponse>?> -> {
                    if (state.response?.data?.reservationData?.size == 0) {
                        binding.txtBooking1.visibility = View.VISIBLE
                        binding.rvReservationData.visibility = View.GONE
                    } else {

                        binding.txtBooking1.visibility = View.GONE
                        binding.rvReservationData.visibility = View.VISIBLE
                    }
                    hideProgressBar()
                    adapter.setList(state.response?.data?.reservationData as ArrayList<ListReservationResponse.ReservationData>)
                    adapter.notifyDataSetChanged()
                }
            }
        })
    }

    override fun onclick(reservationID: String) {
        reservationId = reservationID
        viewTripDetails()
    }
}
