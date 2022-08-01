package com.example.questionsApp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.questionsApp.models.Question
import com.example.questionsApp.models.QuestionsResponse
import com.example.questionsApp.network.controllers.GetQuestionsController



class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val mainController: GetQuestionsController by lazy { GetQuestionsController() }

     suspend fun fetchQuestions(): ArrayList<Question>? =
        mainController.fetchQuestions()

}