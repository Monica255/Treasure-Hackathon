package com.example.treasurehackathon.ui.home.donatur

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.treasurehackathon.core.data.source.remote.model.RequestPurchase
import com.example.treasurehackathon.core.domain.usercase.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailDonateViewModel @Inject constructor(private val useCase: UseCase) : ViewModel() {
    var totalItem = 0
        set(value) {
            itemCount.value = if (value >= 5) 5 else value
            field = value
        }
    var itemPrice = 0
    val itemCount = MutableLiveData<Int>()

    fun purchase(id: String,requestPurchase: RequestPurchase)=useCase.purchase(id, requestPurchase).asLiveData()
}