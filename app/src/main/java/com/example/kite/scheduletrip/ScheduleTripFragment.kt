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
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.kite.R
import com.example.kite.constants.Constants
import com.example.kite.databinding.FragmentScheduleTripBinding
import com.example.kite.dateandtime.ui.DateAndTimeFragment
import com.example.kite.login.model.LoginResponse
import com.example.kite.network.ApiInterface
import com.example.kite.network.RetrofitHelper
import com.example.kite.scheduletrip.adapter.ScheduleTripAdapter
import com.example.kite.scheduletrip.model.ScheduleTimeDuration
import com.example.kite.scheduletrip.model.ScheduleTripRequest
import com.example.kite.scheduletrip.model.ScheduleTripResponse
import com.example.kite.scheduletrip.repository.ScheduleTripRepository
import com.example.kite.scheduletrip.viewmodel.STVMFactory
import com.example.kite.scheduletrip.viewmodel.ScheduleTripViewModel
import com.example.kite.utils.PrefManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton

class ScheduleTripFragment : Fragment() {
    private lateinit var binding: FragmentScheduleTripBinding
    private lateinit var viewModel: ScheduleTripViewModel
    private lateinit var scheduleTripAdapter: ScheduleTripAdapter
    var list = ArrayList<ScheduleTimeDuration>()
    private var count = 0
    private lateinit var bundle: ScheduleTripResponse
    var selectedDate: String = ""
    var selectedTime: String = ""
    var myResponse = ScheduleTripResponse()


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
        setData()
        setCounter()
        setDateAndTime()
        return binding.root
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
            /* findNavController().navigate(
                 ScheduleTripFragmentDirections.actionScheduleTripFragmentToDateAndTimeFragment(
                 )
             )*/
            val dialog = DateAndTimeFragment(requireContext())
            dialog.show(requireActivity().supportFragmentManager, "")
        }
        binding.imgDownTime.setOnClickListener {
            if (binding.txtSTDateSelect.isVisible) {
                val dialog = DateAndTimeFragment(requireContext())
                dialog.show(requireActivity().supportFragmentManager, "")
            } else {
                Toast.makeText(requireContext(), "Please select date", Toast.LENGTH_SHORT).show()
            }
        }
        binding.txtSTDateSelect.setOnClickListener {
            val dialog = DateAndTimeFragment(requireContext())
            dialog.show(requireActivity().supportFragmentManager, "")
        }

        binding.txtSTTimeSelected.setOnClickListener {
            if (binding.txtSTDateSelect.isVisible) {
                /*findNavController().navigate(
                    ScheduleTripFragmentDirections.actionScheduleTripFragmentToDateAndTimeFragment(
                        bundle
                    )
                )*/
                val dialog = DateAndTimeFragment(requireContext())
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

    private fun setCounter() {
        binding.txtCountRepeat.text = count.toString()
        binding.imgRepeatRemove.setOnClickListener {
            if (count != 0) count--
            binding.txtCountRepeat.text = count.toString()
        }
        binding.imgRepeatADD.setOnClickListener {
            count++
            binding.txtCountRepeat.text = count.toString()
        }
    }

    private fun setUPBottomSheet() {
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
        scheduleTripAdapter = ScheduleTripAdapter(requireContext())
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
                bundle = it
                scheduleTripAdapter.setList(
                    it.data?.tripDuration as ArrayList<ScheduleTripResponse.Data.TripDuration>, list
                )
                myResponse = it
                scheduleTripAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(requireContext(), "${it.message}", Toast.LENGTH_SHORT).show()
            }
        }

        val prefLogin = PrefManager.get<LoginResponse>("LOGIN_RESPONSE")


        val args = this.arguments
        val vehicleTypeID = args?.getString("vehicleTypeID")
        val manufacturerID = args?.getString("ManufacturerID")

        val sharedPreference =
            activity?.getSharedPreferences("VEHICLE", Context.MODE_PRIVATE)?.edit()
        sharedPreference?.putString("TypeID", vehicleTypeID)
        sharedPreference?.putString("MID", manufacturerID)?.apply()


        viewModel.getRequiredData(
            ScheduleTripRequest(
                access_token = prefLogin?.data?.accessToken,
                vehicle_type_id = vehicleTypeID,
                manufacturer_id = manufacturerID
            )
        )
    }

}