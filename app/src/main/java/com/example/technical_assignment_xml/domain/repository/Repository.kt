package com.example.technical_assignment_xml.domain.repository

import com.example.technical_assignment_xml.domain.models.StoreItem
import com.example.technical_assignment_xml.network.response.Resource
import kotlinx.coroutines.flow.Flow


interface Repository {

    suspend fun getAllItems()
            : Flow<Resource<List<StoreItem>>>
}