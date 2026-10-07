package com.example.cancellation

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.cancellation.databinding.ActivityMainBinding
import kotlin.getValue

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel by viewModels<CalculationViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        viewModel.resultLV.observe(this) { result: Long? ->
            binding.tvResult.text = result.toString()
        }

        viewModel.progress.observe(this) { progress: Boolean? ->
            progress?.let {
                binding.progressBar.visibility = if (progress) View.VISIBLE else View.GONE
                binding.btnCalculate.isEnabled = !progress
                binding.btnCancel.isEnabled = progress

            }


        }

        binding.btnCalculate.setOnClickListener {
            val n = binding.etNum.text.toString().toLong()

            viewModel.calculate(n)
        }

        binding.btnCancel.setOnClickListener {
            viewModel.cancel()
        }

    }

}