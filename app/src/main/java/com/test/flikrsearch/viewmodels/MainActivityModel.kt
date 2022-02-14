package com.test.flikrsearch.viewmodels

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.flikrsearch.AppController
import com.test.flikrsearch.pojo.MyResponse
import com.test.flikrsearch.pojo.PhotoItem
import com.test.flikrsearch.utils.LoadStates
import com.test.flikrsearch.utils.Utils
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers

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
            /*viewModelScope.launch(Dispatchers.IO) {
                try {

                    val response = (AppController.context.apiServices).getImages(query, page)
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
            }*/
            AppController.context.apiServices.getImages(query,page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(object : DisposableSingleObserver<MyResponse>() {

                    override fun onError(e: Throwable) {
                        // Network error
                        loadingStates.postValue(LoadStates.ERROR)
                        Log.e(TAG, "fetchMovieByQuery: error-$e")
                    }

                    override fun onSuccess(t: @NonNull MyResponse) {
                        Log.e(TAG, "onSuccess: response $t")

                        if (page == 1) {
                            nextPage = 2
                            mutablePhotos.postValue(ArrayList(t.photos?.photo!!))
                        } else {
                            val addAll = mutablePhotos.value
                            if (addAll != null) {
                                addAll.addAll(t.photos?.photo!!)
                                mutablePhotos.postValue(addAll!!)
                            }
                        }
                        loadingStates.postValue(LoadStates.LOADED)

                    }

                })
        } else {
            loadingStates.postValue(LoadStates.ERROR)
            Toast.makeText(AppController.context, "No Internet", Toast.LENGTH_SHORT).show()
        }
    }

}