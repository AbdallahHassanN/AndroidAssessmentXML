package com.example.technical_assignment_xml.network

import com.example.technical_assignment_xml.domain.models.StoreItem
import retrofit2.Response
import retrofit2.http.GET


interface ShopService {
    @GET("products")
    suspend fun getAllItems()
            : Response<List<StoreItem>>
}