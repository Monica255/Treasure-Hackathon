package com.example.treasurehackathon.core.domain.usercase

import com.example.treasurehackathon.core.data.Resource
import com.example.treasurehackathon.core.data.source.remote.model.RequestPurchase
import com.example.treasurehackathon.core.data.source.remote.model.ResponsePurchase
import com.example.treasurehackathon.core.domain.model.Account
import com.example.treasurehackathon.core.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface UseCase {
    fun loginWithId(id:String): List<Account>
    fun loginWithEmailPassword2(email:String,password:String): Flow<Resource<Account>>

    fun loginWithEmailPassword(email:String,password:String): Flow<Resource<List<Account>>>
    fun getAllProduct():Flow<Resource<List<Product>>>
    fun purchase(id: String,requestPurchase: RequestPurchase):Flow<Resource<ResponsePurchase>>
}