package com.example.treasurehackathon.core.utils

import com.example.treasurehackathon.core.data.source.local.model.AccountEntity
import com.example.treasurehackathon.core.data.source.local.model.ProductEntity
import com.example.treasurehackathon.core.data.source.remote.model.AccountResponse
import com.example.treasurehackathon.core.data.source.remote.model.ProductResponse
import com.example.treasurehackathon.core.domain.model.Account
import com.example.treasurehackathon.core.domain.model.Product

object DataMapper {
    fun entitiesToDomain(data: List<AccountEntity>): List<Account> {
        return data.map {
            Account(
                id = it.id,
                email = it.email,
                name = it.username,
                role = it.role
            )
        }
    }

    fun entityToDomain(it: AccountEntity) = Account(
        id = it.id,
        email = it.email,
        name = it.username,
        role = it.role
    )

    fun responseToEntities(it: AccountResponse) = AccountEntity(
        id = it.id_contributor,
        email = it.email,
        username = it.username,
        role = it.role
    )

    fun responseToDomain(it: AccountResponse) = Account(
        id = it.id_contributor,
        email = it.email,
        name = it.username,
        role = it.role
    )

    fun productResponsesToEntities(data: List<ProductResponse>): List<ProductEntity> {
        val list = ArrayList<ProductEntity>()
        data.map {
            val product = ProductEntity(
                id_product = it.id_product,
                name = it.name,
                prize = it.prize,
                id_seller = it.id_seller,
                desc = it.desc,
                qty = it.qty,
                username = it.username
            )
            list.add(product)
        }
        return list
    }

    fun productEntitiesToDomain(it: List<ProductEntity>) = it.map {
        Product(
            id_product = it.id_product,
            name = it.name,
            prize = it.prize,
            id_seller = it.id_seller,
            desc = it.desc,
            qty = it.qty,
            username = it.username
        )
    }

    fun totalPriceToPrice(data:Int,qty:Int)=data/qty
}