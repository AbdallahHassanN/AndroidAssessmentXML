package com.example.technical_assignment_xml.repository

import android.content.Context
import android.util.Log
import com.example.technical_assignment_xml.presentation.common.handleResponse
import com.example.technical_assignment_xml.db.StoreItemDao
import com.example.technical_assignment_xml.domain.models.StoreItem
import com.example.technical_assignment_xml.domain.repository.Repository
import com.example.technical_assignment_xml.network.ShopService
import com.example.technical_assignment_xml.network.response.Resource
import com.example.technical_assignment_xml.presentation.common.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.net.UnknownHostException


class RepositoryImpl(
    private val shopService: ShopService,
    private val appContext: Context,
    private val dao: StoreItemDao
) : Repository {
    override suspend fun getAllItems()
            : Flow<Resource<List<StoreItem>>> {
        return try {
            val response = shopService.getAllItems()
            if (response.isSuccessful) {
                dao.upsertStoreItemsList(response.body()!!)
            }
            handleResponse(response, appContext)
        } catch (e: UnknownHostException) {
            flow {
                val itemsLocal = dao.getStoreItems()
                if (itemsLocal.isNotEmpty()) {
                    emit(Resource.Success(itemsLocal))
                } else {
                    emit(
                        Resource.Error("No internet connection and No cached data." +
                            " Please connect to the internet"))
                }
            }
        } catch (e: Exception) {
            flow {
                emit(Resource.Error("An unexpected error occurred"))
                Log.d(Constants.TAG, e.message.toString())
            }
        }
    }
}