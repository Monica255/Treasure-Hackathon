package com.example.treasurehackathon.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.treasurehackathon.R
import com.example.treasurehackathon.ui.home.donatur.HomeDonaturActivity
import com.example.treasurehackathon.ui.home.donatur.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransactionFragment : Fragment() {

    override fun onStart() {
        super.onStart()
        if (isAdded) {
            val activity = activity as HomeDonaturActivity
            activity.state = Screen.TRANSACTION
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transaction, container, false)
    }

}