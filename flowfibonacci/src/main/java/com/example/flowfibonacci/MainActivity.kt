package com.example.flowfibonacci

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.flowfibonacci.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import kotlin.getValue

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel by viewModels<CalculationViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state: State ->
                    when (state) {
                        Error -> {   //state.error  state == Error
                            Toast.makeText(this@MainActivity, "Non valid int value", Toast.LENGTH_SHORT)
                                .show()
                        }

                        is Progress -> {
                            binding.etNum.isEnabled = false
                            binding.progressBar.visibility = View.VISIBLE
                            binding.btnCalculate.isEnabled = false
                            binding.btnCancel.isEnabled = true
                            binding.tvResult.text =  binding.tvResult.text.toString() + " " + state.value
                        }

                        Canceled -> {
                            binding.etNum.isEnabled = true
                            binding.progressBar.visibility = View.GONE
                            binding.btnCalculate.isEnabled = true
                            binding.btnCancel.isEnabled = false

                            Toast.makeText(this@MainActivity, "Calculation was canceled", Toast.LENGTH_SHORT)
                                .show()
                        }

                        Calculated -> { // calculated
                            binding.etNum.isEnabled = true
                            binding.progressBar.visibility = View.GONE
                            binding.btnCalculate.isEnabled = true
                            binding.btnCancel.isEnabled = false
                        }
                    }
                }
            }
        }


        binding.btnCalculate.setOnClickListener {
            val n_str = binding.etNum.text.toString() // "".toLong()

            viewModel.calculate(n_str)
        }

        binding.btnCancel.setOnClickListener {
            viewModel.cancel()
        }

    }

}