package com.example.treasurehackathon.core.data.source.remote.model

import com.google.gson.annotations.SerializedName

data class RequestLogin(
    var email:String,
    var password:String
)
