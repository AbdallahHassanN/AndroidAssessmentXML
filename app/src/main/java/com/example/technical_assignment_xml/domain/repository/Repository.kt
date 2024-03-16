package com.example.technical_assignment_xml.domain.repository

import com.example.technical_assignment_xml.domain.models.StoreItem


interface Repository {

    suspend fun getAllItems()
            : List<StoreItem>

    suspend fun saveItems(storeItemList: List<StoreItem>)
    suspend fun getItems()
            : List<StoreItem>
}