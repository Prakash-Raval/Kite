package com.example.kite.wrongdropcharge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kite.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class WrongDropChargeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wrong_drop_charge, container, false)
    }


}