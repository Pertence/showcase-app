package com.example.app.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Locations(
    val id: Int,
    var img: String?,
    val name: String,
    val review: Float,
    val type: String,
) : Parcelable





