package com.example.questionsApp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.questionsApp.models.AnswerToSubmit
import com.example.questionsApp.models.EmptyResponse
import com.example.questionsApp.network.controllers.GetQuestionsController
import com.example.questionsApp.network.controllers.SubmitAnswerController


class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val mainController: GetQuestionsController by lazy { GetQuestionsController() }
    private val submitController: SubmitAnswerController by lazy { SubmitAnswerController() }
    private val questionResponseMutable: MutableLiveData<ArrayList<*>?> by lazy { MutableLiveData<ArrayList<*>?>() }
    private val submittedAnswerMutable: MutableLiveData<AnswerToSubmit?> by lazy { MutableLiveData<AnswerToSubmit?>() }
    private val submissionResponseMutable: MutableLiveData<EmptyResponse> by lazy { MutableLiveData<EmptyResponse>() }
    private val submissionCounterMutable: MutableLiveData<Int> by lazy { MutableLiveData<Int>() }
    private var answersCount: Int = 0

    suspend fun fetchQuestions() {
        postQuestionsList(mainController.fetchQuestions())
    }

    private fun postQuestionsList(response: ArrayList<*>?) {
        questionResponseMutable.postValue(response)
    }

    fun observeResponse(lifecycleOwner: LifecycleOwner, observer: Observer<ArrayList<*>?>) {
        questionResponseMutable.observe(lifecycleOwner, observer)
    }


    fun postSubmittedAnswer(answer: AnswerToSubmit?) {
        submittedAnswerMutable.postValue(answer)
    }

    fun observeSubmittedAnswer(lifecycleOwner: LifecycleOwner, observer: Observer<AnswerToSubmit?>) {
        submittedAnswerMutable.observe(lifecycleOwner, observer)
    }

    suspend fun submitAnswer(questionSubmit: AnswerToSubmit?) {
        val response = submitController.submitAnswer(questionSubmit)
        postSubmissionResponse(response as EmptyResponse?)
    }

    fun postSubmissionResponse(response: EmptyResponse?) {
        submissionResponseMutable.postValue(response)
    }

    fun observeSubmissionResponse(lifecycleOwner: LifecycleOwner, observer: Observer<EmptyResponse>) {
        submissionResponseMutable.observe(lifecycleOwner, observer)
    }


    fun submissionCounting(): MutableLiveData<Int> {
        submissionCounterMutable.value = answersCount
        return submissionCounterMutable
    }

    fun getNextSubmission() {
        answersCount += 1
        submissionCounterMutable.value = answersCount
    }


}