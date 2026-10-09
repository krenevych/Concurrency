package com.example.cancellation

import android.os.Bundle
import android.view.View
import android.widget.Toast
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

        viewModel.state.observe(this) { state ->

            if (state.error){
                Toast.makeText(this, "Non valid int value", Toast.LENGTH_SHORT)
                    .show()
            } else if (state.progress) {
                binding.etNum.isEnabled = false
                binding.progressBar.visibility = View.VISIBLE
                binding.btnCalculate.isEnabled = false
                binding.btnCancel.isEnabled = true
                binding.tvResult.text = ""
            } else if (state.canceled){
                binding.etNum.isEnabled = true
                binding.progressBar.visibility = View.GONE
                binding.btnCalculate.isEnabled = true
                binding.btnCancel.isEnabled = false

                Toast.makeText(this, "Calculation was canceled", Toast.LENGTH_SHORT)
                    .show()
            } else { // calculated
                binding.etNum.isEnabled = true
                binding.progressBar.visibility = View.GONE
                binding.btnCalculate.isEnabled = true
                binding.btnCancel.isEnabled = false
                binding.tvResult.text = state.calculated.toString()
            }

        }

//        viewModel.resultLV.observe(this) { result: Long? ->
//            binding.tvResult.text = result.toString()
//        }
//
//        viewModel.progress.observe(this) { progress: Boolean? ->
//            progress?.let {
//                binding.progressBar.visibility = if (progress) View.VISIBLE else View.GONE
//                binding.btnCalculate.isEnabled = !progress
//                binding.btnCancel.isEnabled = progress
//            }
//        }
//
//        viewModel.error.observe(this) { error ->
//            if (error)
//                Toast.makeText(this, "Non valid int value", Toast.LENGTH_SHORT)
//                    .show()
//        }
//
//        viewModel.canceled.observe(this) { canceled ->
//            if (canceled){
//                Toast.makeText(this, "Calculation was canceled", Toast.LENGTH_SHORT)
//                    .show()
//            }
//
//        }

        binding.btnCalculate.setOnClickListener {
            val n_str = binding.etNum.text.toString() // "".toLong()

            viewModel.calculate(n_str)
        }

        binding.btnCancel.setOnClickListener {
            viewModel.cancel()
        }

    }

}