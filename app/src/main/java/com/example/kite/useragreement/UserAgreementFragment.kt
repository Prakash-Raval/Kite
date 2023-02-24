package com.example.kite.useragreement

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.kite.R
import com.example.kite.bikelisting.adapter.OnCellClicked
import com.example.kite.constants.Constants
import com.example.kite.countrylisting.*
import com.example.kite.databinding.FragmentUserAgreementBinding
import com.example.kite.network.ApiInterface
import com.example.kite.network.RetrofitHelper
import com.example.kite.useragreement.adapter.UserAgreementAdapter
import com.example.kite.useragreement.model.GuestModel
import com.github.dhaval2404.imagepicker.ImagePicker
import java.text.SimpleDateFormat
import java.util.*


class UserAgreementFragment : Fragment(), OnCellClicked, OnCellClickedCountry {
    private lateinit var binding: FragmentUserAgreementBinding
    private lateinit var adapter: UserAgreementAdapter
    private var myDialog: DatePickerDialog? = null
    private lateinit var countryViewModel: CountryViewModel
    private lateinit var countryListingAdapter: CountryListingAdapter
    private lateinit var builder: AlertDialog

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
        countryListingAdapter = CountryListingAdapter(this, "")
        setUpToolBar()
        setUPSignaturePad()
        setSpannableText()
        setUPDatePicker()
        setUPDialogs()
        changeLayout()
        setUPAdapter()
        setupNavigation()
        getCountryData()
        uploadPhotoID()
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
            openDialogCountry()
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

    //opening dialog for country list
    private fun openDialogCountry() {
        builder = AlertDialog.Builder(requireContext()).create()
        builder.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val view = layoutInflater.inflate(R.layout.dialog_country_picker, null)
        builder.setView(view)

        val recycler = view.findViewById<RecyclerView>(R.id.rvCountryList)
        recycler.adapter = countryListingAdapter
        builder.setCanceledOnTouchOutside(false)
        builder.show()

    }

    // getting country data from the api
    @SuppressLint("NotifyDataSetChanged")
    private fun getCountryData() {
        val service =
            RetrofitHelper.getInstance(Constants.BASE_URL).create(ApiInterface::class.java)
        val repository = CountryRepository(service)
        countryViewModel = ViewModelProvider(
            this, CountryVMFactory(repository)
        )[CountryViewModel::class.java]
        countryViewModel.getCountryList()
        countryViewModel.profileLiveData.observe(viewLifecycleOwner) {
            countryListingAdapter.setList(it.countryList as ArrayList<CountryResponse.Country>)
            countryListingAdapter.notifyDataSetChanged()
        }
    }

    override fun isClicked(data: String, position: Int) {
        binding.edtUACountry.setText(data)
        builder.dismiss()
    }

    //uploading photo id
    private fun uploadPhotoID() {

        binding.btnUploadID.setOnClickListener {
          /*  ImagePicker.with(this)
                .crop()                    //Crop image(Optional), Check Customization for more option
                .compress(1024)    //Final image size will be less than 1 MB(Optional)
                .maxResultSize(
                    1080,
                    1080
                )    //Final image resolution will be less than 1080 x 1080(Optional)
                .start(101)*/

            /*resultLauncher.launch()*/
/*
            var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    // There are no request codes
                    val data: Intent? = result.data

                }
            }
*/


        }

    }





}

