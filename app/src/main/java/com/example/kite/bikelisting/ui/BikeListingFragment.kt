package com.example.kite.bikelisting.ui

import android.content.Context
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
import com.example.kite.bikelisting.model.BikeListingRequest
import com.example.kite.bikelisting.model.BikeListingResponse
import com.example.kite.bikelisting.viewmodel.BikeListingViewModel
import com.example.kite.constants.Constants
import com.example.kite.constants.GenericAdapter
import com.example.kite.databinding.FragmentBikeListingBinding
import com.example.kite.databinding.ItemVehicleListingBinding
import com.example.kite.extensions.DateAndTime
import com.example.kite.login.model.LoginResponse
import com.example.kite.utils.PrefManager


class BikeListingFragment : BaseFragment() {

    /*
    * variables
    * */
    private lateinit var binding: FragmentBikeListingBinding
    private lateinit var viewModel: BikeListingViewModel
    val bundle = Bundle()
    var vehicleList = ArrayList<BikeListingResponse.VehicleDetail>()

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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setNavigation()
        getData()
    }


    /*
    * setting the navigation for toolbar
    * */
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
                bundle
            )
        }
    }

    /*
    *
    * init view model for vehicle listing
    * */
    private fun getViewModel(): BikeListingViewModel {
        viewModel = ViewModelProvider(this)[BikeListingViewModel::class.java]
        return viewModel
    }

    /*
    *
    * setting up the adapter for recyclerview
    * */
    private fun setAdapter() {
        var selected = -1
        val vehicleAdapter = object :
            GenericAdapter<BikeListingResponse.VehicleDetail, ItemVehicleListingBinding>(
                requireContext(),
                vehicleList
            ) {
            override val layoutResId: Int
                get() = R.layout.item_vehicle_listing

            override fun onBindData(
                model: BikeListingResponse.VehicleDetail,
                position: Int,
                dataBinding: ItemVehicleListingBinding
            ) {
                dataBinding.data = model
                dataBinding.executePendingBindings()
                dataBinding.isSelected = selected == position
            }

            override fun onItemClick(model: BikeListingResponse.VehicleDetail, position: Int) {
                selected = position
                notifyMyAdapter()
                selectVehicle(position)
            }
        }
        binding.rvListVehicleContainer.adapter = vehicleAdapter
    }

    /*
    * checking vehicle slug and passing related data to next fragment
    * */
    private fun selectVehicle(position: Int) {
        val bind = binding.model?.vehicleDetails?.get(position)
        val vehicleSlug = bind?.vehicleTypeSlug
        val availableVehicle = bind?.availableVehicles

        if (position != -1) {
            if (availableVehicle == 0) {
                binding.isSelected = false
                binding.isSelectedText = false
            }
            when (vehicleSlug) {
                Constants.SCOOTER -> {
                    if (availableVehicle != null && availableVehicle > 0) {
                        binding.isSelected = true
                        binding.isSelectedText = false
                        bundle.putParcelable("VehicleDetails", bind)
                    }
                }
                Constants.CAR -> {
                    if (availableVehicle != null && availableVehicle > 0) {
                        binding.isSelected = true
                        binding.isSelectedText = true
                        bundle.putParcelable("VehicleDetails", bind)
                    }
                }
                Constants.BIKE -> {
                    if (availableVehicle != null && availableVehicle > 0) {
                        binding.isSelected = true
                        binding.isSelectedText = false
                        bundle.putParcelable("VehicleDetails", bind)
                    }
                }
                else -> {
                    binding.isSelected = false
                    binding.isSelectedText = false

                }
            }
        }
    }


    /*
    *
    * */
    private fun getData() {
        viewModel = getViewModel()
        val token = PrefManager.get<LoginResponse>("LOGIN_RESPONSE")?.accessToken
        val sharedPreferences =
            activity?.getSharedPreferences("THIRD_PARTY_ID", Context.MODE_PRIVATE)
        val thirdPartyID = sharedPreferences?.getString("ThirdPartyID", "ThirdPartyID")
        viewModel.getAddCardRequest(
            BikeListingRequest(
                access_token = token,
                DateAndTime.currentDate,
                DateAndTime.currentTime,
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
                    // adapter.setList(state.response?.data?.vehicleDetails as ArrayList<BikeListingResponse.VehicleDetail>)
                    PrefManager.put(state.response?.data?.vehicleDetails, "VEHICLE_RESPONSE")
                    vehicleList =
                        state.response?.data?.vehicleDetails as ArrayList<BikeListingResponse.VehicleDetail>
                    setAdapter()
                }
            }
        })
    }


}