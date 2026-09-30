package com.example.coroutine

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.transition.Visibility
import com.example.coroutine.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.btnLoadData.setOnClickListener {
            loadData()
        }

    }

    private fun loadData() {
        Log.d(TAG, "loadData: start data loading from the internet...")

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
    }

    private fun loadCity(): String {
        Thread.sleep(3_000)  // to simulate Long-running operation

        return "Kyiv"
    }

    private fun loadTemperature(city: String): Int {
        Thread.sleep(3_000)   // to simulate Long-running operation

        return 15  // Celsius degrees
    }

    companion object {
        val TAG = "XXXX"
    }

}