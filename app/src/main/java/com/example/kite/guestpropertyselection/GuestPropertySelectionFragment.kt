package com.example.kite.guestpropertyselection

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
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
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.updateLayoutParams
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.kite.R
import com.example.kite.databinding.FragmentGuestPropertySelectionBinding
import com.example.kite.extensions.onRightDrawableClicked
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.text.SimpleDateFormat
import java.util.*

class GuestPropertySelectionFragment : Fragment() {

    private lateinit var binding: FragmentGuestPropertySelectionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.fragment_guest_property_selection,
            container,
            false
        )
        getData()
        setNavigation()
        setUPSignaturePad()
        setSpannableText()
        setUPDatePicker()
        setUPDialogs()
        return binding.root
    }


    //dialog for no access code
    private fun openBottomSheet() {
        val dialog = BottomSheetDialog(requireContext())
        val view = layoutInflater.inflate(R.layout.dialog_no_code, null)
        val back = view.findViewById<ImageView>(R.id.imgNCBack)
        val close = view.findViewById<Button>(R.id.btnNCClose)
        dialog.setCancelable(false)
        dialog.setContentView(view)
        dialog.show()
        back.setOnClickListener { dialog.dismiss() }
        close.setOnClickListener { dialog.dismiss() }

    }

    //getting data from bundle
    private fun getData() {
        val args = this.arguments
        if (args != null) {

            when (args.getInt("POSITION")) {
                0 -> {
                    binding.imgGTopToolbar.setImageResource(R.drawable.ic_hotel_property)
                    binding.txtGPName.setText(R.string.hotel_resort)
                    binding.gpHotel.visibility = View.VISIBLE
                    binding.gpResident.visibility = View.GONE
                }
                1 -> {
                    binding.imgGTopToolbar.setImageResource(R.drawable.ic_residential_property)
                    binding.txtGPName.setText(R.string.residential)
                    binding.txtGCodeText.setText(R.string.residential_text)
                    binding.gpHotel.visibility = View.GONE
                    binding.gpResident.visibility = View.VISIBLE
                    binding.txtGBirthDate.updateLayoutParams<ConstraintLayout.LayoutParams> {
                        topToBottom = binding.txtGNext.id
                    }

                }
                2 -> {
                    binding.imgGTopToolbar.setImageResource(R.drawable.ic_commerical_property)
                    binding.txtGPName.setText(R.string.commercial_office)
                    binding.gpHotel.visibility = View.VISIBLE
                    binding.gpResident.visibility = View.GONE
                }
            }
        }
    }

    private fun setNavigation() {
        binding.imgGBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    //setting up the signature pad
    private fun setUPSignaturePad() {
        binding.imgGClearPad.setOnClickListener {
            binding.gSignaturePad.clear()
        }
    }

    //setting up the spanning text
    private fun setSpannableText() {
        binding.txtGRentalText.movementMethod = LinkMovementMethod.getInstance()
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
        binding.txtGRentalText.text = spannable
    }

    //setting up the dialogs
    private fun setUPDialogs() {
        binding.txtGPhotoIDDialog.setOnClickListener {
            openPhotoDialog()
        }
        binding.btnGCode.setOnClickListener {
            openBottomSheet()
        }
        binding.edtGAccessCode.onRightDrawableClicked {
            openAccessCodeDialog()
        }
        binding.edtGCountry.setOnClickListener {
            // openDialogCountry()
        }

    }

    //dialog for photo id
    private fun openPhotoDialog() {
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

    //dialog for Access code
    private fun openAccessCodeDialog() {
        val builder = AlertDialog.Builder(requireContext())
            .create()
        builder.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val view = layoutInflater.inflate(R.layout.dialog_access_code, null)
        builder.setView(view)
        val close = view.findViewById<TextView>(R.id.txtGCancel)
        val closeID = view.findViewById<ImageView>(R.id.ImgGClose)
        val btnClose: Button = view.findViewById(R.id.btnGURequestCode)

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
    @SuppressLint("SimpleDateFormat")
    private fun setUPDatePicker() {
        val cal = Calendar.getInstance()
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val myFormat = "dd/MM/yyyy"
                val sdf = SimpleDateFormat(myFormat)
                binding.edtGBirthDate.setText(sdf.format(cal.time))
            }

        binding.edtGBirthDate.setOnClickListener {
            val myDialog = DatePickerDialog(
                requireContext(), dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            )
            myDialog.datePicker.maxDate = System.currentTimeMillis()
            myDialog.show()
        }

    }
}