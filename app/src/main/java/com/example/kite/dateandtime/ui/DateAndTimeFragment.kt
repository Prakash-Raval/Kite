package com.example.kite.dateandtime.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.window.layout.WindowMetrics
import androidx.window.layout.WindowMetricsCalculator
import com.example.kite.MainActivity
import com.example.kite.R
import com.example.kite.constants.Constants
import com.example.kite.databinding.FragmentDateAndTimeBinding
import com.example.kite.databinding.ItemCalendarDayBinding
import com.example.kite.databinding.ItemCalendarHeaderBinding
import com.example.kite.dateandtime.adapter.DateAndTimeAdapter
import com.example.kite.dateandtime.listner.OnCellClicked
import com.example.kite.dateandtime.model.HeaderModel
import com.example.kite.dateandtime.model.TimeSlotRequest
import com.example.kite.dateandtime.model.TimeSlotResponse
import com.example.kite.dateandtime.repository.TimeSlotRepository
import com.example.kite.dateandtime.viewmodel.TSVMFactory
import com.example.kite.dateandtime.viewmodel.TimeSlotViewModel
import com.example.kite.login.model.LoginResponse
import com.example.kite.network.ApiInterface
import com.example.kite.network.RetrofitHelper
import com.example.kite.scheduletrip.ScheduleTripFragment
import com.example.kite.utils.*
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.CalendarMonth
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.view.MonthDayBinder
import com.kizitonwose.calendar.view.MonthHeaderFooterBinder
import com.kizitonwose.calendar.view.ViewContainer
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.*


