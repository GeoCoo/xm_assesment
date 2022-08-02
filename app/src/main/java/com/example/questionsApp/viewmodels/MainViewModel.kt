package com.example.questionsApp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.questionsApp.network.controllers.GetQuestionsController
import com.google.gson.internal.LinkedTreeMap


class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val mainController: GetQuestionsController by lazy { GetQuestionsController() }
    private val responseMutable: MutableLiveData<ArrayList<LinkedTreeMap<String, Any>>?> = MutableLiveData()

    suspend fun fetchQuestions() {
        postResponse(mainController.fetchQuestions())
    }

    private fun postResponse(response: ArrayList<LinkedTreeMap<String, Any>>?) {
        responseMutable.postValue(response)
    }

    fun observeResponse(lifecycleOwner: LifecycleOwner, observer: Observer<ArrayList<LinkedTreeMap<String, Any>>?>) {
        responseMutable.observe(lifecycleOwner, observer)
    }


}