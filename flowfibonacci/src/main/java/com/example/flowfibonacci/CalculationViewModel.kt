package com.example.flowfibonacci

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.math.BigInteger
import kotlin.time.Duration.Companion.milliseconds

class CalculationViewModel : ViewModel() {

    private val _state: MutableLiveData<State> = MutableLiveData(Calculated)
    val state: LiveData<State>
        get() = _state

//    private val _fibonacciData: MutableLiveData<String> = MutableLiveData("")
//    val fibonacciData: LiveData<String>
//        get() = _fibonacciData

    val fibonacciData: LiveData<BigInteger> = fib(100)
        .asLiveData()

    private fun fib(n: Long): Flow<BigInteger> {
        return flow {

            var f2 = BigInteger.ONE  // 0-й член послідовності Фібоначчі
            emit(f2)  // надсилає дані споживачу flow
            delay(500.milliseconds)

            var f1 = BigInteger.ONE  // 1-й член послідовності Фібоначчі
            emit(f1)  // надсилає дані споживачу flow
            delay(500.milliseconds)

            for (i in 2..n) {  // рахуємо починаючи з 2-го
                val f = f2 + f1  // поточний член послідовності Фібоначчі
                emit(f)  // надсилає дані споживачу flow
                delay(500.milliseconds)
                f2 = f1
                f1 = f
            }
        }
    }


    private var calculationJob: Job? = null
    fun calculate(n: String?) {

        if (n.isNullOrEmpty()) {  // n = null or n = ""
            _state.value = Error //State(error = true)
            return
        }

        calculationJob =
            fib(n.toLong())
                .onStart {
                    Log.d("XXX", "Started")
                }
                .map { "$it"  }
                .onEach { it: String ->
                    Log.d("XXX", "calculate: $it")
//                    _state.value = Progress(it)  #           java.lang.IllegalStateException: Cannot invoke setValue on a background thread
                    _state.postValue(Progress(it))
//                    _fibonacciData.postValue(it)
                }
                .flowOn(Dispatchers.Default)
                .onCompletion { cause: Throwable? ->
                    if (cause == null){
                        _state.value = Calculated
                    } else
                    {
                        _state.value = Canceled
                    }

                }
                .launchIn(viewModelScope)

    }

    fun cancel() {
        calculationJob?.cancel()
        calculationJob = null

//        _state.value = Canceled   //State(canceled = true)
    }


}