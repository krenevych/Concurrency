package com.example.cancellation

data class State(
    val progress: Boolean = false,
    val error: Boolean = false,
    val canceled: Boolean = false,
    var calculated: Long = 0L
)