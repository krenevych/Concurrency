package com.example.coroutine

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.coroutine.databinding.ActivityMainBinding
import kotlin.concurrent.thread
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLoadData.setOnClickListener {
            val continuation = LoadDataContinuation(this)
            loadData(continuation)
        }
    }

    /**
     * Стейт-машина без універсального `result: Any?`.
     * Дані записуються безпосередньо у відповідні поля [completion].
     */
    fun loadData(completion: LoadDataContinuation) {
        Log.d(TAG, "loadDataContinuation: label=${completion.label}")

        when (completion.label) {
            0 -> {
                // КРОК 0: Початок виконання
                completion.label = 1

                binding.btnLoadData.isEnabled = false
                binding.progressBar.visibility = View.VISIBLE
                binding.tvCityValue.text = ""
                binding.tvTemperatureValue.text = ""
                Toast.makeText(this, "Loading data...", Toast.LENGTH_SHORT).show()

                thread {
                    loadCity(completion)
                }
            }

            1 -> {
                // КРОК 1: Відновлення — місто вже записано в completion.city
                binding.tvCityValue.text = completion.city

                completion.label = 2

                thread {
                    loadTemperature(completion)
                }
            }

            2 -> {
                // КРОК 2: Відновлення — температура вже записана в completion.temperature
                binding.tvTemperatureValue.text = completion.temperature.toString()

                binding.progressBar.visibility = View.GONE
                binding.btnLoadData.isEnabled = true
            }
        }
    }

    private fun loadCity(continuation: LoadDataContinuation) {
        Thread.sleep(3_000) // Імітація тривалої роботи

        runOnUiThread {
            continuation.city = "Kyiv" // Записуємо результат безпосередньо у поле
            continuation.resumeWith(Result.success(Unit))
        }
    }

    private fun loadTemperature(continuation: LoadDataContinuation) {
        Thread.sleep(3_000) // Імітація тривалої роботи

        runOnUiThread {
            continuation.temperature = 15 // Записуємо результат безпосередньо у поле
            continuation.resumeWith(Result.success(Unit))
        }
    }

    companion object {
        const val TAG = "XXXX"
    }
}

/**
 * Спеціалізований Continuation зі строко типізованими полями для збереження стану.
 */
class LoadDataContinuation(
    private val activity: MainActivity,
    override val context: CoroutineContext = EmptyCoroutineContext
) : Continuation<Unit> {

    // Стан стейт-машини
    var label: Int = 0

    // Строго типізовані поля для результатів
    var city: String = ""
    var temperature: Int = 0

    override fun resumeWith(result: Result<Unit>) {
        if (result.isFailure) {
            Log.e(MainActivity.TAG, "Error during execution", result.exceptionOrNull())
            return
        }
        activity.loadData(this)
    }
}
