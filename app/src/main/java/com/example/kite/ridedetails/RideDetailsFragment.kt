package com.example.kite.ridedetails

import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.kite.R
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.ResponseData
import com.example.kite.basefragment.BaseFragment
import com.example.kite.databinding.FragmentRideDetailsBinding
import com.example.kite.login.model.LoginResponse
import com.example.kite.ridedetails.model.PrintReceiptRequest
import com.example.kite.ridedetails.model.PrintReceiptResponse
import com.example.kite.ridedetails.model.RideDetailRequest
import com.example.kite.ridedetails.model.RideDetailResponse
import com.example.kite.ridedetails.viewmodel.PrintReceiptViewModel
import com.example.kite.ridedetails.viewmodel.RideDetailViewModel
import com.example.kite.utils.PrefManager
import com.example.kite.utils.Util
import com.google.common.primitives.Bytes
import kotlinx.coroutines.*
import java.io.*


class RideDetailsFragment : BaseFragment() {

    private lateinit var binding: FragmentRideDetailsBinding
    private lateinit var viewModel: RideDetailViewModel
    private lateinit var viewModelReceipt: PrintReceiptViewModel
    lateinit var list: List<Int>
    private var bookingID = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.fragment_ride_details,
            container,
            false
        )
        setUPToolbar()
        getRideHistoryData()
        setGenerateReceipt()
        return binding.root
    }

    /*
    * setting up the toolbar and back navigation
    * */

    private fun setUPToolbar() {
        binding.inRideBar.txtToolbarHeader.setText(R.string.ride_details)
        binding.inRideBar.imgBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    /*
    * generating travel details receipt
    * */
    private fun setGenerateReceipt() {
        binding.btnRDSupport.setOnClickListener {
            findNavController().navigate(RideDetailsFragmentDirections.actionRideDetailsFragmentToSupportFragment())
        }

        binding.btnRDReceipt.setOnClickListener {
            getReceiptData()
        }
    }

    /*
    * getting the viewModel
    * */
    private fun getViewModel(): RideDetailViewModel {
        viewModel = ViewModelProvider(this)[RideDetailViewModel::class.java]
        return viewModel
    }

    private fun getViewModelReceipt(): PrintReceiptViewModel {
        viewModelReceipt = ViewModelProvider(this)[PrintReceiptViewModel::class.java]
        return viewModelReceipt
    }

    /*
    * request for api
    * */
    private fun getRideHistoryData() {
        viewModel = getViewModel()
        val args = this.arguments
        bookingID = args?.getString("BookingID").toString()
        val token = PrefManager.get<LoginResponse>("LOGIN_RESPONSE")?.accessToken
        Log.d("RideDetailsFragment", "setObserverData: $bookingID")


        viewModel.getRideDetailsRequest(
            RideDetailRequest(
                bookingId = bookingID,
                accessToken = token
            )
        )
        setObserverRideDetails()
    }


    /*
    *
    * getting receipt api data
    * */
    private fun getReceiptData() {
        viewModelReceipt = getViewModelReceipt()
        val args = this.arguments
        val bookingID = args?.getString("BookingID")
        val token = PrefManager.get<LoginResponse>("LOGIN_RESPONSE")?.accessToken
        Log.d("RideDetailsFragment", "setObserverData: $bookingID")


        viewModelReceipt.getThirdPartyList(
            PrintReceiptRequest(
                bookingId = bookingID,
                accessToken = token
            )
        )
        setObserverReceipt()
    }

    /*
   * setting the observer
   * */
    private fun setObserverReceipt() {
        //handling error event in snack bar
        viewModelReceipt.liveData.observe(viewLifecycleOwner, Observer { state ->
            if (state == null) {
                return@Observer
            }
            when (state) {
                is ResponseHandler.Loading -> {
                    showProgressDialog()
                    Log.d("RideDetailsFragment", "setObserverData: $state")
                }
                is ResponseHandler.OnFailed -> {
                    hideProgressBar()
                    Log.d("RideDetailsFragment", "setObserverData: $state")
                }
                is ResponseHandler.OnSuccessResponse<ResponseData<PrintReceiptResponse>?> -> {
                    hideProgressBar()
                    list = state.response?.data?.data!!
                    /*
                    *
                    * running task on background thread saving pdf
                    * */
                    lifecycleScope.launch(Dispatchers.IO) {
                        writeResponseBodyToDisk(list.let { Bytes.toArray(it) }, bookingID)
                    }
                    Log.d("RideDetailsFragment", "setObserverData: ${state.response.data}")
                }
            }
        })
    }

    /*
    * setting the observer
    * */
    private fun setObserverRideDetails() {
        //handling error event in snack bar
        viewModel.liveData.observe(viewLifecycleOwner, Observer { state ->
            if (state == null) {
                return@Observer
            }
            when (state) {
                is ResponseHandler.Loading -> {
                    showProgressDialog()
                    Log.d("RideDetailsFragment", "setObserverData: $state")
                }
                is ResponseHandler.OnFailed -> {
                    hideProgressBar()
                    Log.d("RideDetailsFragment", "setObserverData: $state")
                }
                is ResponseHandler.OnSuccessResponse<ResponseData<RideDetailResponse>?> -> {
                    hideProgressBar()
                    binding.rideDetail = state.response?.data
                    binding.txtRDDistance.text =
                        buildString {
                            append(state.response?.data?.totalDistance.toString().take(4))
                            append(" Km Travelled")
                        }

                    binding.txtRDDate.text = state.response?.data?.startDate?.let {
                        Util.getMillisFromTime(
                            it
                        )
                    }?.let { Util.getDateFromTimeString(it) }
                    binding.txtRDStartTime.text =
                        convertTime(state.response?.data?.startDate.toString())
                    binding.txtRDEndingTime.text =
                        convertTime(state.response?.data?.endDate.toString())
                    Log.d("RideDetailsFragment", "setObserverData: ${state.response?.data}")
                }
            }
        })
    }

    /*
    * converting time and date format
    * */
    private fun convertTime(dateStr: String): String {
        return Util.getDateFromTimeString(Util.getMillisFromTime(dateStr), "HH:mm a")
    }

    /*
    * converting list into byte array
    * for download pdf
    * */
    private fun writeResponseBodyToDisk(body: ByteArray, bookingId: String): Boolean {
        return try {
            val futureStudioIconFile =
                File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
                        .toString()
                            + "/" + bookingId + "_Kite_Booking_Receipt" + ".pdf"
                )
            var inputStream: InputStream? = null
            var outputStream: OutputStream? = null
            if (!futureStudioIconFile.exists()) {
                try {
                    val fileReader = ByteArray(4096)
                    val fileSize = body.size.toLong()
                    var fileSizeDownloaded: Long = 0
                    inputStream = ByteArrayInputStream(body)
                    outputStream = FileOutputStream(futureStudioIconFile)
                    while (true) {
                        val read = inputStream.read(fileReader)
                        if (read == -1) {
                            break
                        }
                        outputStream.write(fileReader, 0, read)
                        fileSizeDownloaded += read.toLong()
                        Log.d("LOgDownload", "file download: $fileSizeDownloaded of $fileSize")
                    }
                    outputStream.flush()

                    true
                } catch (e: IOException) {
                    Log.d("LOgDownload", "file download: $e")
                    Toast.makeText(
                        requireContext(),
                        e.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                    false
                } finally {
                    inputStream?.close()
                    outputStream?.close()
                }
            } else {
                true
            }
        } catch (e: IOException) {
            false
        }
    }

/*    private fun showPDF(filepath: String) {
        val file = File(Environment.getExternalStorageDirectory().toString() + filepath)
        val packageManager: PackageManager? = activity?.packageManager
        val testIntent = Intent(Intent.ACTION_VIEW)
        testIntent.type = "application/pdf"
        val list: MutableList<ResolveInfo>? =
            packageManager?.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY)
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        val uri = Uri.fromFile(file)
        intent.setDataAndType(uri, "application/pdf")
        startActivity(intent)
    }*/
}


