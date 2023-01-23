package com.example.kite.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.kite.R
import com.example.kite.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

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
        setUpDrawer()
        setUpNavigation()
        return binding.root
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


}