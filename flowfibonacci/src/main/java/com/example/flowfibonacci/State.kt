package com.example.flowfibonacci

import java.math.BigInteger


sealed class State

data class Progress(
    val value: String = ""
): State()  // стан, коли застосунок обчислює значення
data object Error: State()  // стан, коли користувач задав неправильні дані
data object Canceled: State() // стан, коли користувач відмінив обчислення
data object Calculated : State() // старн, коли результат обчислений