class DateAndTimeFragment(val context1: Context) : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentDateAndTimeBinding

    private lateinit var viewModel: TimeSlotViewModel
    private lateinit var adapter: DateAndTimeAdapter

    var list = ArrayList<TimeSlotResponse.Data.AllTimeSlot>()
    private val sections = ArrayList<HeaderModel>()

    private var selectedTime = ""

    private val today = LocalDate.now()

    private var selectedDate: LocalDate? = today

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.setOnShowListener {
            setupFullHeight(it as BottomSheetDialog)
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

    private fun setupFullHeight(bottomSheetDialog: BottomSheetDialog) {
        val bottomSheet: FrameLayout =
            bottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet)!!
        val behavior: BottomSheetBehavior<View> = BottomSheetBehavior.from(bottomSheet)
        val layoutParams = bottomSheet.layoutParams
        val windowHeight = getWindowHeight()
        if (layoutParams != null) {
            layoutParams.height = windowHeight
        }
        bottomSheet.layoutParams = layoutParams
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
        behavior.skipCollapsed = true
    }

    private fun getWindowHeight(): Int {
        val windowMetrics: WindowMetrics =
            WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(
                requireActivity()
            )
        return windowMetrics.bounds.height()
    }

    /* override fun onStart() {
         super.onStart()
         dialog?.also {
             val bottomSheet = dialog.findViewById<View>()
             bottomSheet.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
             val behavior = BottomSheetBehavior.from<View>(bottomSheet)
             behavior.peekHeight = resources.displayMetrics.heightPixels //replace to whatever you want
             view?.requestLayout()
         }
     }*/

    /* @RequiresApi(Build.VERSION_CODES.TIRAMISU)
     fun onCreateView(
         inflater: LayoutInflater, container: ViewGroup?,
         savedInstanceState: Bundle?
     ): View {
         // Inflate the layout for this fragment

         return binding.root
     }*/

    /* override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
         val bottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
         bottomSheetDialog.setOnShowListener { dialog: DialogInterface ->
             val dialogc = dialog as BottomSheetDialog
             // When using AndroidX the resource can be found at com.google.android.material.R.id.design_bottom_sheet
             val bottomSheet : FrameLayout? =
                 dialogc.findViewById(com.google.android.material.R.id.design_bottom_sheet)
             val bottomSheetBehavior: BottomSheetBehavior<*> =
                 BottomSheetBehavior.from(bottomSheet)
             bottomSheetBehavior.peekHeight = Resources.getSystem().displayMetrics.heightPixels
             bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED)
         }
         return bottomSheetDialog
     }*/


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

                if (data.position == DayPosition.MonthDate) {
                    textView.makeVisible()
                    when (data.date) {
                        selectedDate -> {
                            textView.setTextColorRes(com.example.kite.R.color.white)
                            textView.setBackgroundResource(com.example.kite.R.drawable.bg_calender_red)
                        }
                        today -> {
                            textView.setTextColorRes(com.example.kite.R.color.bg_main)
                            textView.background = null
                        }
                        else -> {
                            textView.setTextColorRes(com.example.kite.R.color.black)
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
        val bundle = Bundle()
        bundle.putString("DateSelected", selectedDate.toString())
        bundle.putString("TimeSelected", selectedTime)
        binding.btnDTSet.setOnClickListener {
            if (binding.spinnerDTRegion.selectedItem.toString() == "Select Region") {
                Toast.makeText(context1, "Please select region", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(
                    context1,
                    "Time : " + selectedTime + "Date : " + selectedDate.toString(),
                    Toast.LENGTH_LONG
                ).show()


            }
        }

        binding.btnDTSetTime.setOnClickListener {
            Toast.makeText(
                context1,
                "Time : " + selectedTime + "Date : " + selectedDate.toString(),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    //setting up spinner data
    private fun setUpSpinnerData() {
        /* val args: DateAndTimeFragmentArgs by navArgs()
         val user: ScheduleTripResponse = args.scheduleTripRes*/
        val user = ScheduleTripFragment().myResponse

        val list = ArrayList<String>()
        list.add(0, "Select Region")
        user.data?.timezoneArr?.forEach { it ->
            it?.timeZone?.let { it1 -> list.add(it1) }
        }

        val arrayAdapter = ArrayAdapter(
            requireContext(), R.layout.item_spinner_date_time, list
        )

        binding.spinnerDTRegion.adapter = arrayAdapter

        //spinner data selected
        binding.spinnerDTRegion.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    binding.txtDTSelectTimezone.setText(R.string.timezone)
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?, view: View?, position: Int, id: Long
                ) {
                    user.data?.timezoneArr?.forEach { it ->
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
    @SuppressLint("NotifyDataSetChanged")
    private fun getTimeSlotData() {
        val service =
            RetrofitHelper.getInstance(Constants.BASE_URL).create(ApiInterface::class.java)
        val repository = TimeSlotRepository(service)
        viewModel = ViewModelProvider(
            context1 as ScheduleTripFragment, TSVMFactory(repository)
        )[TimeSlotViewModel::class.java]

        //passing data to request class
        val token = PrefManager.get<LoginResponse>("LOGIN_RESPONSE")?.data?.accessToken
        val timeZone = binding.spinnerDTRegion.selectedItem.toString()
        val sharedPreference =
            (context1 as MainActivity).getSharedPreferences("VEHICLE", Context.MODE_PRIVATE)
        val vehicleTypeID = sharedPreference?.getString("TypeID", "")
        val manufacturerID = sharedPreference?.getString("MID", "")
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


        viewModel.getRequiredData(
            TimeSlotRequest(
                access_token = token,
                current_time = currentTime,
                time_zone = timeZone,
                manufacturer_id = manufacturerID,
                vehicle_type_id = vehicleTypeID,
                schedule_date = scheduleDate
            )
        )

        viewModel.timeSlotLD.observe(context1.viewLifecycleOwner) {
            if (it.code == 200) {
                sections.clear()
                list = it.data?.allTimeSlots as ArrayList<TimeSlotResponse.Data.AllTimeSlot>
                setHeaderData()
            }
        }
    }

    //setting data to time slot recycler view header
    @SuppressLint("NotifyDataSetChanged")

    fun setHeaderData() {
        if (LocalDate.parse(selectedDate.toString()).equals(today)) {

            val mPickupTimeBean: ArrayList<TimeSlotResponse.Data.AllTimeSlot> = ArrayList()
            val nPickupTimeBean: ArrayList<TimeSlotResponse.Data.AllTimeSlot> = ArrayList()
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
        Toast.makeText(context1, "SSSS " + sections.size, Toast.LENGTH_SHORT).show()
        Log.i("TAG", "setHeaderData: $sections")
        setUPTimeSlotsAdapter()
    }

    private fun setUPTimeSlotsAdapter() {
        adapter = DateAndTimeAdapter(context1, object : OnCellClicked {
            override fun onClick(
                position1: Int, data: TimeSlotResponse.Data.AllTimeSlot, timeValue: String
            ) {
                ScheduleTripFragment().selectedTime = data.time.toString()
                ScheduleTripFragment().selectedDate = data.date.toString()

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

    /* private lateinit var mBehavior: BottomSheetBehavior<FrameLayout>

     override fun setContentView(view: View) {
         super.setContentView(view)
         val bottomSheet =
             window?.decorView?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
         mBehavior = BottomSheetBehavior.from(bottomSheet)
         mBehavior.state = BottomSheetBehavior.STATE_EXPANDED
     }

     override fun onStart() {
         super.onStart()
         mBehavior.state = BottomSheetBehavior.STATE_EXPANDED
     }*/


    /*override fun onClick(
        position1: Int,
        data: TimeSlotResponse.Data.AllTimeSlot,
        timeValue: String
    ) {
        selectedTime = timeValue
    }
*/
}