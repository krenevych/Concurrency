package com.example.coroutine

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.coroutine.databinding.ActivityMainBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.btnLoadData.setOnClickListener {

            lifecycleScope.launch {
                loadData()
            }

        }

    }

    private suspend fun loadData() {
        Log.d(TAG, "loadData: START data loading from the internet... $this")

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
            val city = loadCity()

               // and set it into correspondent text view
                binding.tvCityValue.text = city

            // then load temperature for loaded City,
                val temperature = loadTemperature(city)
               // and set it into correspondent text view
                binding.tvTemperatureValue.text = temperature.toString()

        // on Finish:
            // hide progress bar
            binding.progressBar.visibility = View.GONE

            // enable button "load data"
            binding.btnLoadData.isEnabled = true

        Log.d(TAG, "loadData: FINISH data loading from the internet... $this")
    }

    private suspend fun loadCity(): String {
//        Thread.sleep(3_000)  // to simulate Long-running operation
        delay(3_000.milliseconds)

        return "Kyiv"
    }

    private suspend fun loadTemperature(city: String): Int {
//        Thread.sleep(3_000)   // to simulate Long-running operation
        delay(3_000.milliseconds)

        return 15  // Celsius degrees
    }

    override fun onDestroy() {
        super.onDestroy()

//        coroutineScope.cancel()
    }

    companion object {
        val TAG = "XXXX"
    }

}