package com.example.app.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.model.Images
import com.example.app.model.ListLocations
import com.example.app.network.ImagesAPI
import com.example.app.network.LocationAPI
import com.example.app.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val locationAPI: LocationAPI,
    private val imagesAPI: ImagesAPI
) : ViewModel() {

    private val _locations = MutableLiveData<ListLocations>()
    val locations: LiveData<ListLocations> = _locations

    private val _statusRequest = MutableLiveData<Status>()
    val statusRequest: LiveData<Status>
        get() = _statusRequest

    private var _images = MutableLiveData<Images>()
    val images: LiveData<Images> = _images

    var messageHome: String? = null

    init {
        viewModelScope.launch {
            val request = requestLocations()
            messageHome = request.message
            _statusRequest.value = request.status
        }
    }

    private suspend fun requestLocations(): Resource<ListLocations> {
        return try {
            val returnLocations = locationAPI.getAllLocations()

            if (returnLocations.isSuccessful) {
                returnLocations.body()?.let {
                    _locations.value = returnLocations.body()
                    return@let Resource.success(returnLocations.body())
                } ?: Resource.error(EMPTY_OR_INVALID_REQUEST, null)
            } else {
                Resource.error(REQUEST_NOT_FOUND, null)
            }
        } catch (e: Exception) {
            Resource.error(REQUEST_NOT_FOUND, null)
        }
    }

    fun getImages(quantityImage: Int) {
        viewModelScope.launch {
            try {
                val returnImages = imagesAPI.getImages(quantityImage)
                returnImages.body()?.let {
                    _images.value = it
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    companion object{
        const val EMPTY_OR_INVALID_REQUEST = "Empty or invalid request"
        const val REQUEST_NOT_FOUND = "Request not found"
    }
}