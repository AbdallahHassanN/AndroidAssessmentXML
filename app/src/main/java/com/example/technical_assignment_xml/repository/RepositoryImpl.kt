package com.example.technical_assignment_xml.repository

import android.content.Context
import com.example.technical_assignment_xml.db.StoreItemDao
import com.example.technical_assignment_xml.domain.models.StoreItem
import com.example.technical_assignment_xml.domain.repository.Repository
import com.example.technical_assignment_xml.network.ShopService

class RepositoryImpl(
    private val shopService: ShopService,
    private val appContext: Context,
    private val dao: StoreItemDao
) : Repository {
    override suspend fun getAllItemsFromApi()
            : List<StoreItem> {
        return shopService.getAllItems()
    }
    override suspend fun saveItemsToDb(storeItemList: List<StoreItem>) {
        dao.upsertStoreItemsList(storeItemList)
    }
    override suspend fun getAllItemsFromDb()
            : List<StoreItem> {
        return dao.getStoreItems()
    }
}