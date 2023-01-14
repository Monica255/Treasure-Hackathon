package com.example.treasurehackathon.core.data.source.local.room

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.treasurehackathon.core.data.source.local.model.AccountEntity
import com.example.treasurehackathon.core.data.source.local.model.ProductEntity
import kotlinx.coroutines.flow.Flow

@androidx.room.Dao
interface Dao {
    @Query("SELECT * FROM session WHERE id= :id")
    fun loginWithId(id:String): List<AccountEntity>

    @Query("SELECT * FROM session WHERE email= :email And password= :password")
    fun loginWithEmailPassword(email:String,password:String): Flow<List<AccountEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSession(data: AccountEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<ProductEntity>)

    @Query("SELECT * FROM products")
    fun getAllProduct(): Flow<List<ProductEntity>>
}