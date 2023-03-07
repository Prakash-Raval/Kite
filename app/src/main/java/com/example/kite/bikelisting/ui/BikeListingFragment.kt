package com.example.kite.bikelisting.ui

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.kite.R
import com.example.kite.bikelisting.adapter.Adapter
import com.example.kite.bikelisting.adapter.OnCellClicked
import com.example.kite.bikelisting.model.BikeListingRequest
import com.example.kite.bikelisting.model.BikeListingResponse
import com.example.kite.bikelisting.repository.BikeListingRepository
import com.example.kite.bikelisting.viewmodel.BLVMFactory
import com.example.kite.bikelisting.viewmodel.BikeListingViewModel
import com.example.kite.constants.Constants
import com.example.kite.databinding.FragmentBikeListingBinding
import com.example.kite.login.model.LoginResponse
import com.example.kite.network.ApiInterface
import com.example.kite.network.RetrofitHelper
import com.example.kite.utils.PrefManager
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class BikeListingFragment : Fragment(), OnCellClicked {
    private lateinit var binding: FragmentBikeListingBinding
    private lateinit var viewModel: BikeListingViewModel
    private lateinit var adapter: Adapter
    val bundle = Bundle()
    val vehicleDetails = Bundle()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.fragment_bike_listing,
            container,
            false
        )
        setNavigation()
        setAdapter()
        getData()
        return binding.root
    }

    private fun setNavigation() {
        binding.imgBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnRideNow.setOnClickListener {
            findNavController().navigate(
                R.id.action_bikeListingFragment_to_scanQRCodeFragment,
                bundle
            )
        }

        binding.txtScheduleTrip.setOnClickListener {
            findNavController().navigate(
                R.id.action_bikeListingFragment_to_scheduleTripFragment,
                vehicleDetails
            )
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun setAdapter() {
        adapter = Adapter(requireContext(), this)
        binding.rvListVehicleContainer.adapter = adapter
    }

    @SuppressLint("NotifyDataSetChanged")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun getData() {
        val service =
            RetrofitHelper.getInstance(Constants.BASE_URL).create(ApiInterface::class.java)
        val repository = BikeListingRepository(service)
        viewModel = ViewModelProvider(
            this, BLVMFactory(repository)
        )[BikeListingViewModel::class.java]

        viewModel.bikeListingLD.observe(viewLifecycleOwner) {
            binding.model = it
            adapter.setList(it.data?.vehicleDetails as ArrayList<BikeListingResponse.Data.VehicleDetail>)
            adapter.notifyDataSetChanged()

            PrefManager.put(it?.data?.vehicleDetails, "VEHICLE_RESPONSE")
        }
        binding.lifecycleOwner = viewLifecycleOwner

        val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")
        val currentDate = LocalDateTime.now().format(dateFormatter)
        val currentTime = LocalDateTime.now().format(timeFormatter)
        val token = PrefManager.get<LoginResponse>("LOGIN_RESPONSE")?.data?.accessToken

        viewModel.getRequiredData(
            BikeListingRequest(
                token,
                currentDate,
                currentTime,
                BikeListingRequest.UserLocation()
            )
        )
    }

    //interface for clicking item on recyclerview
    override fun isClicked(data: Int) {
        val bind = binding.model?.data?.vehicleDetails?.get(data)
        val vehicleSlug = bind?.vehicleTypeSlug
        val availableVehicle = bind?.availableVehicles

        val modelName = bind?.vehicleType
        val modelImage = bind?.vehicleTypeImage

        val vehicleTypeID = bind?.vehicleTypeId
        val manufacturerID = bind?.manufacturerId


        if (data != -1) {

            if (availableVehicle == 0) {
                disableButton()
                disableText()
            }
            when (vehicleSlug) {

                "eScooter" -> {
                    if (availableVehicle != null && availableVehicle > 0) {
                        enableButton()
                        disableText()

                        bundle.putString("VehicleSlug", vehicleSlug)
                        vehicleDetails.putString("ModelName", modelName)
                        vehicleDetails.putString("ModelImage", modelImage)

                        vehicleDetails.putString("vehicleTypeID", vehicleTypeID.toString())
                        vehicleDetails.putString("ManufacturerID", manufacturerID.toString())
                    }
                }
                "eCar" -> {
                    if (availableVehicle != null && availableVehicle > 0) {
                        enableButton()
                        enableText()
                        bundle.putString("VehicleSlug", vehicleSlug)
                        vehicleDetails.putString("ModelName", modelName)
                        vehicleDetails.putString("ModelImage", modelImage)
                        vehicleDetails.putString("vehicleTypeID", vehicleTypeID.toString())
                        vehicleDetails.putString("ManufacturerID", manufacturerID.toString())
                    }
                }
                "eBike" -> {
                    if (availableVehicle != null && availableVehicle > 0) {
                        enableButton()
                        disableText()
                        bundle.putString("VehicleSlug", vehicleSlug)
                        vehicleDetails.putString("ModelName", modelName)
                        vehicleDetails.putString("ModelImage", modelImage)
                        vehicleDetails.putString("vehicleTypeID", vehicleTypeID.toString())
                        vehicleDetails.putString("ManufacturerID", manufacturerID.toString())
                    }
                }
                else -> {

                    disableButton()
                    disableText()
                }
            }


        }
    }

    private fun enableButton() {
        binding.btnRideNow.isEnabled = true
        binding.btnRideNow.setBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.black
            )
        )
        binding.btnRideNow.alpha = 1.0f

    }

    private fun disableButton() {
        binding.btnRideNow.isEnabled = false
        binding.btnRideNow.alpha = 0.2f

    }

    private fun enableText() {
        binding.txtScheduleTrip.isClickable = true
        binding.txtScheduleTrip.alpha = 1.0f
        binding.txtScheduleTrip.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.black
            )
        )
        binding.txtScheduleTrip.compoundDrawables[2]
            .setTint(ContextCompat.getColor(requireContext(), R.color.black))
    }

    private fun disableText() {
        binding.txtScheduleTrip.isClickable = false
        binding.txtScheduleTrip.alpha = 0.2f
        binding.txtScheduleTrip.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.black
            )
        )
        binding.txtScheduleTrip.compoundDrawables[2]
            .setTint(ContextCompat.getColor(requireContext(), R.color.black))
    }
}