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

    private var _resulLV: MutableLiveData<Long> = MutableLiveData(0L)
    val resultLV: LiveData<Long>
        get() = _resulLV

    private var _progress = MutableLiveData(false)
    val progress: LiveData<Boolean>
        get() = _progress

    private var _error = MutableLiveData(false)
    val error: LiveData<Boolean>
        get() = _error

    private var _canceled = MutableLiveData(false)
    val canceled: LiveData<Boolean>
        get() = _canceled

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
            _error.value = true
            return
        }

        calculationJob = viewModelScope.launch {
            _progress.value = true

            _resulLV.value = withContext(Dispatchers.Default) {
                calculateSum(n.toLong())
            }

            _progress.value = false
        }

    }

    fun cancel() {

        calculationJob?.cancel()
        calculationJob = null

        _canceled.value = true
        _progress.value = false
        _resulLV.postValue(0)

    }


}