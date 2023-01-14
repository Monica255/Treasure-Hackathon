package com.example.treasurehackathon.core.data.source.local.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    var id_product: String,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "price")
    var prize: Int,
    @ColumnInfo(name = "id_seller")
    var id_seller: String,
    @ColumnInfo(name = "username")
    var username: String,
    @ColumnInfo(name = "description")
    var desc: String,
    @ColumnInfo(name = "quantity")
    var qty:Int
)