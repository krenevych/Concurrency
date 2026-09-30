package com.example.multithreading

import android.os.Bundle
import android.os.Handler
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
            loadData()
        }
    }

    val handler = Handler()  // хендлер, що асоційований з головним потоком

    private fun loadData() {

        Log.d(TAG, "loadData: START loading")

        thread {
            // Імітація важкої роботи (15 секунд)
            Thread.sleep(3_000)

            Log.d(TAG, "loadData: FINISH loading")

            handler.post { // надсилає меседж у Looper
                binding.tvResult.text = "Дані завантажено!"  // можемо міняти лише з головного потоку
                Log.d(TAG, "loadData: text_view updated")
            }


        }

        Log.d(TAG, "loadData: end of function")
    }

    companion object
    {
        val TAG = "XXX"
    }
}