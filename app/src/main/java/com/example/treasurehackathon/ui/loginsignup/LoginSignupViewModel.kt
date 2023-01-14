package com.example.treasurehackathon.ui.loginsignup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.treasurehackathon.core.domain.usercase.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginSignupViewModel @Inject constructor(private val useCase: UseCase):ViewModel() {
        fun loginWithEmailPassword2(email:String,password:String)=useCase.loginWithEmailPassword2(email, password).asLiveData()

        fun loginWithEmailPassword(email:String,password:String)=useCase.loginWithEmailPassword(email, password).asLiveData()
}