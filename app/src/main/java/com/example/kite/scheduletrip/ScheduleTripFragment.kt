package com.example.kite.scheduletrip

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.kite.R
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.ResponseData
import com.example.kite.basefragment.BaseFragment
import com.example.kite.databinding.FragmentScheduleTripBinding
import com.example.kite.databinding.ItemBsRepeatCountBinding
import com.example.kite.dateandtime.listner.GetDateAndTime
import com.example.kite.dateandtime.ui.DateAndTimeFragment
import com.example.kite.extensions.DateAndTime
import com.example.kite.extensions.snackError
import com.example.kite.login.model.LoginResponse
import com.example.kite.scheduletrip.adapter.ScheduleTripAdapter
import com.example.kite.scheduletrip.listner.OnTripClick
import com.example.kite.scheduletrip.model.*
import com.example.kite.scheduletrip.viewmodel.CancelTripViewModel
import com.example.kite.scheduletrip.viewmodel.ScheduleTripViewModel
import com.example.kite.scheduletrip.viewmodel.TripViewModel
import com.example.kite.scheduletrip.viewmodel.UpdateTripViewModel
import com.example.kite.utils.PrefManager
import com.example.kite.viewscheduletrip.model.ViewTripResponse
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ScheduleTripFragment : BaseFragment(), GetDateAndTime, OnTripClick {

    private lateinit var binding: FragmentScheduleTripBinding

    @Inject
    lateinit var viewModelTrip: TripViewModel
    @Inject
    lateinit var viewModel: ScheduleTripViewModel
    @Inject
    lateinit var updateTripViewModel: UpdateTripViewModel
    @Inject
    lateinit var cancelTripViewModel: CancelTripViewModel
    private lateinit var scheduleTripAdapter: ScheduleTripAdapter


    private var list = ArrayList<ScheduleTimeDuration>()

    private var count = 0
    private val bundle2 = Bundle()
    private val bundle = Bundle()

    private var selectedDate: String = ""
    private var selectedTime: String = ""
    private var selectedTimeZone = " "
    private var selectedTimeZoneString = ""
    private var vehicleReservationPricingId = -1
    private var duration = ""

    private var isUpdate = false

    val token = PrefManager.get<LoginResponse>("LOGIN_RESPONSE")?.accessToken

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.fragment_schedule_trip, container, false
        )
        setUPToolbar()
        setUPAdapter()
        getApiData()
        setUPUpdateApiCall()
        setCounter()
        getUpdateData()
        cancelTrip()
        return binding.root
    }


    private fun setUPCancelDialog() {
        val builder: android.app.AlertDialog.Builder =
            android.app.AlertDialog.Builder(requireContext())
        builder.setTitle(R.string.before_you_go)
        builder.setMessage(R.string.cancel_dialog_trip_text)
            .setCancelable(false)
            .setPositiveButton("YES") { _, _ ->
                getCancelTripData()
            }
            .setNegativeButton("NOT YET") { dialog, _ ->
                dialog.dismiss()
            }
        val alert: android.app.AlertDialog? = builder.create()
        alert?.show()
    }

    private fun cancelTripSuccess() {
        val builder: android.app.AlertDialog.Builder =
            android.app.AlertDialog.Builder(requireContext())
        builder.setTitle(R.string.app_name)
        builder.setMessage(R.string.reservation_cancel_success)
            .setCancelable(false)
            .setPositiveButton("Ok") { _, _ ->
                findNavController().navigate(ScheduleTripFragmentDirections.actionScheduleTripFragmentToHomeFragment())
            }

        val alert: android.app.AlertDialog? = builder.create()
        alert?.show()
    }

    /*
    * manage visibility of date and time text view
    * setting data to date and time text view
    * */
    private fun setDateAndTime() {
        if (selectedDate == "") {
            binding.imgDownDate.visibility = View.VISIBLE
            binding.txtSTDateSelect.visibility = View.GONE
        } else {
            binding.txtSTDateSelect.text = selectedDate
            binding.imgDownDate.visibility = View.GONE
            binding.txtSTDateSelect.visibility = View.VISIBLE
        }

        if (selectedTime == "") {
            binding.imgDownTime.visibility = View.VISIBLE
            binding.txtSTTimeSelected.visibility = View.GONE
        } else {
            binding.imgDownTime.visibility = View.GONE
            binding.txtSTTimeSelected.visibility = View.VISIBLE
            binding.txtSTTimeSelected.text = selectedTime
        }

        binding.btnSTScheduleTrip.setOnClickListener {
            checkTripValidation()
        }
    }

    /*
    * set up toolbar
    */
    private fun setUPToolbar() {
        binding.imgSTBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.txtRepeatDialog.setOnClickListener {
            setUPBottomSheet()
        }
        val dialog = DateAndTimeFragment(requireContext(), this)

        binding.imgDownDate.setOnClickListener {
            dialog.arguments = bundle2
            dialog.show(requireActivity().supportFragmentManager, "")
        }

        binding.txtSTDateSelect.setOnClickListener {
            dialog.arguments = bundle2
            dialog.show(requireActivity().supportFragmentManager, "")
        }

        binding.txtSTTimeSelected.setOnClickListener {
            if (binding.txtSTDateSelect.isVisible) {
                dialog.show(requireActivity().supportFragmentManager, "")
            } else {
                Toast.makeText(requireContext(), "Please select date", Toast.LENGTH_SHORT).show()
            }
        }
        binding.imgDownTime.setOnClickListener {
            if (binding.txtSTDateSelect.isVisible) {
                dialog.show(requireActivity().supportFragmentManager, "")
            } else {
                Toast.makeText(requireContext(), "Please select date", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setData() {
        val args = this.arguments
        val modelName = args?.getString("ModelName")
        val modelImage = args?.getString("ModelImage")
        binding.txtSTVehicleName.text = modelName
        Glide.with(requireContext()).load(modelImage).into(binding.imgVehicleImage)
    }

    /*
    * setting counter variable and it's limit
    * */
    private fun setCounter() {
        val args = this.arguments
        val repeatCount = args?.getString("RepeatCount")
        binding.txtCountRepeat.text = count.toString()

        //removing counting
        binding.imgRepeatRemove.setOnClickListener {
            if (count != 0) count--
            binding.txtCountRepeat.text = count.toString()
        }

        //adding counting
        binding.imgRepeatADD.setOnClickListener {
            if (repeatCount?.toInt()!! > count)
                count++
            binding.txtCountRepeat.text = count.toString()
        }
    }

    /*
    * bottom sheet dialog for repeat count
    * */
    private fun setUPBottomSheet() {
        val args = this.arguments
        val repeatCount = args?.getString("RepeatCount")
        val dialog = BottomSheetDialog(requireContext())
        val bind: ItemBsRepeatCountBinding =
            ItemBsRepeatCountBinding.inflate(LayoutInflater.from(context))
        bind.imgBSClose.setOnClickListener {
            binding.txtCountRepeat.text = bind.txtBSCounter.text
            dialog.dismiss()
        }
        bind.txtBSCounter.text = binding.txtCountRepeat.text
        bind.btnBSSave.setOnClickListener {
            binding.txtCountRepeat.text = bind.txtBSCounter.text
            dialog.dismiss()
        }


        bind.imgBSAdd.setOnClickListener {
            if (repeatCount?.toInt()!! > count)
                count++
            bind.txtBSCounter.text = count.toString()
        }

        bind.imgBSRemove.setOnClickListener {
            if (count != 0) count--
            bind.txtBSCounter.text = count.toString()
        }

        dialog.apply {
            setCancelable(false)
            setContentView(bind.root)
            show()
        }

    }

    /*
    * setting the data to second adapter for hour duration
    * */
    private fun setUPAdapter() {
        scheduleTripAdapter = ScheduleTripAdapter(requireContext(), this)
        list.add(ScheduleTimeDuration("1 hr", R.drawable.one_hour_duration))
        list.add(ScheduleTimeDuration("2 hr", R.drawable.two_hour_duration))
        list.add(ScheduleTimeDuration("4 hr", R.drawable.four_hour_duration))
        list.add(ScheduleTimeDuration("8 hr", R.drawable.eight_hour_duration))
        list.add(ScheduleTimeDuration("24 hr", R.drawable.twenty_four_hour_duration))
        binding.rvSTDuration.adapter = scheduleTripAdapter
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun getApiData() {

        val args = this.arguments
        val vehicleTypeID = args?.getString("vehicleTypeID")
        val manufacturerID = args?.getString("ManufacturerID")
        PrefManager.put(manufacturerID, "MID")
        PrefManager.put(vehicleTypeID, "TypeID")
        val thirdPartyID = PrefManager.get<String>("ThirdPartyID")


        viewModel.getScheduleTripData(
            ScheduleTripRequest(
                access_token = token,
                vehicle_type_id = vehicleTypeID,
                manufacturer_id = manufacturerID,
                third_party_id = thirdPartyID
            )
        )

        viewModel.responseLiveData.observe(viewLifecycleOwner, Observer { state ->
            if (state == null) {
                return@Observer

            }
            when (state) {
                is ResponseHandler.Loading -> {
                    showProgressDialog()
                    Log.d("TripCodeResponse1", "setLiveDataObservers: $state")
                }
                is ResponseHandler.OnFailed -> {
                    hideProgressBar()
                    Log.d("TripCodeResponse2", "setLiveDataObservers: $state")
                }
                is ResponseHandler.OnSuccessResponse<ResponseData<ScheduleTripResponse>?> -> {
                    hideProgressBar()
                    if (state.response?.code == 200) {
                        //passing data to bundle
                        bundle2.putParcelable("ScheduleResponse", state.response.data)
                        vehicleReservationPricingId =
                            state.response.data?.vehicleReservationPricingId!!
                        bundle.putString("CancelCharge", state.response.data?.updationCharge)
                        //array adapter list
                        scheduleTripAdapter.setList(
                            state.response.data?.tripDuration as ArrayList<ScheduleTripResponse.TripDuration>,
                            list
                        )
                        scheduleTripAdapter.notifyDataSetChanged()

                    } else {
                        Toast.makeText(
                            requireContext(),
                            "${state.response?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    Log.d("TripCodeResponse3", "setLiveDataObservers: ${state.response?.data}")
                }
            }
        })

    }

    override fun getDate(date: String, time: String, timeZone: String, timeZoneText: String) {
        selectedDate = date
        selectedTime = time
        selectedTimeZone = timeZone
        selectedTimeZoneString = timeZoneText

        bundle2.putString("ScheduleDate", selectedDate)
        bundle2.putString("ScheduleTime", selectedTime)
        bundle2.putString("ScheduleTimezone", selectedTimeZone)
        setDateAndTime()
    }

    private fun checkTripValidation() {
        if (binding.txtSTDateSelect.text == "") {
            binding.txtSTDateSelect.snackError("Please select date")
        } else if (binding.txtSTTimeSelected.text == "") {
            binding.txtSTTimeSelected.snackError("Please select time")
        } else {
            addTripApiCall()
        }

    }


    private fun addTripApiCall() {

        //getting request data

        val subscribe =
            PrefManager.get<LoginResponse>("LOGIN_RESPONSE")?.subscription?.isSubscribe
        val customerID = PrefManager.get<LoginResponse>("LOGIN_RESPONSE")?.customerId
        val args = this.arguments
        val vehicleTypeID = args?.getString("vehicleTypeID")
        val manufacturerID = args?.getString("ManufacturerID")
        val thirdPartyID = PrefManager.get<String>("ThirdPartyID")
        //passing request data
        viewModelTrip.getTripRequest(
            TripRequest(
                accessToken = token,
                isSubscribed = subscribe.toString(),
                manufacturerId = manufacturerID,
                vehicleTypeId = vehicleTypeID,
                vehicleReservationPricingId = vehicleReservationPricingId,
                timeZone = selectedTimeZoneString,
                startDate = selectedDate,
                startTime = selectedTime,
                recurringDayCount = binding.txtCountRepeat.text.toString(),
                reservationCustomerId = customerID,
                promocodeId = "",
                duration = duration,
                thirdPartyId = thirdPartyID
            )
        )


        viewModelTrip.responseLiveData.observe(this, Observer { state ->
            if (state == null) {
                return@Observer

            }
            when (state) {
                is ResponseHandler.Loading -> {
                    showProgressDialog()
                    Log.d("TripCodeResponse1", "setLiveDataObservers: $state")
                }
                is ResponseHandler.OnFailed -> {
                    hideProgressBar()
                    Log.d("TripCodeResponse2", "setLiveDataObservers: $state")
                }
                is ResponseHandler.OnSuccessResponse<ResponseData<TripResponse>?> -> {
                    if (state.response?.code == 200) {
                        bundle.putBoolean("UpCharge", true)
                        findNavController().navigate(
                            R.id.action_scheduleTripFragment_to_homeFragment,
                            bundle
                        )
                        hideProgressBar()
                    }
                    Log.d("TripCodeResponse3", "setLiveDataObservers: ${state.response?.data}")
                }
            }
        })
    }

    override fun onClickTrip(data: String) {
        duration = data
    }


    private fun getUpdateData() {

        val args = this.arguments
        val viewTripResponse = args?.getParcelable<ViewTripResponse>("ViewTripResponse")

        isUpdate = args?.getBoolean("UpdateTrip") == true
        binding.updateTripData = viewTripResponse
        binding.visible = isUpdate


        if (isUpdate) {
            //pass data for time slot api call
            PrefManager.put(viewTripResponse?.vehicleTypeId.toString(), "TypeID")
            PrefManager.put(viewTripResponse?.manufacturerId.toString(), "MID")
            bundle2.putParcelable("ScheduleResponse", viewTripResponse)

            binding.txtSTTRip.setText(R.string.update_your_trip)
            val thirdPartyID = PrefManager.get<String>("ThirdPartyID")
            //call for schedule trip time duration api call
            binding.txtSTVehicleName.text = viewTripResponse?.vehicleType
            viewModel.getScheduleTripData(
                ScheduleTripRequest(
                    access_token = token,
                    vehicle_type_id = viewTripResponse?.vehicleTypeId.toString(),
                    manufacturer_id = viewTripResponse?.manufacturerId.toString(),
                    third_party_id = thirdPartyID
                )
            )
        } else {
            setData()
        }
    }


    private fun setUPUpdateApiCall() {
        binding.btnSTConfirm.setOnClickListener {
            updateApiCall()
        }
        setObserverUpdate()
    }

    private fun updateApiCall() {
        val args = this.arguments
        val customerId = PrefManager.get<LoginResponse>("LOGIN_RESPONSE")?.customerId
        val viewTripResponse = args?.getParcelable<ViewTripResponse>("ViewTripResponse")

        updateTripViewModel.getTripRequest(
            UpdateTripRequest(
                accessToken = token,
                startDate = selectedDate,
                startTime = selectedTime,
                reservationCustomerId = customerId.toString(),
                reservationId = viewTripResponse?.reservationId.toString(),
                duration = duration,
                forcefullyUpdate = 0,
                timeZone = selectedTimeZoneString
            )
        )
    }


    private fun setObserverUpdate() {
        //observing view model response data
        updateTripViewModel.liveData.observe(viewLifecycleOwner, Observer { state ->
            if (state == null) {
                return@Observer
            }
            when (state) {
                is ResponseHandler.Loading -> {
                    showProgressDialog()
                    Log.d("ScheduleTripFragmentUpdate", "setObserverData: $state")
                }
                is ResponseHandler.OnFailed -> {
                    hideProgressBar()
                    Log.d("ScheduleTripFragmentUpdate", "setObserverData: $state")

                }
                is ResponseHandler.OnSuccessResponse<ResponseData<UpdateTripResponse>?> -> {
                    hideProgressBar()
                    findNavController().navigate(R.id.action_scheduleTripFragment_to_homeFragment)
                    Log.d("ScheduleTripFragmentUpdate", "setObserverData: ${state.response?.data}")
                }
            }
        })
    }


    private fun cancelTrip() {
        binding.btnSTCancelTrip.setOnClickListener {
            setUPCancelDialog()
        }
    }

    private fun getCancelTripData() {
        val args = this.arguments
        val viewTripResponse = args?.getParcelable<ViewTripResponse>("ViewTripResponse")

        cancelTripViewModel.getCancelTripRequest(
            CancelTripRequest(
                cancel_fee = viewTripResponse?.cancelFee,
                access_token = token,
                reservation_id = viewTripResponse?.reservationId.toString(),
                current_date = DateAndTime.currentDate,
                current_time = DateAndTime.currentTime,
                forcefully_cancel = "0",
                time_zone = viewTripResponse?.timeZone,
            )
        )
        setObserverCancelTrip()
    }

    private fun setObserverCancelTrip() {
        //observing view model response data
        cancelTripViewModel.liveData.observe(viewLifecycleOwner, Observer { state ->
            if (state == null) {
                return@Observer
            }
            when (state) {
                is ResponseHandler.Loading -> {
                    showProgressDialog()
                    Log.d("ScheduleTripFragmentUpdate", "setObserverData: $state")
                }
                is ResponseHandler.OnFailed -> {
                    hideProgressBar()
                    Log.d("ScheduleTripFragmentUpdate", "setObserverData: $state")

                }
                is ResponseHandler.OnSuccessResponse<ResponseData<CancelTripResponse>?> -> {
                    hideProgressBar()
                    cancelTripSuccess()
                    Log.d("ScheduleTripFragmentUpdate", "setObserverData: ${state.response?.data}")
                }
            }
        })
    }
}