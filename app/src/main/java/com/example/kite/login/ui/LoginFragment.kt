package com.example.kite.login.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.kite.R
import com.example.kite.constants.Constants
import com.example.kite.databinding.FragmentLoginBinding
import com.example.kite.login.repository.LoginRepository
import com.example.kite.login.viewmodel.LoginVMFFactory
import com.example.kite.login.viewmodel.LoginViewModel
import com.example.kite.network.ApiInterface
import com.example.kite.network.RetrofitHelper


class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.fragment_login,
            container,
            false
        )
        // Inflate the layout for this fragment
        loadFragment()
        getRetrofitData()
        return binding.root
    }

    private fun loadFragment() {
        binding.txtForgetPassword.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgetPasswordFragment)
        }
        binding.txtAccountCreate.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }
        binding.imgBack.setOnClickListener {
            //requireActivity().onBackPressed()
            //Navigation.findNavController(requireActivity(),R.id.welcomeFragment)
            findNavController().navigateUp()
        }
    }

    private fun getRetrofitData() {
        val loginService =
            RetrofitHelper.getInstance(Constants.BASE_URL).create(ApiInterface::class.java)
        val repository = LoginRepository(loginService)
        viewModel =
            ViewModelProvider(this, LoginVMFFactory(repository))[LoginViewModel::class.java]

        binding.loginData = viewModel
        binding.lifecycleOwner = this
        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { it1 ->
                Toast.makeText(requireContext(), it1, Toast.LENGTH_SHORT).show()
            }
        }
    }


}