package com.example.treasurehackathon.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.treasurehackathon.core.domain.model.Account
import com.example.treasurehackathon.core.domain.usercase.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val useCase: UseCase):ViewModel(){
    val getAllProducts=useCase.getAllProduct().asLiveData()
}