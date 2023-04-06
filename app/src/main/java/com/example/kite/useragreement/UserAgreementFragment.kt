package com.example.kite.useragreement

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.kite.R
import com.example.kite.base.network.client.ResponseHandler
import com.example.kite.base.network.model.ResponseListData
import com.example.kite.countrylisting.CountryListingAdapter
import com.example.kite.countrylisting.CountryResponse
import com.example.kite.countrylisting.OnCellClickedRegion
import com.example.kite.countrylisting.RegionViewModel
import com.example.kite.countrylisting.statelisting.StateListingAdapter
import com.example.kite.countrylisting.statelisting.StateRequest
import com.example.kite.countrylisting.statelisting.StateResponse
import com.example.kite.databinding.FragmentUserAgreementBinding
import com.example.kite.extensions.setLocalImage
import com.example.kite.useragreement.adapter.UserAgreementAdapter
import com.example.kite.useragreement.model.GuestModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class UserAgreementFragment : Fragment(), OnCellClicked, OnCellClickedRegion {
    private lateinit var binding: FragmentUserAgreementBinding
    private lateinit var adapter: UserAgreementAdapter
    private var myDialog: DatePickerDialog? = null
    private lateinit var regionViewModel: RegionViewModel
    private lateinit var countryListingAdapter: CountryListingAdapter
    private lateinit var stateListingAdapter: StateListingAdapter
    private lateinit var builder: AlertDialog

    companion object {
        const val PERMISSION_REQUEST_CODE = 200
    }

    private var profileImagePath: String? = null


    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let { imageUri ->
                // Suppose you have an ImageView that should contain the image:
                binding.imgUPloadID1.visibility = View.VISIBLE
                binding.imgUPloadID1.setLocalImage(imageUri, false)
                profileImagePath = getFileFromUri(imageUri).absolutePath

            }
        }


    private val startForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            //  you will get result here in result.data
            binding.imgUPloadID1.visibility = View.VISIBLE
            binding.imgUPloadID1.setImageBitmap(result.data?.extras?.get("data") as Bitmap)

        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.fragment_user_agreement,
            container,
            false
        )
        setUpToolBar()
        setUPSignaturePad()
        setSpannableText()
        setUPDatePicker()
        setUPDialogs()
        changeLayout()
        setUPAdapter()
        setupNavigation()
        uploadPhotoID()
        enableNavigation()
        return binding.root
    }


    //setting up the toolbar
    private fun setUpToolBar() {
        binding.inUserAgreementBar.imgBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.inUserAgreementBar.txtToolbarHeader.setText(R.string.terms_and_conditions)

    }

    //setting up the signature pad
    private fun setUPSignaturePad() {
        binding.imgUAClearPad.setOnClickListener {
            binding.uaSignaturePad.clear()
        }
    }

    //setting up the spanning text
    private fun setSpannableText() {
        binding.txtRentalText.movementMethod = LinkMovementMethod.getInstance()
        val signUp: ClickableSpan = object : ClickableSpan() {
            override fun updateDrawState(ds: TextPaint) {
                ds.isUnderlineText = false
            }

            override fun onClick(p0: View) {
                findNavController().navigate(R.id.action_userAgreementFragment_to_termsFragment)
            }
        }

        val spannable =
            SpannableString(resources.getString(R.string.i_have_read_and_accept_the_terms_and_conditions_provided))
        spannable.setSpan(
            signUp,
            27,
            47,
            Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )
        spannable.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.bg_button)),
            27,
            47,
            Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )
        binding.txtRentalText.text = spannable
    }

    //setting up the dialogs
    private fun setUPDialogs() {
        binding.txtPhotoIDDialog.setOnClickListener {
            openDialog()
        }
        binding.edtUACountry.setOnClickListener {
            openDialogCountry(binding.edtUACountry.text.toString())
        }
        binding.edtUAState.setOnClickListener {
            openDialogState(binding.edtUAState.text.toString())
        }
    }

    //dialog for photo id
    @SuppressLint("MissingInflatedId")
    private fun openDialog() {
        val builder = AlertDialog.Builder(requireContext())
            .create()
        builder.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val view = layoutInflater.inflate(R.layout.dialog_upload_photo_id, null)
        builder.setView(view)
        val close = view.findViewById<TextView>(R.id.txtUACancel)
        val closeID = view.findViewById<ImageView>(R.id.ImgUAClose)
        val btnClose: Button = view.findViewById(R.id.btnUAUploadID)

        closeID.setOnClickListener {
            builder.dismiss()
        }
        close.setOnClickListener {
            builder.dismiss()
        }
        builder.setCanceledOnTouchOutside(false)
        builder.show()
    }

    //setting up date picker for birth day
    private fun setUPDatePicker() {
        val cal = Calendar.getInstance()
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                view.maxDate = System.currentTimeMillis()
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val myFormat = "dd/MM/yyyy"
                val sdf = SimpleDateFormat(myFormat, Locale.US)
                binding.edtUABirthDate.setText(sdf.format(cal.time))
            }

        binding.edtUABirthDate.setOnClickListener {

            myDialog = DatePickerDialog(
                requireContext(), dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            )
            if (cal.get(Calendar.YEAR) <= 1999) {
                binding.rbAgeChecker.isChecked = true
            }
            myDialog?.datePicker?.maxDate = System.currentTimeMillis()
            if (!myDialog?.isShowing!!) {
                myDialog!!.show()
            }

        }

    }


    //changing layout according to user
    private fun changeLayout() {
        binding.rgUserSelector.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == binding.rbResident.id) {
                binding.gpGuest.visibility = View.GONE
                binding.gpResident.visibility = View.VISIBLE

            } else {
                binding.gpGuest.visibility = View.VISIBLE
                binding.gpResident.visibility = View.GONE

            }
        }
    }

    //setting up the adapter
    @SuppressLint("NotifyDataSetChanged")
    private fun setUPAdapter() {
        adapter = UserAgreementAdapter(this)
        binding.rvGuestProperty.adapter = adapter
        val list = ArrayList<GuestModel>()
        list.add(GuestModel(R.drawable.ic_hotel_property, "Hotel/Resort"))
        list.add(GuestModel(R.drawable.ic_residential_property, "Residential"))
        list.add(GuestModel(R.drawable.ic_commerical_property, "Commercial/Office"))
        adapter.setList(list)
        adapter.notifyDataSetChanged()
    }

    //setting up the navigation
    private fun setupNavigation() {
        binding.btnChangeProperty.setOnClickListener {
            findNavController().navigate(UserAgreementFragmentDirections.actionUserAgreementFragmentToSelectProgramFragment())
        }
        binding.btnUASubmit.setOnClickListener {
            findNavController().navigate(UserAgreementFragmentDirections.actionUserAgreementFragmentToLicenceAgreementFragment())
        }
    }

    //fun for recycler view click event
    override fun isClicked(data: Int) {
        val bundle = Bundle()
        bundle.putInt("POSITION", data)
        findNavController().navigate(
            R.id.action_userAgreementFragment_to_guestPropertySelectionFragment,
            bundle
        )

    }

    private fun enableNavigation() {
        val args = this.arguments
        Log.d("TS-ARGS", "${args?.getString("VehicleSlug")}")
        if (args != null) {

            when (args.getString("VehicleSlug")) {
                "eCar" -> {
                    binding.btnUASubmit.isClickable = true
                    binding.btnUASubmit.alpha = 1.0f
                }
                else -> {
                    binding.btnUASubmit.isClickable = false
                    binding.btnUASubmit.alpha = 0.5f

                }
            }
        }
    }

    //opening dialog for country list
    private fun openDialogCountry(mCountry: String) {
        builder = AlertDialog.Builder(requireContext()).create()
        builder.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val view = layoutInflater.inflate(R.layout.dialog_country_picker, null)
        builder.setView(view)
        countryListingAdapter = CountryListingAdapter(this, mCountry)
        val recycler = view.findViewById<RecyclerView>(R.id.rvCountryList)
        recycler.adapter = countryListingAdapter
        builder.setCanceledOnTouchOutside(false)
        builder.show()
        getCountryData()

    }

    private fun getViewModel(): RegionViewModel {
        regionViewModel = ViewModelProvider(this)[RegionViewModel::class.java]
        return regionViewModel
    }

    // getting country data from the api
    @SuppressLint("NotifyDataSetChanged")
    private fun getCountryData() {

        regionViewModel = getViewModel()
        regionViewModel.getCountryRequest()
        regionViewModel.responseLiveDataCountry.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer { state ->
                if (state == null) {
                    return@Observer
                }
                when (state) {
                    is ResponseHandler.Loading -> {
                        Log.d("ViewTripFragment", "setObserverData: $state")
                    }
                    is ResponseHandler.OnFailed -> {
                        Log.d("ViewTripFragment", "setObserverData: $state")

                    }
                    is ResponseHandler.OnSuccessResponse<ResponseListData<CountryResponse>?> -> {
                        Log.d("ViewTripFragment", "setObserverData: ${state.response?.data}")
                        if (state.response?.code == 200) {
                            countryListingAdapter.setList(state.response.data as ArrayList<CountryResponse>)
                            countryListingAdapter.notifyDataSetChanged()
                        }
                    }
                }
            })

    }


    override fun isClickedCountry(data: String, position: Int) {
        binding.edtUACountry.setText(data)
        binding.edtUAState.setText("")
        builder.dismiss()
    }

    //uploading photo id
    private fun uploadPhotoID() {
        binding.btnUploadID.setOnClickListener {
            cameraDialog()
        }
    }

    private fun getFileFromUri(uri: Uri): File {
        val storageDir: File? =
            requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val file =
            File.createTempFile("Img_", ".png", storageDir)
        file.outputStream().use {
            val inputStream = requireContext().contentResolver.openInputStream(uri)
            inputStream?.copyTo(it)
            inputStream?.close()
        }

        return file
    }

    // getting country data from the api
    @SuppressLint("NotifyDataSetChanged")
    private fun getStateData() {
        regionViewModel = getViewModel()
        regionViewModel.getStateRequest(StateRequest(binding.edtUACountry.text.toString()))
        regionViewModel.responseLiveDataState.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer { state ->
                if (state == null) {
                    return@Observer
                }
                when (state) {
                    is ResponseHandler.Loading -> {
                        Log.d("ViewTripFragment", "setObserverData: $state")
                    }
                    is ResponseHandler.OnFailed -> {
                        Log.d("ViewTripFragment", "setObserverData: $state")

                    }
                    is ResponseHandler.OnSuccessResponse<ResponseListData<StateResponse>?> -> {
                        Log.d("ViewTripFragment", "setObserverData: ${state.response?.data}")
                        if (state.response?.code == 200) {
                            stateListingAdapter.setList(
                                state.response.data?.getOrNull(0)?.stateList?.getOrNull(
                                    0
                                )?.states as ArrayList<String?>
                            )
                            stateListingAdapter.notifyDataSetChanged()
                        }
                    }
                }
            })
    }

    //opening dialog for state list
    private fun openDialogState(mState: String) {
        builder = AlertDialog.Builder(requireContext())
            .create()
        builder.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val view = layoutInflater.inflate(R.layout.dialog_state_listing, null)
        builder.setView(view)
        stateListingAdapter = StateListingAdapter(mState, this)
        val recycler = view.findViewById<RecyclerView>(R.id.rvStateList)
        recycler.adapter = stateListingAdapter
        builder.setCanceledOnTouchOutside(false)
        builder.show()
        getStateData()
    }

    private fun cameraDialog() {
        builder = AlertDialog.Builder(requireContext())
            .create()
        builder.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val view = layoutInflater.inflate(R.layout.dialog_camera, null)
        val camera = view.findViewById<TextView>(R.id.txtDCamera)
        val gallery = view.findViewById<TextView>(R.id.txtDGallery)

        camera.setOnClickListener {
            checkPermission()
            builder.dismiss()
        }
        gallery.setOnClickListener {
            getContent.launch("image/*")
            builder.dismiss()
        }
        builder.setView(view)
        builder.setCanceledOnTouchOutside(false)
        builder.show()
    }

    private fun checkPermission(): Boolean {
        val cameraPermission = ContextCompat.checkSelfPermission(
            requireActivity(),
            Manifest.permission.CAMERA
        )
        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                arrayOf(Manifest.permission.CAMERA),
                PERMISSION_REQUEST_CODE
            )
            return false
        } else {
            imageTakeFromCamera()
        }
        return true
    }

    private fun showMessageOKCancel(okListener: DialogInterface.OnClickListener) {
        AlertDialog.Builder(requireContext())
            .setMessage("You need to allow access permissions")
            .setPositiveButton("OK", okListener)
            .setNegativeButton("Cancel", null)
            .create()
            .show()
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                imageTakeFromCamera()

            } else {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(
                            requireActivity(),
                            Manifest.permission.CAMERA
                        )
                    ) {
                        showMessageOKCancel { _, _ ->

                        }
                    } else {
                        showMessageOKCancel { _, _ ->

                        }
                    }
                }
            }
        }
    }

    private fun imageTakeFromCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startForResult.launch(intent)
    }


    override fun isClickedState(data: String, position: Int) {
        binding.edtUAState.setText(data)
        builder.dismiss()
    }


}



