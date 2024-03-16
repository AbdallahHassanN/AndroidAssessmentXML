package com.example.technical_assignment_xml.domain.useCases

import android.content.Context
import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.example.technical_assignment_xml.domain.models.StoreItem
import com.example.technical_assignment_xml.network.response.Resource
import com.example.technical_assignment_xml.domain.repository.Repository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetAllItemsUseCase
@Inject constructor(
    private val repo: Repository,
    @ApplicationContext private val context: Context
) {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun execute()
            : Flow<Resource<List<StoreItem>>> = flow {
        try {
            emit(Resource.Loading())
            val response = repo.getAllItemsFromApi()
            repo.saveItemsToDb(response)
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            emit(Resource.Error("An unexpected error occurred"))
        } catch (e: IOException) {
            if (repo.getAllItemsFromDb().isNotEmpty()) {
                emit(Resource.Success(repo.getAllItemsFromDb()))
            } else {
                emit(
                    Resource.Error(
                        "No internet connection and No cached data." +
                                " Please connect to the internet"
                    )
                )
            }
        }
    }
}