package com.example.questionsApp.network.controllers

import NetworkResponse
import Service
import com.example.questionsApp.models.Question
import com.example.questionsApp.network.requests.QuestionsRequest

class GetQuestionsController : Service() {

     suspend fun fetchQuestions(): ArrayList<Question>? {
        val request = QuestionsRequest()
        return when (val response = doSuspendRequest<ArrayList<Question>?>(request)) {
            is NetworkResponse.Success<*> -> response.result as ArrayList<Question>?
//            is NetworkResponse.Error -> fetchQuestions()
            else -> null
        }
    }

}