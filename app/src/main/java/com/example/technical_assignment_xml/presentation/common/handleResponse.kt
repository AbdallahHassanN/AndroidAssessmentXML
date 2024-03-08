package com.example.technical_assignment_xml.presentation.common

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.example.technical_assignment_xml.network.response.Resource
import com.example.technical_assignment_xml.presentation.common.Constants.TAG
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

fun <T> handleResponse(response: Response<T>, context: Context): Flow<Resource<T>> {


    return flow {
        if (isNetworkConnected(context)) {
            emit(Resource.Loading())

            if (response.isSuccessful) {
                when (response.code()) {
                    in 200..300 -> {
                        if (response.body() == null) {
                            emit(Resource.Error("Empty Data"))
                            Log.d(TAG, "Empty Data")

                        } else {
                            emit(Resource.Success(response.body()!!))
                            Log.d(TAG, "body ${response.body()}")
                        }
                    }

                    in 301..400 -> {
                        emit(Resource.Error(message = response.message()))
                        Log.d(TAG, "400")
                    }

                    else -> {
                        emit(Resource.Error(message = response.message()))
                        Log.d(TAG, "500")
                    }
                }
            } else {
                emit(Resource.Error(message = response.message()))
                Log.d(TAG, "Failed")
            }
        } else {
            // No network connectivity
            emit(Resource.Error("No network connection"))
        }
    }
}


private fun isNetworkConnected(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val networkCapabilities = connectivityManager.activeNetwork ?: return false
    val activeNetwork =
        connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false

    return when {
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
        else -> false
    }
}