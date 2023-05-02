package com.example.kite.dateandtime.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.kite.R
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.ResponseData
import com.example.kite.databinding.FragmentDateAndTimeBinding
import com.example.kite.databinding.ItemCalendarDayBinding
import com.example.kite.databinding.ItemCalendarHeaderBinding
import com.example.kite.dateandtime.adapter.DateAndTimeAdapter
import com.example.kite.dateandtime.listner.GetDateAndTime
import com.example.kite.dateandtime.listner.OnCellClicked
import com.example.kite.dateandtime.model.HeaderModel
import com.example.kite.dateandtime.model.PromoCodeResponse
import com.example.kite.dateandtime.model.TimeSlotRequest
import com.example.kite.dateandtime.model.TimeSlotResponse
import com.example.kite.dateandtime.viewmodel.PromoCodeViewModel
import com.example.kite.dateandtime.viewmodel.TimeSlotViewModel
import com.example.kite.login.model.LoginResponse
import com.example.kite.scheduletrip.model.ScheduleTripResponse
import com.example.kite.utils.*
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.CalendarMonth
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.view.MonthDayBinder
import com.kizitonwose.calendar.view.MonthHeaderFooterBinder
import com.kizitonwose.calendar.view.ViewContainer
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class DateAndTimeFragment(val context1: Context, val getDateAndTime: GetDateAndTime) :
    BottomSheetDialogFragment() {


    /*
    *
    * variables
    * */
    private lateinit var binding: FragmentDateAndTimeBinding

    @Inject
    lateinit var viewModel: TimeSlotViewModel

    @Inject
    lateinit var viewModelPromoCode: PromoCodeViewModel

    private lateinit var adapter: DateAndTimeAdapter

    var list = ArrayList<TimeSlotResponse.AllTimeSlot>()
    private val sections = ArrayList<HeaderModel>()
    private val today = LocalDate.now()
    private var selectedDate: LocalDate? = today
    private var timeZone: String = ""
    private var timeZoneText: String = ""


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.setOnShowListener {
            FullScreenDialog.setupFullHeight(it as BottomSheetDialog, requireActivity())
        }
        manageLayoutVisibility()
        val daysOfWeek = daysOfWeek()
        configureBinders()
        binding.exTwoCalendar.setup(
            YearMonth.now(),
            YearMonth.now().plusMonths(200),
            daysOfWeek.first(),
        )
        setUpSpinnerData()
        setUpNavigate()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.fragment_date_and_time, null, false
        )
        return binding.root
    }


    //calender view data
    private fun configureBinders() {
        val calendarView = binding.exTwoCalendar


        class DayViewContainer(view: View) : ViewContainer(view) {
            // Will be set when this container is bound. See the dayBinder.
            lateinit var day: CalendarDay
            val textView = ItemCalendarDayBinding.bind(view).exTwoDayText

            init {
                textView.setOnClickListener {
                    Toast.makeText(
                        context1,
                        "Date : ${day.position} : ${DayPosition.MonthDate}",
                        Toast.LENGTH_SHORT
                    ).show()
                    if (day.position == DayPosition.MonthDate) {
                        if (selectedDate == day.date) {
                            /* selectedDate = null
                             calendarView.notifyDayChanged(day)*/
                        } else {
                            val oldDate = selectedDate
                            selectedDate = day.date
                            Toast.makeText(
                                context1, "Date :$selectedDate", Toast.LENGTH_SHORT
                            ).show()

                            calendarView.notifyDateChanged(day.date)
                            oldDate?.let { calendarView.notifyDateChanged(oldDate) }
                        }

                    }
                }
            }
        }



        calendarView.dayBinder = object : MonthDayBinder<DayViewContainer> {

            override fun create(view: View) = DayViewContainer(view)


            override fun bind(container: DayViewContainer, data: CalendarDay) {
                container.day = data
                val textView = container.textView
                textView.text = data.date.dayOfMonth.toString()

                //making past date non clickable
                if (data.date.isBefore(today)) {
                    textView.isClickable = false
                    textView.alpha = 0.3f
                }


                if (data.position == DayPosition.MonthDate) {
                    textView.makeVisible()
                    when (data.date) {
                        selectedDate -> {
                            textView.setTextColorRes(R.color.white)
                            textView.setBackgroundResource(R.drawable.bg_calender_red)
                        }
                        today -> {
                            textView.setTextColorRes(R.color.bg_main)
                            textView.background = null
                        }
                        else -> {
                            textView.setTextColorRes(R.color.black)
                            textView.background = null
                        }
                    }
                } else {
                    textView.makeInVisible()
                }
            }
        }

        class MonthViewContainer(view: View) : ViewContainer(view) {
            val textView = ItemCalendarHeaderBinding.bind(view).exTwoHeaderText
        }
        calendarView.monthHeaderBinder = object : MonthHeaderFooterBinder<MonthViewContainer> {
            override fun create(view: View) = MonthViewContainer(view)


            override fun bind(container: MonthViewContainer, data: CalendarMonth) {
                container.textView.text = data.yearMonth.displayText()
            }
        }
    }

    //managing date and time layout visibility
    private fun manageLayoutVisibility() {
        binding.txtDTDate.setOnClickListener {
            binding.nsTimeData.visibility = View.GONE
            binding.gpDTDate.visibility = View.VISIBLE
            binding.txtDTTime.alpha = 0.4f
            binding.txtDTDate.alpha = 1.0f
        }
        binding.txtDTTime.setOnClickListener {
            if (binding.spinnerDTRegion.selectedItem.toString() == "Select Region") {
                Toast.makeText(context1, "Please select region", Toast.LENGTH_SHORT).show()
            } else {
                getTimeSlotData()
                binding.txtDTDate.alpha = 0.4f
                binding.txtDTTime.alpha = 1.0f
                binding.gpDTDate.visibility = View.GONE
                binding.nsTimeData.visibility = View.VISIBLE
            }
        }
    }


    //navigation
    private fun setUpNavigate() {
        binding.btnDTSet.setOnClickListener {
            if (binding.spinnerDTRegion.selectedItem.toString() == "Select Region") {
                Toast.makeText(context1, "Please select region", Toast.LENGTH_SHORT).show()
            } else {
                //navigate back to schedule fragment
                getDateAndTime.getDate(
                    selectedDate.toString(),
                    "",
                    timeZone, timeZoneText
                )
                if (!binding.edtDTPromoCode.text.isNullOrEmpty()) {
                    setLiveDataObservers()

                } else {
                    dialog?.dismiss()
                }
            }
        }

        binding.btnDTSetTime.setOnClickListener {
            dialog?.dismiss()
        }

        binding.imgDTClose.setOnClickListener {
            dialog?.dismiss()
        }
    }

    //setting up spinner data
    private fun setUpSpinnerData() {
        val args = this.arguments
        val user = args?.getParcelable<ScheduleTripResponse>("ScheduleResponse")

        //setting up date
        val date = args?.getString("ScheduleDate")
        val time = args?.getString("ScheduleTime")
        val timeZone2 = args?.getString("ScheduleTimezone")
        if (date != null)
            selectedDate = if (date == "") {
                today
            } else {
                LocalDate.parse(date)
            }


        val list = ArrayList<String>()
        list.add(0, "Select Region")
        user?.timezoneArr?.forEach { it ->
            it?.timeZone?.let { it1 -> list.add(it1) }
        }

        val arrayAdapter = ArrayAdapter(
            requireContext(), R.layout.item_spinner_date_time, list
        )

        binding.spinnerDTRegion.adapter = arrayAdapter

        if (timeZone2 != null) {
            if (list.size > 0 && timeZone2.isNotEmpty())
                binding.spinnerDTRegion.setSelection(timeZone2.toInt())
        }

        //spinner data selected
        binding.spinnerDTRegion.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {


                    timeZone = position.toString()
                    timeZoneText = binding.spinnerDTRegion.selectedItem.toString()
                    Log.d("TimeZone", "onClick: $timeZone")

                    user?.timezoneArr?.forEach { it ->
                        if (binding.spinnerDTRegion.selectedItem.toString() == it?.timeZone) {
                            if (position == 0) {
                                binding.txtDTSelectTimezone.setText(R.string.timezone)
                            } else {
                                binding.txtDTSelectTimezone.text = it.title
                            }

                        }

                    }
                }

            }
    }


    //calling api data
    private fun getTimeSlotData() {


        //collecting data for passing in to request class
        val token = PrefManager.get<LoginResponse>("LOGIN_RESPONSE")?.accessToken
        val timeZone = binding.spinnerDTRegion.selectedItem.toString()
        val vehicleTypeID = PrefManager.get<String>("TypeID")
        val manufacturerID = PrefManager.get<String>("MID")
        val thirdPartyID = PrefManager.get<String>("ThirdPartyID")

        val scheduleDate: String = if (selectedDate != null) {
            selectedDate.toString()
        } else {
            today.toString()
        }
        val currentTime: String = if (selectedDate == today) {
            SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
        } else {
            "00:00:00"
        }

        //passing data to request class
        viewModel.getTimeSlot(
            TimeSlotRequest(
                access_token = token,
                current_time = currentTime,
                time_zone = timeZone,
                manufacturer_id = manufacturerID,
                vehicle_type_id = vehicleTypeID,
                schedule_date = scheduleDate,
                third_party_id = thirdPartyID
            )
        )

        viewModel.liveData.observe(this, Observer { state ->
            if (state == null) {
                return@Observer
            }
            when (state) {
                is ResponseHandler.Loading -> {

                    Log.d("PromoCodeResponse", "setLiveDataObservers: $state")

                }
                is ResponseHandler.OnFailed -> {

                    Log.d("PromoCodeResponse", "setLiveDataObservers: $state")


                }
                is ResponseHandler.OnSuccessResponse<ResponseData<TimeSlotResponse>?> -> {
                    Log.d("PromoCodeResponse", "setLiveDataObservers: $state")
                    sections.clear()
                    list =
                        state.response?.data?.allTimeSlots as ArrayList<TimeSlotResponse.AllTimeSlot>
                    setHeaderData()
                }
            }
        })


    }

    //setting data to time slot recycler view header
    private fun setHeaderData() {
        if (LocalDate.parse(selectedDate.toString()).equals(today)) {

            val mPickupTimeBean: ArrayList<TimeSlotResponse.AllTimeSlot> = ArrayList()
            val nPickupTimeBean: ArrayList<TimeSlotResponse.AllTimeSlot> = ArrayList()
            for (mTempPosition in 0 until list.size) {
                if (LocalDate.parse(list[mTempPosition].date).equals(today)) {
                    mPickupTimeBean.add(list[mTempPosition])
                } else {
                    nPickupTimeBean.add(list[mTempPosition])
                }
            }
            sections.add(
                HeaderModel(
                    "", mPickupTimeBean, false
                )
            )
            sections.add(
                HeaderModel(
                    DateTimeFormatter.ofPattern("MMMM dd").format(
                        LocalDate.parse(
                            nPickupTimeBean[0].date
                        )
                    ), nPickupTimeBean, true
                )
            )


        } else {

            sections.add(
                HeaderModel(
                    "", list, false
                )
            )
        }
        setUPTimeSlotsAdapter()
    }

    private fun setUPTimeSlotsAdapter() {
        adapter = DateAndTimeAdapter(context1, object : OnCellClicked {
            override fun onClick(
                position1: Int, data: TimeSlotResponse.AllTimeSlot
            ) {
                //passing data to previous fragment (ScheduleTripFragment)
                getDateAndTime.getDate(
                    data.date.toString(),
                    data.convertedTime.toString(),
                    timeZone,
                    timeZoneText
                )

                //checking adapter position for header data
                /*
                * if position 0
                *   header will be null or empty
                * if position 1
                *   header will contain next date string
                * */
                if (data.position == 0) {
                    adapter.notifyItemChanged(1)
                } else {
                    adapter.notifyItemChanged(0)
                }
            }
        })
        adapter.setList(sections)
        binding.rvTimeSlotContainer.adapter = adapter
    }

    //promo code api call
    private fun setLiveDataObservers() {
        binding.viewModelPromoCode = viewModelPromoCode

        val token = PrefManager.get<LoginResponse>("LOGIN_RESPONSE")?.accessToken
        if (token != null) {
            viewModelPromoCode.checkPromoCode(token = token)
        }

        viewModelPromoCode.responseLiveData.observe(this, Observer { state ->
            if (state == null) {
                return@Observer
            }
            when (state) {
                is ResponseHandler.Loading -> {
                    //PromoCodeViewModel.showProgressBar(true)
                    //httpFailedHandler(state.code, state.message, state.messageCode)
                    Log.d("PromoCodeResponse", "setLiveDataObservers: $state")

                }
                is ResponseHandler.OnFailed -> {
                    //PromoCodeViewModel.showProgressBar(false)
                    Log.d("PromoCodeResponse", "setLiveDataObservers: $state")


                }
                is ResponseHandler.OnSuccessResponse<ResponseData<PromoCodeResponse>?> -> {
                    //PromoCodeViewModel.showProgressBar(false)
                    Log.d("PromoCodeResponse", "setLiveDataObservers: $state")
                    dialog?.dismiss()

                }
            }
        })
    }
}