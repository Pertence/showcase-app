package com.example.app.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListLocations(
    val listLocations: List<Locations>
) : Parcelable