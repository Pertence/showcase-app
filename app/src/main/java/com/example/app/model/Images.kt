package com.example.app.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Images(
    val hits: List<ImageURL> = listOf()
) : Parcelable

