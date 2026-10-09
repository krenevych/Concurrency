package com.example.flowfibonacci

import java.math.BigInteger


sealed class State

data object Progress: State()  // стан, коли застосунок обчислює значення
data object Error: State()  // стан, коли користувач задав неправильні дані
data object Canceled: State() // стан, коли користувач відмінив обчислення
data class Calculated(
    val value: BigInteger = BigInteger.ZERO
) : State() // старн, коли результат обчислений

