package com.example.treasurehackathon.core.data.source.remote.model

data class RequestPurchase(
    var qty:Int,
    var prize:Int,
    var id_seller:String,
    var id_product:String
)
