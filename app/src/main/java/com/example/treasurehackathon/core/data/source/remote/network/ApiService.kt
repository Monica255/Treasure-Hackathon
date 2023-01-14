package com.example.treasurehackathon.core.data.source.remote.network

import com.example.treasurehackathon.core.data.source.remote.model.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("contributor/signin")
    suspend fun login(@Body requestLogin: RequestLogin): AccountResponse

    @GET("contributor/products")
    suspend fun getProducts():List<ProductResponse>

    @POST("contributor/purchase/{contributorId}")
    suspend fun purchase(
        @Path("contributorId") contributorId:String,
        @Body requestPurchase: RequestPurchase
    ):ResponsePurchase
}