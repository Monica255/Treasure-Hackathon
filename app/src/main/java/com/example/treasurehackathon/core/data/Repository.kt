package com.example.treasurehackathon.core.data

import android.provider.ContactsContract.Data
import android.util.Log
import com.example.treasurehackathon.core.data.source.local.LocalDataSource
import com.example.treasurehackathon.core.data.source.local.model.AccountEntity
import com.example.treasurehackathon.core.data.source.remote.model.AccountResponse
import com.example.treasurehackathon.core.data.source.remote.model.ProductResponse
import com.example.treasurehackathon.core.data.source.remote.model.RequestPurchase
import com.example.treasurehackathon.core.data.source.remote.model.ResponsePurchase
import com.example.treasurehackathon.core.data.source.remote.network.ApiResponse
import com.example.treasurehackathon.core.data.source.remote.network.RemoteDataSource
import com.example.treasurehackathon.core.domain.model.Account
import com.example.treasurehackathon.core.domain.model.Product
import com.example.treasurehackathon.core.domain.repository.IRepository
import com.example.treasurehackathon.core.utils.DataMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : IRepository {

    override fun loginWithEmailPassword2(email: String, password: String): Flow<Resource<Account>> {
        return flow {
            emit(Resource.Loading())
            val response = remoteDataSource.loginWithEmailPassword(email, password).first()
            when (response) {
                is ApiResponse.Success -> {
                    val s=DataMapper.responseToDomain(response.data)
                    emit(Resource.Success(s))
                }
                is ApiResponse.Empty -> {
                    emit(Resource.Error("Empty"))
                }
                is ApiResponse.Error -> {
                    emit(Resource.Error("Error"))

                }
            }
        }

    }

    override fun loginWithEmailPassword(
        email: String,
        password: String
    ): Flow<Resource<List<Account>>> =
        object : NetworkBoundResource<List<Account>, AccountResponse>() {
            override fun query(): Flow<List<Account>> {
                return localDataSource.loginWithEmailPassword(email, password).map {
                    DataMapper.entitiesToDomain(it)
                }

            }

            override fun shouldFetch(data: List<Account>?): Boolean = true
            //data == null

            override suspend fun fetch(): Flow<ApiResponse<AccountResponse>> =
                remoteDataSource.loginWithEmailPassword(email, password)

            override suspend fun saveFetchResult(data: AccountResponse) {
                val accountData = DataMapper.responseToEntities(data)
                //Log.d("TAG",accountData.toString())
                localDataSource.insertSession(accountData).also {

                }
            }
        }.asFlow()

    override fun loginWithId(id: String): List<Account> =
        DataMapper.entitiesToDomain(localDataSource.loginWithId(id))

    override fun getAllProducts(): Flow<Resource<List<Product>>> =
        object : NetworkBoundResource<List<Product>, List<ProductResponse>>() {
            override fun query(): Flow<List<Product>> {
                return localDataSource.getAllProducts().map {
                    DataMapper.productEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Product>?): Boolean = true
            //data == null || data.isEmpty()
            //true // ganti dengan true jika ingin selalu mengambil data dari internet

            override suspend fun fetch(): Flow<ApiResponse<List<ProductResponse>>> =
                remoteDataSource.getAllProduct().also {
                    Log.d("TAG",it.first().toString())
                }

            override suspend fun saveFetchResult(data: List<ProductResponse>) {
                val list = DataMapper.productResponsesToEntities(data)
                localDataSource.insertProducts(list)
            }
        }.asFlow()

    override fun purchase(
        id: String,
        requestPurchase: RequestPurchase
    ): Flow<Resource<ResponsePurchase>> {
        return flow {
            emit(Resource.Loading())
            val response = remoteDataSource.purchase(id,requestPurchase).first()
            when (response) {
                is ApiResponse.Success -> {
                    emit(Resource.Success(response.data))
                }
                is ApiResponse.Empty -> {
                    emit(Resource.Error("Empty"))
                }
                is ApiResponse.Error -> {
                    emit(Resource.Error("Error"))

                }
            }
        }
    }


}