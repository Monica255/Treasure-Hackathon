package com.example.treasurehackathon.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.treasurehackathon.core.domain.usercase.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val useCase: UseCase):ViewModel(){
    fun loginWithId(id:String)=useCase.loginWithId(id)
    fun loginWithEmailPassword2(email:String,password:String)=useCase.loginWithEmailPassword2(email, password).asLiveData()

}