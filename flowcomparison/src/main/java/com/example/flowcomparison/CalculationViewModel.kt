package com.example.flowcomparison

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.math.BigInteger
import kotlin.time.Duration.Companion.milliseconds

class CalculationViewModel : ViewModel() {


    //    Cold flow
    val fib: Flow<BigInteger> = flow {
        var f2 = BigInteger.ONE  // 0-й член послідовності Фібоначчі
        var f1 = BigInteger.ONE  // 1-й член послідовності Фібоначчі
        while (true) {
            emit(f1)  // надсилає дані споживачу flow
            Log.i("EEEE", "ViewModel: $f1")
            delay(500.milliseconds)
            val f = f2 + f1  // поточний член послідовності Фібоначчі
            f2 = f1
            f1 = f
        }
    }

    //    Hot flow
//    val _fib = MutableStateFlow(BigInteger.ONE)
//
//    private var f2 = BigInteger.ONE  // 0-й член послідовності Фібоначчі
//    private var f1 = BigInteger.ONE  // 1-й член послідовності Фібоначчі
//
//    val fib = _fib.asStateFlow()
//
//    //
//    init {
//        viewModelScope.launch {
//            while (true) {
//                _fib.emit(f1)  // надсилає дані споживачу flow
//                Log.i("EEEE", "ViewModel: $f1")
//                delay(500.milliseconds)
//                val f = f2 + f1  // поточний член послідовності Фібоначчі
//                f2 = f1
//                f1 = f
//            }
//        }
//    }


}  // CalculationViewModel
