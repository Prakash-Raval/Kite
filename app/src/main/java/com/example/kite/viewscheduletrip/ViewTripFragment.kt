package com.example.kite.viewscheduletrip

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.kite.R
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.ResponseData
import com.example.kite.basefragment.BaseFragment
import com.example.kite.databinding.FragmentViewTripBinding
import com.example.kite.login.model.LoginResponse
import com.example.kite.utils.PrefManager
import com.example.kite.viewscheduletrip.model.ViewTripRequest
import com.example.kite.viewscheduletrip.model.ViewTripResponse
import com.example.kite.viewscheduletrip.viewmodel.ViewTripDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint

class ViewTripFragment : BaseFragment() {

    private lateinit var binding: FragmentViewTripBinding

    @Inject
    lateinit var viewModel: ViewTripDetailsViewModel
    val bundle = Bundle()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_view_trip, container, false)
        requestApiData()
        setObserverData()
        backNavigation()
        return binding.root
    }

    /*
    * setting up the navigation
    * */
    private fun backNavigation() {
        binding.imgVTBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnVTUpdateTrip.setOnClickListener {
            val action = R.id.action_viewTripFragment_to_scheduleTripFragment2
            findNavController().navigate(action, bundle)
        }
    }


    //getting data from home fragment
    private fun requestApiData() {
        val args = this.arguments
        val token = PrefManager.get<LoginResponse>("LOGIN_RESPONSE")?.accessToken
        val reservationID = args?.getString("ReservationID")

        viewModel.getTripRequest(
            ViewTripRequest(
                access_token = token,
                reservation_id = reservationID
            )
        )
    }

    private fun setObserverData() {
        //observing view model response data
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
                is ResponseHandler.OnSuccessResponse<ResponseData<ViewTripResponse>?> -> {
                    hideProgressBar()
                    if (state.response?.code == 200) {
                        bundle.putBoolean("UpdateTrip", true)
                        bundle.putParcelable("ViewTripResponse", state.response.data)
                        binding.viewTripResponse = state.response.data
                    }
                    Log.d("ViewTripFragment", "setObserverData: ${state.response?.data}")

                }
            }
        })
    }
}