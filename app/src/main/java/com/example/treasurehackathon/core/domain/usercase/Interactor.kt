package com.example.treasurehackathon.core.domain.usercase

import com.example.treasurehackathon.core.data.Repository
import com.example.treasurehackathon.core.data.Resource
import com.example.treasurehackathon.core.data.source.remote.model.RequestPurchase
import com.example.treasurehackathon.core.data.source.remote.model.ResponsePurchase
import com.example.treasurehackathon.core.domain.model.Account
import com.example.treasurehackathon.core.domain.model.Product
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Interactor @Inject constructor(private val repository: Repository) : UseCase {
    override fun loginWithId(id: String) = repository.loginWithId(id)
    override fun loginWithEmailPassword2(email: String, password: String) =
        repository.loginWithEmailPassword2(email, password)

    override fun loginWithEmailPassword(email: String, password: String) =
        repository.loginWithEmailPassword(email, password)

    override fun getAllProduct(): Flow<Resource<List<Product>>> = repository.getAllProducts()
    override fun purchase(
        id: String,
        requestPurchase: RequestPurchase
    ) = repository.purchase(id, requestPurchase)
}