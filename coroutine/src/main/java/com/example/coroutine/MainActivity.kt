package com.example.coroutine

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.transition.Visibility
import com.example.coroutine.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.concurrent.thread
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.time.Duration.Companion.milliseconds

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.btnLoadData.setOnClickListener {
//            lifecycleScope.launch {
                loadData(LoadDataContinuation(this))
//            }
        }

    }

//    private fun loadData(step: Int, data: Any? = null) {
    private fun loadData(continuation: LoadDataContinuation) {
        Log.d(TAG, "loadData: start data loading from the internet...")

        when(continuation.step) {
            1 -> { // блок 1 (step)
                continuation.step = 2

                // on Start:
                // disable button "load data"
                binding.btnLoadData.isEnabled = false

                // show progress bar
                binding.progressBar.visibility = View.VISIBLE
                // clear City
                binding.tvCityValue.text = ""

                // clear Temperature
                binding.tvTemperatureValue.text = ""

                // show Toast that data is started loading.
                Toast.makeText(this, "Loading data", Toast.LENGTH_SHORT).show()

                // on Progress
                // load City
                thread {
                    loadCity(continuation)  // <- suspend function
                }

            }
            2 -> { // блок 2 (step)
                continuation.step = 3

                // and set it into correspondent text view
                binding.tvCityValue.text = continuation.city

                // then load temperature for loaded City,
                thread {
                    loadTemperature(continuation)
                }

            }
            3 -> {  // блок 3 (step)

                val temperature = continuation.temperature

                // and set it into correspondent text view
                binding.tvTemperatureValue.text = temperature.toString()

                // on Finish:
                // hide progress bar
                binding.progressBar.visibility = View.GONE

                // enable button "load data"
                binding.btnLoadData.isEnabled = true
            }
        } // end of "when"

    }

    private fun loadCity(continuation: LoadDataContinuation) {
        Thread.sleep(3_000)  // to simulate Long-running operation
        continuation.city = "Kyiv"
        runOnUiThread {
//            onResult("Kyiv") // return "Kyiv"
            continuation.resumeWith(Result.success(Unit))
        }

    }

    private fun loadTemperature(continuation: LoadDataContinuation)  {
        Thread.sleep(3_000)   // to simulate Long-running operation
        continuation.temperature = 15

        runOnUiThread {
//            onResult(15) // return 15  // Celsius degrees
            continuation.resumeWith(Result.success(Unit))
        }
    }

    companion object {
        val TAG = "XXXX"
    }

    class LoadDataContinuation(
        private val activity: MainActivity,
        override val context: CoroutineContext = EmptyCoroutineContext,
    ) : Continuation<Unit>{
        var step: Int = 1  // який блок виконувати - для машини станів
        var city: String = ""
        var temperature: Int = 0

        override fun resumeWith(result: Result<Unit>) {
            if (result.isFailure) return

            activity.loadData(this)
        }

    }

}