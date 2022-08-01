package com.example.questionsApp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.questionsApp.databinding.ActivityMainBinding
import com.example.questionsApp.models.Question
import com.example.questionsApp.viewmodels.MainViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModel()
    private var response: ArrayList<Question>? = arrayListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onResume() {
        super.onResume()
        GlobalScope.launch(Dispatchers.IO) {
        response = mainViewModel.fetchQuestions()
        response
        }

    }
}