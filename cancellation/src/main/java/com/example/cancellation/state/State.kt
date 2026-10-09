package com.example.cancellation.state

//data class State(
//    val progress: Boolean = false,
//    val error: Boolean = false,
//    val canceled: Boolean = false,
//    var calculated: Long = 0L
//)

//sealed class State {
//    data object Progress: State()  // стан, коли застосунок обчислює значення
//    data object Error: State()  // стан, коли користувач задав неправильні дані
//    data object Canceled: State() // стан, коли користувач відмінив обчислення
//    data class Calculated(
//        val value: Long = 0L
//    ) : State() // старн, коли результат обчислений
//
//}

sealed class State

data object Progress: State()  // стан, коли застосунок обчислює значення
data object Error: State()  // стан, коли користувач задав неправильні дані
data object Canceled: State() // стан, коли користувач відмінив обчислення
data class Calculated(
    val value: Long = 0L
) : State() // старн, коли результат обчислений





//enum class State {
//    Progress,
//    Error,
//    Canceled,
//    Calculated,
//
//}

