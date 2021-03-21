package com.example.app.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageURL(
    val webformatURL: String =""
): Parcelable
