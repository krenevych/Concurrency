package com.example.multithreading

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.multithreading.databinding.ActivityMainBinding
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLoadData.setOnClickListener {
            loadData { loadedData: String ->
                binding.tvResult.text = loadedData
                Log.d(TAG, "loadData: text_view updated, Activity ${this@MainActivity}")
            }
        }
    }

    private fun loadData(onResult: (String) -> Unit) {

        Log.d(TAG, "loadData: START loading, Activity ${this@MainActivity}")

        thread {

            Thread.sleep(10_000)  // Імітація важкої роботи (15 секунд)
            Log.d(TAG, "loadData: FINISH loading,  Activity ${this@MainActivity}")

            runOnUiThread {
                onResult("Дані завантажено!")
            }

        }
    }

    companion object
    {
        val TAG = "XXX"
    }
}