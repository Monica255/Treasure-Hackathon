package com.example.treasurehackathon.core.data.source.remote.network

import android.util.Log
import com.example.treasurehackathon.core.data.Resource
import com.example.treasurehackathon.core.data.source.local.model.ProductEntity
import com.example.treasurehackathon.core.data.source.remote.model.*
import com.example.treasurehackathon.core.utils.DataMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun purchase(
        id: String,
        requestPurchase: RequestPurchase
    ): Flow<ApiResponse<ResponsePurchase>> {
        return flow {
            try {
                Log.d("TAG",id.toString())
                val response = apiService.purchase(id,requestPurchase)
                if (response.message.contains("success",true)) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun loginWithEmailPassword(
        email: String,
        password: String
    ): Flow<ApiResponse<AccountResponse>> {
        //get data from remote api
        return flow {
            try {
                val reqLogin = RequestLogin(email, password)
                val response = apiService.login(reqLogin)
                if (response.id_contributor != "") {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getAllProduct(): Flow<ApiResponse<List<ProductResponse>>> {
        //get data from remote api
        return flow {
            try {
                val response = apiService.getProducts()
                if (response.isNotEmpty()) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}