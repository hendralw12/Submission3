package com.example.submission1.core.data

import com.example.submission1.core.data.source.local.LocalDataSource
import com.example.submission1.core.data.source.remote.RemoteDataSource
import com.example.submission1.core.data.source.remote.network.ApiResponses
import com.example.submission1.core.data.source.remote.response.MovieResponse
import com.example.submission1.core.data.source.remote.response.TVResponse
import com.example.submission1.core.domain.model.Film
import com.example.submission1.core.domain.repository.IFilmRepository
import com.example.submission1.core.utils.AppExecutors
import com.example.submission1.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

open class MainRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
    ) :
    IFilmRepository {

    companion object {
        @Volatile
        private var instance: com.example.submission1.core.data.MainRepository? = null

        fun getInstance(remoteDataSource: RemoteDataSource, localData : LocalDataSource, appExecutors: AppExecutors): IFilmRepository =
            com.example.submission1.core.data.MainRepository.Companion.instance ?: synchronized(this) {
                com.example.submission1.core.data.MainRepository.Companion.instance
                    ?: com.example.submission1.core.data.MainRepository(
                        remoteDataSource,
                        localData,
                        appExecutors
                    ).apply { com.example.submission1.core.data.MainRepository.Companion.instance = this }
            }
    }

//    override fun getSearch(searchKeyword: String): LiveData<ResponseSearchMulti> {
//        val liveData = MutableLiveData<ResponseSearchMulti>()
//        apiDataSource.getSearchMovieResult(searchKeyword,object : ApiDataSource.loadFilm {
//            override fun onItemsReceived(responseSearchMulti: ResponseSearchMulti) {
//                liveData.postValue(responseSearchMulti)
//            }
//
//            override fun onUnsuccessfulResponse(responseCode: Int) {
//                Log.d("Main Repository",responseCode.toString())
//            }
//
//            override fun onDataNotAvailable() {
//                Log.d("Main Repository","No data found")
//            }
//        })
//        return liveData
//    }
//
//    override fun getListFavorite() : LiveData<PagedList<Film>> {
//        val config = PagedList.Config.Builder()
//            .setEnablePlaceholders(false)
//            .setInitialLoadSizeHint(4)
//            .setPageSize(4)
//            .build()
//        return LivePagedListBuilder(localDataSource.getListFavorite(), config).build()
//    }
//
//    fun getCountFavoriteById(judul : String): LiveData<Int> {
//        return localDataSource.getCountFavoriteById(judul)
//    }

//    suspend fun insertFavorite(film: Film) {
//        localDataSource.insertFavorite(film)
//    }
//
//    suspend fun deleteFavorite(film: Film) {
//        localDataSource.deleteFavorite(film)
//    }

    override fun getMovies(): Flow<com.example.submission1.core.data.Resource<List<Film>>> =
        object : com.example.submission1.core.data.NetworkBoundResource<List<Film>, List<MovieResponse>>() {
            override fun loadFromDB(): Flow<List<Film>> {
                return localDataSource.getAllMovie().map {
                    DataMapper.mapEntitiesToDomainMovie(it)
                }
            }

            override fun shouldFetch(data: List<Film>?): Boolean =
                data == null || data.isEmpty()
//                true // ganti dengan true jika ingin selalu mengambil data dari internet

            override suspend fun createCall(): Flow<ApiResponses<List<MovieResponse>>> =
                remoteDataSource.getAllMovie()

            override suspend fun saveCallResult(data: List<MovieResponse>) {
                val movieList = DataMapper.mapResponsesToEntitiesMovies(data)
                localDataSource.insertMovie(movieList)
            }
        }.asFlow()


    override fun getTV(): Flow<com.example.submission1.core.data.Resource<List<Film>>> =
        object : com.example.submission1.core.data.NetworkBoundResource<List<Film>, List<TVResponse>>() {
            override fun loadFromDB(): Flow<List<Film>> {
                return localDataSource.getAllTV().map {
                    DataMapper.mapEntitiesToDomainTV(it)
                }
            }

            override fun shouldFetch(data: List<Film>?): Boolean =
                data == null || data.isEmpty()
//                true // ganti dengan true jika ingin selalu mengambil data dari internet

            override suspend fun createCall(): Flow<ApiResponses<List<TVResponse>>> =
                remoteDataSource.getAllTV()

            override suspend fun saveCallResult(data: List<TVResponse>) {
                val tvlist = DataMapper.mapResponsesToEntitiesTV(data)
                localDataSource.insertTV(tvlist)
            }
        }.asFlow()


    override fun getFavoriteMovies(): Flow<List<Film>> {
        return localDataSource.getFavoriteMovie().map {
            DataMapper.mapEntitiesToDomainMovie(it)
        }
    }

    override fun getFavoriteTV(): Flow<List<Film>> {
        return localDataSource.getFavoriteTV().map {
            DataMapper.mapEntitiesToDomainTV(it)
        }
    }

    override fun setFavorite(film: Film, state: Boolean) {
        if(film.type=="Movie"){
            val movieEntity = DataMapper.mapDomainToEntityMovie(film)
            appExecutors.diskIO().execute {
                localDataSource.setFavoriteMovie(movieEntity, state)
            }
        }
        else if(film.type=="TV"){
            val TVEntity = DataMapper.mapDomainToEntityTV(film)
            appExecutors.diskIO().execute {
                localDataSource.setFavoriteTV(TVEntity, state)
            }
        }

    }
}