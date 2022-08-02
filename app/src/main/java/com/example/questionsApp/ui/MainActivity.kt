package com.example.questionsApp.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.questionsApp.databinding.ActivityMainBinding
import com.example.questionsApp.viewmodels.MainViewModel
import kotlinx.coroutines.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onResume() {
        super.onResume()
        GlobalScope.launch(Dispatchers.Main) {
            mainViewModel.apply {
                fetchQuestions()
            }
            mainViewModel.observeSubmittedAnswer(this@MainActivity) { submition ->
                if (submition == null) Toast.makeText(this@MainActivity, "you haven't answer", Toast.LENGTH_LONG).show()
                else
                    CoroutineScope(Dispatchers.IO).launch {
                        mainViewModel.submitAnswer(submition)
                    }
            }
        }

    }
}