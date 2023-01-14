package com.example.treasurehackathon.core.data.source.remote.model

import com.google.gson.annotations.SerializedName

data class AccountResponse(
    @field:SerializedName("id_contributor")
    var id_contributor:String,
    @field:SerializedName("username")
    var username:String,
    @field:SerializedName("email")
    var email:String,
    @field:SerializedName("role")
    var role:String
)
