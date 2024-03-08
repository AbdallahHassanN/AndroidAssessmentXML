package com.example.technical_assignment_xml.domain.useCases

import android.content.Context
import android.util.Log
import com.example.technical_assignment_xml.network.response.Resource
import com.example.technical_assignment_xml.domain.repository.Repository
import com.example.technical_assignment_xml.presentation.common.Constants.TAG
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetAllItemsUseCase
@Inject constructor(
    private val repo: Repository,
    @ApplicationContext private val context: Context
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun execute(
    )  = repo.getAllItems()
        .flatMapConcat { response ->
            when(response)  {
                is Resource.Error -> {
                    Log.d(TAG,"UseCase Error ? ${response.message.toString()}")
                    flowOf(Resource.Error(response.message.toString()))
                }
                is Resource.Loading -> {
                    Log.d(TAG,"UseCase Loading ?")
                    flowOf(Resource.Loading())
                }
                is Resource.Success -> {
                    Log.d(TAG,"UseCase Success ${response.data}")
                    flowOf(Resource.Success(response.data!!))
                }
            }
        }
}