package com.example.treasurehackathon.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    var id_product: String,
    var name: String,
    var prize: Int,
    var id_seller: String,
    var username:String,
    var desc: String,
    var qty:Int
):Parcelable
