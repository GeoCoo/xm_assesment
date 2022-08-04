package com.example.questionsApp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.questionsApp.models.AnswerToSubmit
import com.example.questionsApp.network.controllers.GetQuestionsController
import com.example.questionsApp.network.controllers.SubmitAnswerController


open class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val mainController: GetQuestionsController by lazy { GetQuestionsController() }
    private val submitController: SubmitAnswerController by lazy { SubmitAnswerController() }
    val questionResponseMutable: MutableLiveData<ArrayList<*>?> by lazy { MutableLiveData<ArrayList<*>?>() }
    private val submittedAnswerMutable: MutableLiveData<AnswerToSubmit?> by lazy { MutableLiveData<AnswerToSubmit?>() }
    private val submissionCounterMutable: MutableLiveData<Int> by lazy { MutableLiveData<Int>() }
    private var submissionResponseMutable: MutableLiveData<String?> = MutableLiveData<String?>()
    private var answersCount: Int = 0

    open suspend fun fetchQuestions(): ArrayList<Any>? = mainController.fetchQuestions()

    open fun postQuestionsList(response: ArrayList<Any>?) {
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

    open suspend fun submitAnswer(questionSubmit: AnswerToSubmit?): String? = submitController.submitAnswer(questionSubmit)

    fun postSubmissionResponse(response: String?) {
        submissionResponseMutable.postValue(response)
    }

    fun observeSubmissionResponse(lifecycleOwner: LifecycleOwner, observer: Observer<String?>) {
        submissionResponseMutable = MutableLiveData()
        submissionResponseMutable.observe(lifecycleOwner, observer)
    }

    fun submissionCounting(): MutableLiveData<Int> {
        submissionCounterMutable.value = answersCount
        return submissionCounterMutable
    }

    fun addToSubmissionCounter() {
        answersCount += 1
        submissionCounterMutable.postValue(answersCount)
    }
}