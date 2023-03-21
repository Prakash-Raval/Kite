package com.example.kite.scheduletrip

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.kite.R
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.ResponseData
import com.example.kite.basefragment.BaseFragment
import com.example.kite.constants.Constants
import com.example.kite.databinding.FragmentScheduleTripBinding
import com.example.kite.dateandtime.listner.GetDateAndTime
import com.example.kite.dateandtime.ui.DateAndTimeFragment
import com.example.kite.extensions.snackError
import com.example.kite.login.model.LoginResponse
import com.example.kite.network.ApiInterface
import com.example.kite.network.RetrofitHelper
import com.example.kite.scheduletrip.adapter.ScheduleTripAdapter
import com.example.kite.scheduletrip.listner.OnTripClick
import com.example.kite.scheduletrip.model.*
import com.example.kite.scheduletrip.repository.ScheduleTripRepository
import com.example.kite.scheduletrip.viewmodel.*
import com.example.kite.setting.SettingFragmentDirections
import com.example.kite.utils.PrefManager
import com.example.kite.viewscheduletrip.model.ViewTripResponse
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ScheduleTripFragment : BaseFragment(), GetDateAndTime, OnTripClick {

    private lateinit var binding: FragmentScheduleTripBinding

    private lateinit var viewModelTrip: TripViewModel
    private lateinit var viewModel: ScheduleTripViewModel
    private lateinit var scheduleTripAdapter: ScheduleTripAdapter
    private lateinit var updateTripViewModel: UpdateTripViewModel
    private lateinit var cancelTripViewModel: CancelTripViewModel

    var list = ArrayList<ScheduleTimeDuration>()

    private var count = 0
    private val bundle2 = Bundle()
    private val bundle = Bundle()

    var selectedDate: String = ""
    var selectedTime: String = ""
    var selectedTimeZone = " "
    var selectedTimeZoneString = ""
    var vehicleReservationPricingId = -1
    var duration = ""

    var isUpdate = false

    val token = PrefManager.get<LoginResponse>("LOGIN_RESPONSE")?.data?.accessToken

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

    //set up toolbar
    private fun setUPToolbar() {
        binding.imgSTBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.txtRepeatDialog.setOnClickListener {
            setUPBottomSheet()
        }

        binding.imgDownDate.setOnClickListener {
            val dialog = DateAndTimeFragment(requireContext(), this)
            dialog.arguments = bundle2
            Log.d("TAG113", "setUPToolbar: $bundle2")
            dialog.show(requireActivity().supportFragmentManager, "")
        }

        binding.txtSTDateSelect.setOnClickListener {
            val dialog = DateAndTimeFragment(requireContext(), this)
            dialog.arguments = bundle2
            Log.d("TAG112", "setUPToolbar: $bundle2")
            dialog.show(requireActivity().supportFragmentManager, "")
        }

        binding.txtSTTimeSelected.setOnClickListener {
            if (binding.txtSTDateSelect.isVisible) {
                val dialog = DateAndTimeFragment(requireContext(), this)
                dialog.show(requireActivity().supportFragmentManager, "")
            } else {
                Toast.makeText(requireContext(), "Please select date", Toast.LENGTH_SHORT).show()
            }
        }
        binding.imgDownTime.setOnClickListener {
            if (binding.txtSTDateSelect.isVisible) {
                val dialog = DateAndTimeFragment(requireContext(), this)
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

    //setting counter variable and it's limit
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

    private fun setUPBottomSheet() {
        val args = this.arguments
        val repeatCount = args?.getString("RepeatCount")
        val dialog = BottomSheetDialog(requireContext())
        val view = layoutInflater.inflate(R.layout.item_bs_repeat_count, null)
        val countTextView = view.findViewById<TextView>(R.id.txtBSCounter)
        view.findViewById<ImageView>(R.id.imgBSClose).setOnClickListener {
            binding.txtCountRepeat.text = countTextView.text
            dialog.dismiss()
        }
        countTextView.text = binding.txtCountRepeat.text

        view.findViewById<MaterialButton>(R.id.btnBSSave).setOnClickListener {
            binding.txtCountRepeat.text = countTextView.text
            dialog.dismiss()
        }


        view.findViewById<ImageView>(R.id.imgBSAdd).setOnClickListener {
            if (repeatCount?.toInt()!! > count)
                count++
            countTextView.text = count.toString()
        }

        view.findViewById<ImageView>(R.id.imgBSRemove).setOnClickListener {
            if (count != 0) count--
            countTextView.text = count.toString()
        }

        dialog.apply {
            setCancelable(false)
            setContentView(view)
            show()
        }

    }

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
        val service =
            RetrofitHelper.getInstance(Constants.BASE_URL).create(ApiInterface::class.java)
        val repository = ScheduleTripRepository(service)
        viewModel = ViewModelProvider(
            this, STVMFactory(repository)
        )[ScheduleTripViewModel::class.java]

        viewModel.scheduleTripLD.observe(viewLifecycleOwner) {
            Log.d("SCHEDULE-TRIP-RESPONSE", it.toString())
            if (it.code == 200) {

                //passing data to bundle
                bundle2.putParcelable("ScheduleResponse", it)
                vehicleReservationPricingId = it.data?.vehicleReservationPricingId!!
                bundle.putString("CancelCharge", it.data?.updationCharge)
                //array adapter list
                scheduleTripAdapter.setList(
                    it.data?.tripDuration as ArrayList<ScheduleTripResponse.Data.TripDuration>,
                    list
                )
                scheduleTripAdapter.notifyDataSetChanged()

            } else {
                Toast.makeText(requireContext(), "${it.message}", Toast.LENGTH_SHORT).show()
            }
        }


        val args = this.arguments
        val vehicleTypeID = args?.getString("vehicleTypeID")
        val manufacturerID = args?.getString("ManufacturerID")
        val sharedPreference =
            activity?.getSharedPreferences("VEHICLE", Context.MODE_PRIVATE)?.edit()
        sharedPreference?.putString("TypeID", vehicleTypeID)
        sharedPreference?.putString("MID", manufacturerID)?.apply()
        val sharedPreferences =
            activity?.getSharedPreferences("THIRD_PARTY_ID", Context.MODE_PRIVATE)
        val thirdPartyID = sharedPreferences?.getString("ThirdPartyID","ThirdPartyID")



        viewModel.getRequiredData(
            ScheduleTripRequest(
                access_token = token,
                vehicle_type_id = vehicleTypeID,
                manufacturer_id = manufacturerID,
                third_party_id = thirdPartyID
            )
        )
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

    private fun getViewModel(): TripViewModel {
        viewModelTrip = ViewModelProvider(this)[TripViewModel::class.java]
        return viewModelTrip
    }


    private fun addTripApiCall() {
        //view model init
        viewModelTrip = getViewModel()

        //getting request data

        val subscribe =
            PrefManager.get<LoginResponse>("LOGIN_RESPONSE")?.data?.subscription?.isSubscribe
        val customerID = PrefManager.get<LoginResponse>("LOGIN_RESPONSE")?.data?.customerId
        val args = this.arguments
        val vehicleTypeID = args?.getString("vehicleTypeID")
        val manufacturerID = args?.getString("ManufacturerID")
        val sharedPreferences =
            activity?.getSharedPreferences("THIRD_PARTY_ID", Context.MODE_PRIVATE)
        val thirdPartyID = sharedPreferences?.getString("ThirdPartyID","ThirdPartyID")
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
            val sharedPreference =
                activity?.getSharedPreferences("VEHICLE", Context.MODE_PRIVATE)?.edit()
            sharedPreference?.putString("TypeID", viewTripResponse?.vehicleTypeId.toString())
            sharedPreference?.putString("MID", viewTripResponse?.manufacturerId.toString())?.apply()
            bundle2.putParcelable("ScheduleResponse", viewTripResponse)

            binding.txtSTTRip.setText(R.string.update_your_trip)
            val sharedPreferences =
                activity?.getSharedPreferences("THIRD_PARTY_ID", Context.MODE_PRIVATE)
            val thirdPartyID = sharedPreferences?.getString("ThirdPartyID","ThirdPartyID")
            //call for schedule trip time duration api call
            binding.txtSTVehicleName.text = viewTripResponse?.vehicleType
            viewModel.getRequiredData(
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

    private fun getUpdateViewModel(): UpdateTripViewModel {
        updateTripViewModel = ViewModelProvider(this)[UpdateTripViewModel::class.java]
        return updateTripViewModel
    }

    private fun setUPUpdateApiCall() {
        updateTripViewModel = getUpdateViewModel()
        binding.btnSTConfirm.setOnClickListener {
            updateApiCall()
        }
        setObserverUpdate()
    }

    private fun updateApiCall() {
        val args = this.arguments
        val customerId = PrefManager.get<LoginResponse>("LOGIN_RESPONSE")?.data?.customerId
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
                    Log.d("ScheduleTripFragmentUpdate", "setObserverData: ${state.response?.data}")
                }
            }
        })
    }

    private fun getCancelViewModel(): CancelTripViewModel {
        cancelTripViewModel = ViewModelProvider(this)[CancelTripViewModel::class.java]
        return cancelTripViewModel
    }

    private fun cancelTrip() {
        cancelTripViewModel = getCancelViewModel()
        binding.btnSTCancelTrip.setOnClickListener {
            setUPCancelDialog()
        }
    }

    private fun getCancelTripData() {
        val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")
        val currentDate = LocalDateTime.now().format(dateFormatter)
        val currentTime = LocalDateTime.now().format(timeFormatter)
        val args = this.arguments
        val viewTripResponse = args?.getParcelable<ViewTripResponse>("ViewTripResponse")

        cancelTripViewModel.getCancelTripRequest(
            CancelTripRequest(
                cancel_fee = viewTripResponse?.cancelFee,
                access_token = token,
                reservation_id = viewTripResponse?.reservationId.toString(),
                current_date = currentDate,
                current_time = currentTime,
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