package com.example.questionsApp.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.questionsApp.R
import com.example.questionsApp.databinding.ActivityMainBinding
import com.example.questionsApp.network.ResponseStatus
import com.example.questionsApp.viewmodels.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        fetchQuestions()
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        CoroutineScope(Dispatchers.Main).launch {
            mainViewModel.observeResponse(this@MainActivity) { questionsList ->
                if (questionsList?.isEmpty() == true) fetchQuestions()
            }
        }
        mainViewModel.apply {
            observeSubmittedAnswer(this@MainActivity) { submition ->
                if (submition != null)
                    CoroutineScope(Dispatchers.IO).launch {
                        val submissionResponse = submitAnswer(submition)
                        postSubmissionResponse(submissionResponse)
                        if (submissionResponse == ResponseStatus.OK.code.toString()) addToSubmissionCounter()
                    } else makeToast()
            }
        }
    }

    private fun fetchQuestions() {
        mainViewModel.apply {
            CoroutineScope(Dispatchers.IO).launch {
                val questionsList = fetchQuestions()
                postQuestionsList(questionsList)
            }
        }
    }

    private fun makeToast() = Toast.makeText(this@MainActivity,
        this@MainActivity.resources.getString(R.string.no_answer),
        Toast.LENGTH_LONG).show()
}