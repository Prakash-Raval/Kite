package com.example.kite.bikelisting.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.kite.R
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.ResponseData
import com.example.kite.basefragment.BaseFragment
import com.example.kite.bikelisting.adapter.Adapter
import com.example.kite.bikelisting.adapter.OnCellClicked
import com.example.kite.bikelisting.model.BikeListingRequest
import com.example.kite.bikelisting.model.BikeListingResponse
import com.example.kite.bikelisting.viewmodel.BikeListingViewModel
import com.example.kite.databinding.FragmentBikeListingBinding
import com.example.kite.login.model.LoginResponse
import com.example.kite.utils.PrefManager
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class BikeListingFragment : BaseFragment(), OnCellClicked {
    private lateinit var binding: FragmentBikeListingBinding
    private lateinit var viewModel: BikeListingViewModel
    private lateinit var adapter: Adapter
    val bundle = Bundle()
    private val vehicleDetails = Bundle()

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

    private fun getViewModel(): BikeListingViewModel {
        viewModel = ViewModelProvider(this)[BikeListingViewModel::class.java]
        return viewModel
    }

    private fun setAdapter() {
        adapter = Adapter(this)
        binding.rvListVehicleContainer.adapter = adapter
    }

    @SuppressLint("NotifyDataSetChanged")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun getData() {
        viewModel = getViewModel()

        val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")
        val currentDate = LocalDateTime.now().format(dateFormatter)
        val currentTime = LocalDateTime.now().format(timeFormatter)
        val token = PrefManager.get<LoginResponse>("LOGIN_RESPONSE")?.data?.accessToken
        val sharedPreferences =
            activity?.getSharedPreferences("THIRD_PARTY_ID", Context.MODE_PRIVATE)
        val thirdPartyID = sharedPreferences?.getString("ThirdPartyID", "ThirdPartyID")
        viewModel.getAddCardRequest(
            BikeListingRequest(
                access_token = token,
                currentDate,
                currentTime,
                BikeListingRequest.UserLocation(),
                third_party_id = thirdPartyID
            )
        )
        setObserver()
    }

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
                is ResponseHandler.OnSuccessResponse<ResponseData<BikeListingResponse>?> -> {
                    hideProgressBar()
                    Log.d("ViewTripFragment", "setObserverData: ${state.response?.data}")
                    binding.model = state.response?.data
                    adapter.setList(state.response?.data?.vehicleDetails as ArrayList<BikeListingResponse.VehicleDetail>)
                    adapter.notifyDataSetChanged()
                    PrefManager.put(state.response.data?.vehicleDetails, "VEHICLE_RESPONSE")

                }
            }
        })
    }

    //interface for clicking item on recyclerview
    override fun isClicked(data: Int) {
        val bind = binding.model?.vehicleDetails?.get(data)
        val vehicleSlug = bind?.vehicleTypeSlug
        val availableVehicle = bind?.availableVehicles

        val modelName = bind?.vehicleType
        val modelImage = bind?.vehicleTypeImage
        val repeatCount = bind?.repeatDaysCount
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
                        vehicleDetails.putString("RepeatCount", repeatCount.toString())
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
                        vehicleDetails.putString("RepeatCount", repeatCount.toString())
                        vehicleDetails.putString("vehicleTypeID", vehicleTypeID.toString())
                        vehicleDetails.putString("ManufacturerID", manufacturerID.toString())
                    }
                }
                "eBike" -> {
                    if (availableVehicle != null && availableVehicle > 0) {
                        enableButton()
                        disableText()
                        vehicleDetails.putString("RepeatCount", repeatCount.toString())
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