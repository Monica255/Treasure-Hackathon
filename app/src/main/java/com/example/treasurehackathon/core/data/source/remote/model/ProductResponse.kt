package com.example.treasurehackathon.core.data.source.remote.model

data class ProductResponse(
    var id_product: String,
    var name: String,
    var prize: Int,
    var id_seller: String,
    var username:String,
    var desc: String,
    var qty:Int
)