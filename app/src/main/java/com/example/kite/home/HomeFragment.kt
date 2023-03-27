package com.example.kite.home

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.kite.R
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.ResponseData
import com.example.kite.basefragment.BaseFragment
import com.example.kite.databinding.DialogUpdateChargeBinding
import com.example.kite.databinding.FragmentHomeBinding
import com.example.kite.home.viewmodel.ViewTripViewModel
import com.example.kite.login.model.LoginResponse
import com.example.kite.reservation.model.ListReservationRequest
import com.example.kite.reservation.model.ListReservationResponse
import com.example.kite.utils.PrefManager
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class HomeFragment : BaseFragment() {

    /*
    * variables
    * */
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModelViewTrip: ViewTripViewModel
    private val foregroundLocationPermissionsRequestCode = 1
    private val backgroundLocationPermissionsRequestCode = 2

    private var reservationID = ""
    private var isShown = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (isShown == 0) {
            isShown = 1
            openDialog()
        }
        /*
        * permission id of location dialog
        * */
        requestLocationPermissions()
    }

    /*private fun initMap() {
        *//*
        * init radar api for map
        * *//*
        val receiver = MyRadarReceiver()
        Radar.initialize(requireContext(), "prj_test_pk_9fa4fb5a14490462e0c12aa4995063460dd62b5a",receiver)
        Radar.getLocation { status, location, stopped ->
            Log.d("example", "Location: status = ${status}; location = $location; stopped = $stopped")
            Log.d("example", "Location: status = ${status}; location = ${location?.latitude}; location = ${location?.longitude}; stopped = $stopped")
        }
    }*/


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
        binding.cardViewTripContainer.visibility = View.GONE
        setUpDrawer()
        setUpNavigation()
        getViewTripApi()
        setObserver()
        viewTrip()
        return binding.root
    }


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
                    if (state.response?.data?.reservationData?.size == 0) {
                        binding.cardViewTripContainer.visibility = View.GONE
                    } else {
                        binding.viewTrip = state.response?.data?.reservationData?.getOrNull(0)
                        binding.cardViewTripContainer.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    private fun setUpNavigation() {
        binding.viewHelp.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSupportFragment())
        }
    }

    //calling drawer from main activity
    private fun setUpDrawer() {
        val mDrawer = activity?.findViewById<DrawerLayout>(R.id.drawerLayout)
        binding.viewMenu.setOnClickListener {
            mDrawer?.openDrawer(GravityCompat.START)
        }
    }

    private fun openDialog() {
        val builder = AlertDialog.Builder(requireContext())
            .create()
        builder.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val view = layoutInflater.inflate(R.layout.dialog_show_home, null)
        builder.setView(view)
        val close = view.findViewById<ImageView>(R.id.imgClose)
        val btnClose: Button = view.findViewById(R.id.btnOk)

        btnClose.setOnClickListener {
            builder.dismiss()
        }
        close.setOnClickListener {
            builder.dismiss()
        }
        builder.setCanceledOnTouchOutside(false)
        builder.show()
    }

    @SuppressLint("MissingInflatedId")
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


    //assigning view model
    private fun getViewModel(): ViewTripViewModel {
        viewModelViewTrip = ViewModelProvider(this)[ViewTripViewModel::class.java]
        return viewModelViewTrip
    }


    //calling api from reservation fragment for schedule trip view
    private fun getViewTripApi() {
        //view model
        viewModelViewTrip = getViewModel()

        //getting value to pass in request class
        val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")
        val currentDate = LocalDateTime.now().format(dateFormatter)
        val currentTime = LocalDateTime.now().format(timeFormatter)
        val token = PrefManager.get<LoginResponse>("LOGIN_RESPONSE")?.data?.accessToken
        val sharedPreferences =
            activity?.getSharedPreferences("THIRD_PARTY_ID", Context.MODE_PRIVATE)
        val thirdPartyID = sharedPreferences?.getString("ThirdPartyID", "ThirdPartyID")
        //passing data to request class
        viewModelViewTrip.getViewTripRequest(
            ListReservationRequest(
                access_token = token,
                start_date = currentDate,
                start_time = currentTime,
                offset = 0,
                current_date = currentDate,
                current_time = currentTime,
                limit = 1,
                third_party_id = thirdPartyID
            )
        )

    }

    //view trip fragment
    private fun viewTrip() {
        binding.btnHView.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("ReservationID", reservationID)
            val action = R.id.action_homeFragment_to_viewTripFragment
            findNavController().navigate(action, bundle)
        }
    }

    private fun requestLocationPermissions() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.Q) {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_BACKGROUND_LOCATION
                    ),
                    backgroundLocationPermissionsRequestCode
                )
            } else {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ),
                    foregroundLocationPermissionsRequestCode
                )
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == foregroundLocationPermissionsRequestCode && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED && Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION
                )
            ) {
                androidx.appcompat.app.AlertDialog.Builder(requireContext())
                    .setTitle("Background location permissions needed")
                    .setMessage("Background location permission needed, tap \"Allow all the time\" on the next screen")
                    .setPositiveButton(
                        "OK"
                    ) { _, _ ->
                        ActivityCompat.requestPermissions(
                            requireActivity(),
                            arrayOf(Manifest.permission.ACCESS_BACKGROUND_LOCATION),
                            backgroundLocationPermissionsRequestCode
                        )
                    }
                    .create()
                    .show()
            }
        }
    }
}