package com.example.treasurehackathon.core.data.source.local

import com.example.treasurehackathon.core.data.source.local.model.AccountEntity
import com.example.treasurehackathon.core.data.source.local.model.ProductEntity
import com.example.treasurehackathon.core.data.source.local.room.Dao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val dao: Dao) {
    fun loginWithId(id:String)=dao.loginWithId(id)

    fun loginWithEmailPassword(email:String,password:String)=dao.loginWithEmailPassword(email,password)

    suspend fun insertSession(data:AccountEntity)=dao.insertSession(data)

    fun getAllProducts(): Flow<List<ProductEntity>> = dao.getAllProduct()

    suspend fun insertProducts(tourismList: List<ProductEntity>) = dao.insertProducts(tourismList)
}