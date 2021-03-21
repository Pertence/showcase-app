package com.example.app.model

data class DetailLocation(
    val id: Int,
    val name: String,
    val review: Float,
    val type: String,
    val about: String,
    val phone: String,
    val address: String,
    val schedule: Schedule
)