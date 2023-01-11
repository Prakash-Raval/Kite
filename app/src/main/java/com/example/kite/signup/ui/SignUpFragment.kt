package com.example.kite.signup.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.kite.R
import com.example.kite.databinding.FragmentSignUpBinding
import com.example.kite.signup.viewmodel.SignUpViewModel


class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    private lateinit var viewModel: SignUpViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.fragment_sign_up,
            container,
            false
        )
        // Inflate the layout for this fragment
        countryList()
        loadFragment()
        getSignUpData()
        return binding.root
    }

    private fun countryList() {
        val feelings = resources.getStringArray(R.array.country_list)
        binding.edtCountry.adapter =
            activity?.let { ArrayAdapter(it, R.layout.dropdown_item, feelings) }
    }

    private fun loadFragment() {
        binding.imgBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun getSignUpData() {
//        val signUpService =
//            RetrofitHelper.getInstance(Constants.BASE_URL).create(ApiInterface::class.java)
//        val repository = SignUpRepository(signUpService)
//        viewModel =
//            ViewModelProvider(this, SignUpViewModelFactory(repository))[SignUpViewModel::class.java]
//
//        viewModel.signUpLiveData.observe(viewLifecycleOwner) {
//        }
    }
}

