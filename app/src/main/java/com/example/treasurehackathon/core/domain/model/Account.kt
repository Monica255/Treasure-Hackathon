package com.example.treasurehackathon.core.domain.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import kotlinx.parcelize.Parcelize

@Parcelize
data class Account(
    var id:String,
    var email:String,
    var name:String,
    var role:String
):Parcelable
