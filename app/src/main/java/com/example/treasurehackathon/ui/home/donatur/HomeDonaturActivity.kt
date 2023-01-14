package com.example.treasurehackathon.ui.home.donatur

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.findNavController
import com.example.treasurehackathon.R
import com.example.treasurehackathon.core.domain.model.Account
import com.example.treasurehackathon.core.domain.model.Product
import com.example.treasurehackathon.databinding.ActivityHomeDonaturBinding
import com.example.treasurehackathon.ui.home.HomeViewModel
import com.example.treasurehackathon.ui.splash.SplashActivity
import com.google.android.material.navigation.NavigationBarView
import dagger.hilt.android.AndroidEntryPoint

enum class Screen(){
    DONATE,TRANSACTION,PROFILE
}


@AndroidEntryPoint
class HomeDonaturActivity : AppCompatActivity() {
    private lateinit var binding:ActivityHomeDonaturBinding
    private val viewModel:HomeViewModel by viewModels()
    lateinit var state:Screen
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityHomeDonaturBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navController = findNavController(R.id.frame_container)
        binding.navigation.setOnItemSelectedListener(NavigationBarView.OnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item_donate -> {
                    if (state == Screen.TRANSACTION) {
                        navController.navigate(R.id.action_transactionFragment_to_donateFragment)
                    } else if (state == Screen.PROFILE) {
                        navController.navigate(R.id.action_profileFragment_to_donateFragment)
                    }
                    return@OnItemSelectedListener true
                }
                R.id.item_transaction -> {
                    if (state == Screen.DONATE) {
                        navController.navigate(R.id.action_donateFragment_to_transactionFragment)
                    } else if (state == Screen.PROFILE) {
                        navController.navigate(R.id.action_profileFragment_to_transactionFragment)
                    }
                    return@OnItemSelectedListener true
                }

                R.id.item_profile -> {
                    if (state == Screen.DONATE) {
                        navController.navigate(R.id.action_donateFragment_to_profileFragment)
                    } else if (state == Screen.TRANSACTION) {
                        navController.navigate(R.id.action_transactionFragment_to_profileFragment)
                    }
                    return@OnItemSelectedListener true
                }

            }
            false
        })
    }
}