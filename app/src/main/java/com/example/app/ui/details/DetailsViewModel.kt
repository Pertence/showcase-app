package com.example.app.ui.details

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import com.example.app.model.DetailLocation
import com.example.app.network.LocationAPI
import com.example.app.utils.*
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val locationDetailsAPI: LocationAPI
) : ViewModel() {

    private val _detailLocation = MutableLiveData<DetailLocation>()
    val detailLocation: LiveData<DetailLocation> = _detailLocation

    private val _statusRequestDetail = MutableLiveData<Status>()
    val statusRequestDetail: LiveData<Status>
        get() = _statusRequestDetail

    var messageDetail: String? = null

    private suspend fun requestDetailLocation(id: Int): Resource<DetailLocation> {
        return try {
            val returnDetailLocation = locationDetailsAPI.getDetailsLocation(id)

            if (returnDetailLocation.isSuccessful) {
                returnDetailLocation.body()?.let {
                    _detailLocation.value = returnDetailLocation.body()
                    return@let Resource.success(returnDetailLocation.body())
                } ?: Resource.error(EMPTY_OR_INVALID_REQUEST, null)
            } else {
                Resource.error(REQUEST_NOT_FOUND, null)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.error(REQUEST_NOT_FOUND, null)
        }
    }

    fun getDetailsLocation(id: Int) {
        viewModelScope.launch {
            val makeRequestDetailLocationAPI = requestDetailLocation(id)
            messageDetail = makeRequestDetailLocationAPI.message
            _statusRequestDetail.value = makeRequestDetailLocationAPI.status
        }
    }

    companion object{
        const val EMPTY_OR_INVALID_REQUEST = "Empty or invalid request"
        const val REQUEST_NOT_FOUND = "Request not found"
    }

}