package com.example.treasurehackathon.core.data.source.local.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "session")
data class AccountEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    var id:String,
    @ColumnInfo(name = "email")
    var email:String,
    @ColumnInfo(name = "password")
    var username:String,
    @ColumnInfo(name = "role")
    var role:String
)
