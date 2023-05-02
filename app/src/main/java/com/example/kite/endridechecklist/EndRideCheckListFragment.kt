package com.example.kite.endridechecklist

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.canhub.cropper.CropImage.CancelledResult.bitmap
import com.example.kite.R
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.ResponseData
import com.example.kite.basefragment.BaseFragment
import com.example.kite.databinding.DialogRateUsBinding
import com.example.kite.databinding.FragmentEndRideCheckListBinding
import com.example.kite.endridechecklist.model.EndRideResponse
import com.example.kite.endridechecklist.viewmodel.EndRideViewModel
import com.example.kite.login.model.LoginResponse
import com.example.kite.utils.FullScreenDialog
import com.example.kite.utils.PrefManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject

@AndroidEntryPoint

class EndRideCheckListFragment : BaseFragment() {

    /*
    * variables
    * */
    private lateinit var binding: FragmentEndRideCheckListBinding
    @Inject lateinit var viewModelEndRide: EndRideViewModel
    private var profileImagePath: String? = ""
    private var latString = "23.033863"
    private var longString = "72.585022"


    /*
    * creating contract for capturing image from camera
    *
    * */
    private val startForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            //  you will get result here in result.data
            binding.imgERPhoto.setImageBitmap(result.data?.extras?.get("data") as Bitmap)


            /*
            * saving image to get path
            * */
            runBlocking {
                val cw = ContextWrapper(context)
                val directory: File = cw.getDir("ImageDir", Context.MODE_PRIVATE)
                if (!directory.exists()) {
                    if (!directory.mkdir()) {
                        directory.mkdirs()
                    }
                }
                val file = File(directory, "Kite" + System.currentTimeMillis() + ".jpg")
                val fos: FileOutputStream?
                try {
                    fos = withContext(Dispatchers.IO) {
                        FileOutputStream(file)
                    }
                    bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                    withContext(Dispatchers.IO) {
                        fos.flush()
                        fos.close()
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                profileImagePath = file.absolutePath
            }

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.fragment_end_ride_check_list,
            container,
            false
        )
        /*
        * method calls
        * */
        setupToolbar()
        setNavigate()
        return binding.root
    }

    /*
    * setting navigation for fragment
    * */
    private fun setNavigate() {
        binding.btnETCLCamera.setOnClickListener {
            openCamera()
        }
        binding.btnETCLSubmit.setOnClickListener {
            getEndRideRequest()
        }
    }

    /*
    * setting up toolbar
    * */
    private fun setupToolbar() {
        binding.inETCBar.imgBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.inETCBar.txtToolbarHeader.setText(R.string.end_ride_check_List)
    }



    /*
    * getting request data for end trip view model
    * */
    private fun getEndRideRequest() {

        /*
        * getting request values for api call
        * */
        val args = this.arguments
        val booking = args?.getString("BookingIDForEndTrip")
        val token = PrefManager.get<LoginResponse>("LOGIN_RESPONSE")?.accessToken
        val accessToken =
            token?.toRequestBody("text/plain".toMediaTypeOrNull())
        val bookingID = booking?.toRequestBody("text/plain".toMediaTypeOrNull())
        val lat = latString.toRequestBody("text/plain".toMediaTypeOrNull())
        val geolocationID = "18".toRequestBody("text/plain".toMediaTypeOrNull())
        val long = longString.toRequestBody("text/plain".toMediaTypeOrNull())
        val battery = "40".toRequestBody("text/plain".toMediaTypeOrNull())
        val file = profileImagePath?.let { File(it) }
        val requestBody: RequestBody? =
            file?.asRequestBody("image/*".toMediaTypeOrNull())
        val imagePart =
            requestBody?.let { MultipartBody.Part.createFormData("profile_image", file.name, it) }

        if (imagePart != null) {
            viewModelEndRide.getOnGoingRideRequest(
                access_token = accessToken,
                booking_id = bookingID,
                dropoff_lat = lat,
                dropoff_long = long,
                geolocation_id = geolocationID,
                battery = battery,
                image = imagePart
            )
        }
        /*
        * setting up the observer
        * */
        setObserverEndRide()
    }

    /*
    * setting up observer for End ride
    */
    private fun setObserverEndRide() {
        //observing view model response data
        viewModelEndRide.liveData.observe(viewLifecycleOwner, Observer { state ->
            if (state == null) {
                return@Observer
            }
            when (state) {
                is ResponseHandler.Loading -> {
                    showProgressDialog()
                    Log.d("EndRideCheckList", "setObserverOnGoingRide: $state")
                }
                is ResponseHandler.OnFailed -> {
                    hideProgressBar()
                    Log.d("EndRideCheckList", "setObserverOnGoingRide: $state")
                }
                is ResponseHandler.OnSuccessResponse<ResponseData<EndRideResponse>?> -> {
                    hideProgressBar()
                    if (state.response?.code == 200) {
                        Log.d("EndRideCheckList", "setObserverOnGoingRide: ${state.response.data}")
                        val bundle = Bundle()
                        bundle.putString("BookingID", state.response.data?.bookingId)
                        findNavController().navigate(
                            R.id.action_endRideCheckListFragment_to_rideDetailsFragment,
                            bundle
                        )
                    }
                }
            }
        })
    }

    /*
    * opening camera using intent
    * */
    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startForResult.launch(intent)
    }

    private fun rateUsDialog() {
        val builder = BottomSheetDialog(requireContext())
        val bind: DialogRateUsBinding =
            DialogRateUsBinding.inflate(LayoutInflater.from(context))
        builder.setContentView(bind.root)
        bind.btnWDCCancel.setOnClickListener {

        }
        bind.txtWDCEndRide.setOnClickListener {
            builder.dismiss()
        }
        bind.txtFeedBack.setOnClickListener {

        }
        FullScreenDialog.setupFullHeight(builder, requireActivity())
        builder.behavior.isDraggable = false
        builder.show()
    }

}