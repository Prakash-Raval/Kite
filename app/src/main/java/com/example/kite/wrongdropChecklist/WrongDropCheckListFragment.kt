package com.example.kite.wrongdropChecklist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.kite.R
import com.example.kite.databinding.FragmentWrongDropCheckListBinding


class WrongDropCheckListFragment : Fragment() {

    private lateinit var binding: FragmentWrongDropCheckListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.fragment_wrong_drop_check_list,
            container,
            false
        )
        setUPToolbar()
        return binding.root
    }


    private fun setUPToolbar() {
        binding.inWDCBar.imgBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.inWDCBar.txtToolbarHeader.setText(R.string.wrong_drop_check_list)
    }
}