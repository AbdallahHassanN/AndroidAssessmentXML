package com.example.technical_assignment_xml.presentation.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.technical_assignment_xml.domain.models.StoreItem
import com.example.technical_assignment_xml.network.response.Resource
import com.example.technical_assignment_xml.domain.useCases.GetAllItemsUseCase
import com.example.technical_assignment_xml.presentation.common.Constants.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoreAppListViewModel
@Inject constructor(
    private val getAllItemsUseCase: GetAllItemsUseCase
) : ViewModel() {
    val allItems: MutableLiveData<Resource<List<StoreItem>>> = MutableLiveData()
    init {
        getAllItems()
    }

    private fun getAllItems() = viewModelScope.launch {
        allItems.postValue(Resource.Loading())
        getAllItemsUseCase.execute(
        ).catch {
            Log.d(TAG, "Error ${it.message}")
        }.collect { response ->
            when (response) {
                is Resource.Error -> {
                    allItems.postValue(Resource.Error(response.message!!))
                }

                is Resource.Loading -> {
                    allItems.postValue(Resource.Loading())
                }

                is Resource.Success -> {
                    allItems.postValue(Resource.Success(response.data!!))
                }
            }
        }
    }
}

