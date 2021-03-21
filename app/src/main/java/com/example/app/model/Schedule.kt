package com.example.app.model

data class Schedule(
    val monday: Timing = Timing(),
    val tuesday: Timing = Timing(),
    val wednesday: Timing = Timing(),
    val thursday: Timing = Timing(),
    val friday: Timing = Timing(),
    val saturday: Timing = Timing(),
    val sunday: Timing = Timing(),
)