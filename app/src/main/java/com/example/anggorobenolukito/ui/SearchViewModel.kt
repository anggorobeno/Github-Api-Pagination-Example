package com.example.anggorobenolukito.ui

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.*
import com.example.anggorobenolukito.BuildConfig
import com.example.anggorobenolukito.data.Repository
import com.example.anggorobenolukito.data.remote.network.ApiService
import com.example.anggorobenolukito.data.remote.response.ItemsItem
import com.example.anggorobenolukito.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val apiService: ApiService,private val repository: Repository) : ViewModel() {
    private var _users : MutableLiveData<Resource<List<ItemsItem>>> = MutableLiveData()

    val user : LiveData<Resource<List<ItemsItem>>>
    get() {
        return _users
    }
    val currentQuery = MutableLiveData("anggoro")

    val usersss = currentQuery.switchMap { currentQuery ->
        getUser(currentQuery)
    }

    fun searchUser(query : String){
        currentQuery.value = query
    }



    fun getUsers(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _users.postValue(Resource.Loading(null))
            val response = apiService.getUserByQuery(1,query,BuildConfig.API_KEY)
            val result = response.body()?.items
            if (response.isSuccessful && result != null){
                _users.postValue(Resource.Success(result))
            }
        }
    }

    fun getUser(query : String) : LiveData<Resource<List<ItemsItem>>>{

        return liveData {
            try {
                emit(Resource.Loading())
                val response = apiService.getUserByQuery(1,query,BuildConfig.API_KEY)
                val result = response.body()?.items
                if (response.isSuccessful && result != null){
                    Log.d(TAG, "getUser: $result")
                    emit(Resource.Success(result))
                }
                if (result != null && result.isEmpty()){
                    emit(Resource.Error<List<ItemsItem>>("No user Found",null))

                }
            }
            catch (e: HttpException){
                emit(Resource.Error<List<ItemsItem>>("No Internet Connection",null))
            }
            catch (e: IOException){
                emit(Resource.Error<List<ItemsItem>>(e.toString(),null))
            }
        }
    }

//    val user = repository.getUserSearchResults("anggoro")
//    private val currentQuery = MutableLiveData()

}