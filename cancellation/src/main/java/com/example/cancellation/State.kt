package com.example.cancellation

//data class State(
//    val progress: Boolean = false,
//    val error: Boolean = false,
//    val canceled: Boolean = false,
//    var calculated: Long = 0L
//)

abstract class State

class Progress: State()  // стан, коли застосунок обчислює значення
class Error: State()  // стан, коли користувач задав неправильні дані
class Canceled: State() // стан, коли користувач відмінив обчислення
class Calculated(
    val value: Long = 0L
) : State() // старн, коли результат обчислений

