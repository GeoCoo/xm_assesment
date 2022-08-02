package com.example.questionsApp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.questionsApp.models.AnswerToSubmit
import com.example.questionsApp.network.controllers.GetQuestionsController
import com.example.questionsApp.network.controllers.SubmitAnswerController
import com.google.gson.internal.LinkedTreeMap


class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val mainController: GetQuestionsController by lazy { GetQuestionsController() }
    private val submitController: SubmitAnswerController by lazy { SubmitAnswerController() }
    private val responseMutable: MutableLiveData<ArrayList<LinkedTreeMap<String, Any>>?> = MutableLiveData()
    private val submittedAnswerMutable: MutableLiveData<AnswerToSubmit?> = MutableLiveData()

    suspend fun fetchQuestions() {
        postResponse(mainController.fetchQuestions())
    }

    private fun postResponse(response: ArrayList<LinkedTreeMap<String, Any>>?) {
        responseMutable.postValue(response)
    }

    fun observeResponse(lifecycleOwner: LifecycleOwner, observer: Observer<ArrayList<LinkedTreeMap<String, Any>>?>) {
        responseMutable.observe(lifecycleOwner, observer)
    }


    fun postSubmitedAnswer(answer:AnswerToSubmit?) {
        submittedAnswerMutable.postValue(answer)
    }

    fun observeSubmittedAnswer(lifecycleOwner: LifecycleOwner, observer: Observer<AnswerToSubmit?>) {
        submittedAnswerMutable.observe(lifecycleOwner, observer)
    }

    suspend fun submitAnswer(questionSubmit: AnswerToSubmit?) {
        submitController.submitAnswer(questionSubmit)
    }


}