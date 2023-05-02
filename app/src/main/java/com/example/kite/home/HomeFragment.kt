package com.example.kite.home

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.kite.R
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.ResponseData
import com.example.kite.basefragment.BaseFragment
import com.example.kite.databinding.DialogBsEndTripBinding
import com.example.kite.databinding.DialogUpdateChargeBinding
import com.example.kite.databinding.FragmentHomeBinding
import com.example.kite.extensions.DateAndTime
import com.example.kite.home.model.OnGoingRideRequest
import com.example.kite.home.model.OnGoingRideResponse
import com.example.kite.home.viewmodel.OnGoingRideViewModel
import com.example.kite.home.viewmodel.ViewTripViewModel
import com.example.kite.login.model.LoginResponse
import com.example.kite.reservation.model.ListReservationRequest
import com.example.kite.reservation.model.ListReservationResponse
import com.example.kite.utils.FullScreenDialog
import com.example.kite.utils.PrefManager
import com.example.kite.utils.Util
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    /*
    * variables
    * */
    private lateinit var binding: FragmentHomeBinding

    @Inject
    lateinit var viewModelViewTrip: ViewTripViewModel

    @Inject
    lateinit var viewModelOnGoingRide: OnGoingRideViewModel

    val token = PrefManager.get<LoginResponse>("LOGIN_RESPONSE")
    private var reservationID = ""
    private var counter: Int = 0
    private val bundle = Bundle()
    private var countDownTimer: CountDownTimer? = null
    private var isReservationActive: Boolean? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.fragment_home,
            container,
            false
        )
        // Inflate the layout for this fragment
        val args = this.arguments
        val isCheck = args?.getBoolean("UpCharge")
        if (isCheck == true) {
            openUpdateChargeDialog()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*
        * method calls
        * */
        getOnGoingRide()
        setUpDrawer()
        setUpNavigation()
        getViewTripApi()
        setObserver()
        viewTrip()
        setDrawerMenu()
    }


    /*
    *
    * checking for onGoing ride data
    * */
    private fun checkStartTripData(response: OnGoingRideResponse?) {
        if (response?.bookingId != null) {
            val start: Long? = response.startDate?.let { Util.getMillisFromTime(it) }
            Log.d("TAG111", "checkStartTripData: $start")

            /*
            * calculating diff time between started ride time and current time
            * */
            val current: Long? = System.currentTimeMillis()
            val dif: Long? = start?.let { current?.minus(it) }
            if (dif != null) {
                counter = (TimeUnit.MILLISECONDS.toMinutes(dif) - 330).toInt()
            }
        }
    }

    /*
    *
    * set counter for calculating min and charge according to the time
    *
    */
    private fun startTimeCounter() {
        /*
        * checking the countDownTimer value is null or not
        * */
        if (countDownTimer == null) {
            countDownTimer = object : CountDownTimer(43200000, 60000) {
                override fun onTick(millisUntilFinished: Long) {
                    binding.txtBDTime.text = buildString {
                        append(counter)
                        append("/min")
                    }

                    binding.txtBDCost.text = buildString {
                        append("$")
                        append(counter)
                        append(".00")
                    }
                    counter++
                }

                override fun onFinish() {
                }
            }.start()
        }
    }


    /*
    * setting up observer for view schedule trip
    * */
    private fun setObserver() {
        //observing view model response data
        viewModelViewTrip.liveData.observe(viewLifecycleOwner, Observer { state ->
            if (state == null) {
                return@Observer
            }
            when (state) {
                is ResponseHandler.Loading -> {

                }
                is ResponseHandler.OnFailed -> {

                }
                is ResponseHandler.OnSuccessResponse<ResponseData<ListReservationResponse>?> -> {

                    reservationID =
                        state.response?.data?.reservationData?.getOrNull(0)?.reservationId.toString()
                    if (state.response?.data?.reservationData?.size != 0) {
                        isReservationActive = true
                        setDrawerMenu()
                        binding.viewTrip = state.response?.data?.reservationData?.getOrNull(0)
                    }
                }
            }
        })
    }

    /*
    * opening helper fragment
    * */
    private fun setUpNavigation() {
        binding.viewHelp.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSupportFragment())
        }

        binding.btnHEndTrip.setOnClickListener {
            endTripBottomSheetDialog()
        }
    }

    /*
    *
    * calling method from main activity for opening drawer
    * */
    private fun setUpDrawer() {
        val mDrawer = activity?.findViewById<DrawerLayout>(R.id.drawerLayout)
        binding.viewMenu.setOnClickListener {
            mDrawer?.openDrawer(GravityCompat.START)
        }
    }

    /*
    * setting up the name of customer in drawer menu
    * */
    private fun setDrawerMenu() {
        val name = activity?.findViewById<TextView>(R.id.txtCustomerName)
        val tripTaken = activity?.findViewById<TextView>(R.id.txtCustomerTrip)
        val reservation = activity?.findViewById<TextView>(R.id.txtCustomerReservation)
        name?.text = token?.customerFirstName
        tripTaken?.text = buildString {
            append("Trip taken : ")
            append(token?.isFirstRide.toString())
        }
        if (isReservationActive == true) {
            reservation?.alpha = 1.0f
        } else {
            reservation?.alpha = 0.4f
        }

    }

    /*
    *
    * dialog shown when trip scheduled successfully
    * */
    private fun openUpdateChargeDialog() {
        val builder = AlertDialog.Builder(requireContext())
            .create()
        val args = this.arguments
        val charge = args?.getString("CancelCharge", "2.0")
        builder.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val bind: DialogUpdateChargeBinding =
            DialogUpdateChargeBinding.inflate(LayoutInflater.from(context))
        builder.setView(bind.root)
        bind.price = charge
        bind.btnUCOk.setOnClickListener { builder.dismiss() }
        bind.imgUCClose.setOnClickListener { builder.dismiss() }
        builder.setCanceledOnTouchOutside(false)
        builder.show()

    }

    /*
    * calling on going ride api
    * */
    private fun getOnGoingRide() {
        viewModelOnGoingRide.getOnGoingRideRequest(
            OnGoingRideRequest(
                accessToken = token?.accessToken,
                customerId = token?.customerId,
                lang = 1
            )
        )
        setObserverOnGoingRide()
    }


    /*
    * setting up observer for ongoing ride
    */
    private fun setObserverOnGoingRide() {
        //observing view model response data
        viewModelOnGoingRide.liveData.observe(viewLifecycleOwner, Observer { state ->
            if (state == null) {
                return@Observer
            }
            when (state) {
                is ResponseHandler.Loading -> {

                }
                is ResponseHandler.OnFailed -> {

                }
                is ResponseHandler.OnSuccessResponse<ResponseData<OnGoingRideResponse>?> -> {
                    if (state.response?.code == 200) {
                        /*
                        * managing visibility for card views
                        */
                        bundle.putString(
                            "BookingIDForEndTrip",
                            state.response.data?.bookingId.toString()
                        )
                        binding.onGoingRide = state.response.data
                        checkStartTripData(state.response.data)
                        startTimeCounter()
                    }
                }
            }
        })
    }

    /*
    * calling api from reservation fragment for schedule trip view
    */
    private fun getViewTripApi() {


        //getting value to pass in request class
        val thirdPartyID = PrefManager.get<String>("ThirdPartyID")
        //passing data to request class
        viewModelViewTrip.getViewTripRequest(
            ListReservationRequest(
                access_token = token?.accessToken,
                start_date = DateAndTime.currentDate,
                start_time = DateAndTime.currentTime,
                offset = 0,
                current_date = DateAndTime.currentDate,
                current_time = DateAndTime.currentTime,
                limit = 1,
                third_party_id = thirdPartyID
            )
        )
    }

    /*
    * opening view schedule trip fragment
    * */
    private fun viewTrip() {
        binding.btnHView.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("ReservationID", reservationID)
            val action = R.id.action_homeFragment_to_viewTripFragment
            findNavController().navigate(action, bundle)
        }
    }

    /*
    * open bottom sheet dialog when user tries to end trip
    * */
    private fun endTripBottomSheetDialog() {
        val builder = BottomSheetDialog(requireContext())
        val bind: DialogBsEndTripBinding =
            DialogBsEndTripBinding.inflate(LayoutInflater.from(context))
        builder.setContentView(bind.root)
        bind.imgETBack.setOnClickListener { builder.dismiss() }
        bind.btnETEndTrip.setOnClickListener {
            findNavController().navigate(
                R.id.action_homeFragment_to_endRideCheckListFragment,
                bundle
            )
            builder.dismiss()
        }
        bind.txtETNot.setOnClickListener { builder.dismiss() }
        FullScreenDialog.setupFullHeight(builder, requireActivity())
        builder.behavior.isDraggable = false
        builder.show()
    }
}