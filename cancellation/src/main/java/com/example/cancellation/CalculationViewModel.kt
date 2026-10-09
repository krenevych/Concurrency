package com.example.cancellation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.yield
import kotlin.time.Duration.Companion.milliseconds

class CalculationViewModel : ViewModel() {

    private val _state: MutableLiveData<State> = MutableLiveData(Calculated())
    val state: LiveData<State>
        get() = _state

    private suspend fun calculateSum(n: Long): Long {
        var result: Long = 0L
        for (i in 1..n) {
            result += i

//            delay(500.milliseconds)
//            if (result % 100_000_000L == 0L){
                yield() // <== корутина буде перевіряти чи вона скасована
//                Log.d("XXXX", "Current = $result")
//            }
        }
        return result
    }


    private var calculationJob: Job? = null
    fun calculate(n: String?) {

        if (n.isNullOrEmpty()) {  // n = null or n = ""
            _state.value = Error() //State(error = true)
            return
        }

        calculationJob = viewModelScope.launch {
            _state.value = Progress() //State(progress = true)


            val result = withContext(Dispatchers.Default) {
                calculateSum(n.toLong())
            }

            _state.value = Calculated(result) //State(calculated = result)
        }

    }

    fun cancel() {
        calculationJob?.cancel()
        calculationJob = null

        _state.value = Canceled()//State(canceled = true)
    }


}