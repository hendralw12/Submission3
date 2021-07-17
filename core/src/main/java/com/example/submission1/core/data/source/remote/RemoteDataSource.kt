package com.example.submission1.core.data.source.remote

import android.util.Log
import com.example.submission1.core.data.source.remote.network.ApiResponses
import com.example.submission1.core.data.source.remote.network.ApiService
import com.example.submission1.core.data.source.remote.response.MovieResponse
import com.example.submission1.core.data.source.remote.response.TVResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {

    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(service: ApiService): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource(service)
            }
    }

    suspend  fun getAllMovie(): Flow<ApiResponses<List<MovieResponse>>> {
        //get data from remote api
        return flow {
            try {
                val response = apiService.getMovies()
                val dataArray = response.results
                if (dataArray.isNotEmpty()){
                    emit(ApiResponses.Success(response.results))
                } else {
                    emit(ApiResponses.Empty)
                }
            } catch (e : Exception){
                emit(ApiResponses.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getAllTV(): Flow<ApiResponses<List<TVResponse>>> {
        //get data from remote api
        return flow {
            try {
                val response = apiService.getTV()
                val dataArray = response.results
                if (dataArray.isNotEmpty()){
                    emit(ApiResponses.Success(response.results))
                } else {
                    emit(ApiResponses.Empty)
                }
            } catch (e : Exception){
                emit(ApiResponses.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

}