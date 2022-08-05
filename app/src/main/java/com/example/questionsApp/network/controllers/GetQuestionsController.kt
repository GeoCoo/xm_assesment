package com.example.questionsApp.network.controllers

import com.example.questionsApp.network.NetworkResponse
import com.example.questionsApp.network.Service
import com.example.questionsApp.network.requests.QuestionsRequest

class GetQuestionsController : Service() {

    suspend fun fetchQuestions(): ArrayList<Any>? {
        val request = QuestionsRequest()
        return when (val response = doSuspendRequest<ArrayList<Any>?>(request)) {
            is NetworkResponse.Success<*> -> response.result as ArrayList<Any>?
            else -> {null}
        }
    }

}