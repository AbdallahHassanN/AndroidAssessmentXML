package com.example.technical_assignment_xml.domain.repository

import com.example.technical_assignment_xml.domain.models.StoreItem


interface Repository {

    suspend fun getAllItemsFromApi()
            : List<StoreItem>

    suspend fun saveItemsToDb(storeItemList: List<StoreItem>)
    suspend fun getAllItemsFromDb()
            : List<StoreItem>
}