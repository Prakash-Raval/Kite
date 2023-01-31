package com.example.kite.reservation

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.kite.R
import com.example.kite.constants.Constants
import com.example.kite.databinding.FragmentReservationBinding
import com.example.kite.login.model.LoginResponse
import com.example.kite.network.ApiInterface
import com.example.kite.network.RetrofitHelper
import com.example.kite.reservation.model.ListReservationRequest
import com.example.kite.reservation.repository.ListReservationRepository
import com.example.kite.reservation.viewmodel.LRVMFactory
import com.example.kite.reservation.viewmodel.ListReservationViewModel
import com.example.kite.utils.PrefManager
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ReservationFragment : Fragment() {

    private lateinit var binding: FragmentReservationBinding
    private lateinit var viewModel: ListReservationViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.fragment_reservation,
            container,
            false
        )
        getData()
        navigation()
        return binding.root
    }

    private fun navigation() {
        binding.imgBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.btnScheduleTrip.setOnClickListener {
            findNavController().navigate(R.id.bikeListingFragment)
        }
    }

    //getting api data
    @RequiresApi(Build.VERSION_CODES.O)
    private fun getData() {
        val service =
            RetrofitHelper.getInstance(Constants.BASE_URL).create(ApiInterface::class.java)
        val repository = ListReservationRepository(service)
        viewModel =
            ViewModelProvider(this, LRVMFactory(repository))[ListReservationViewModel::class.java]

        //getting required data to generate response
        val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")
        val currentDate = LocalDateTime.now().format(dateFormatter)
        val currentTime = LocalDateTime.now().format(timeFormatter)
        val token = PrefManager.get<LoginResponse>("LOGIN_RESPONSE")

        if (token != null) {
            viewModel.getRequestData(
                ListReservationRequest(
                    token.data?.accessToken,
                    currentDate,
                    currentTime,
                    1,
                    0,
                    currentDate,
                    currentTime
                )
            )
        }
    }
}