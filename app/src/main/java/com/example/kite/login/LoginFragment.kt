package com.example.kite.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.kite.R
import com.example.kite.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding

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
            requireActivity().onBackPressed()
            //Navigation.findNavController(requireActivity(),R.id.welcomeFragment)
        }
    }


}