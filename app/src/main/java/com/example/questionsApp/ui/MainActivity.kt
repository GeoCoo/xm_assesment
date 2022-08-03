package com.example.questionsApp.ui

import ResponseStatus
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.questionsApp.R
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
        mainViewModel.apply {
            GlobalScope.launch(Dispatchers.IO) {
                val questionsList = fetchQuestions()
                postQuestionsList(questionsList)
            }
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


    private fun makeToast() = Toast.makeText(this@MainActivity,
        this@MainActivity.resources.getString(R.string.no_answer),
        Toast.LENGTH_LONG).show()
}