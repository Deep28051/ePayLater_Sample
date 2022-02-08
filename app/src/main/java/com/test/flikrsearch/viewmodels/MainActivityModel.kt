package com.test.flikrsearch.viewmodels

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.flikrsearch.AppController
import com.test.flikrsearch.AppController.Companion.apiServices
import com.test.flikrsearch.pojo.PhotoItem
import com.test.flikrsearch.utils.LoadStates
import com.test.flikrsearch.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityModel : ViewModel() {

    private val TAG = "MainActivityModel"

    val mutablePhotos = MutableLiveData<ArrayList<PhotoItem>>()
    val loadingStates = MutableLiveData<LoadStates>()
    private var nextPage = 1

    companion object {
        var oldSize = 0

        var searchTerm = ""
    }

    fun fetchMovieByQuery(query: String, page: Int = nextPage++) {
        if (Utils.isConnected()) {
            loadingStates.postValue(LoadStates.LOADING)
            viewModelScope.launch(Dispatchers.IO) {
                try {

                    val response = apiServices.getImages(query, page)
                    if (response.isSuccessful && response.body() != null) {
                        if (page == 1) {
                            nextPage = 2
                            mutablePhotos.postValue(ArrayList(response.body()!!.photos?.photo!!))
                        } else {
                            val addAll = mutablePhotos.value
                            if (addAll != null) {
                                addAll.addAll(response.body()!!.photos?.photo!!)
                                mutablePhotos.postValue(addAll!!)
                            }
                        }
                    }

                    loadingStates.postValue(LoadStates.LOADED)
                } catch (e: Exception) {
                    loadingStates.postValue(LoadStates.ERROR)
                    Log.e(TAG, "fetchMovieByQuery: error-$e")
                }
            }
        } else {
            loadingStates.postValue(LoadStates.ERROR)
            Toast.makeText(AppController.context, "No Internet", Toast.LENGTH_SHORT).show()
        }
    }

}