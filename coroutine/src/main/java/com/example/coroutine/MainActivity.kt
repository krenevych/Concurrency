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
import kotlin.time.Duration.Companion.milliseconds

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.btnLoadData.setOnClickListener {
//            lifecycleScope.launch {
                loadData(1)
//            }
        }

    }

    private fun loadData(step: Int, data: Any? = null) {
        Log.d(TAG, "loadData: start data loading from the internet...")

        when(step) {
            1 -> { // блок 1 (step)
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
                    loadCity({city ->
                        loadData(2, city)
                    })  // <- suspend function
                }

            }
            2 -> { // блок 2 (step)
                // and set it into correspondent text view
                val city = data as String

                binding.tvCityValue.text = city

                // then load temperature for loaded City,
                thread {
                    loadTemperature(city) { temperature ->  // <- suspend function
                        loadData(3, temperature)
                    }
                }

            }
            3 -> {  // блок 3 (step)

                val temperature = data as Int

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

    private fun loadCity(onResult: (String) -> Unit) {
        Thread.sleep(3_000)  // to simulate Long-running operation

        runOnUiThread {
            onResult("Kyiv") // return "Kyiv"
        }

    }

    private fun loadTemperature(city: String, onResult: (Int) -> Unit)  {
        Thread.sleep(3_000)   // to simulate Long-running operation

        runOnUiThread {
            onResult(15) // return 15  // Celsius degrees
        }
    }

    companion object {
        val TAG = "XXXX"
    }

}