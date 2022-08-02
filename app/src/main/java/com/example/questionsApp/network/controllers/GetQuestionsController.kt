package com.example.questionsApp.network.controllers

import NetworkResponse
import Service
import com.example.questionsApp.network.requests.QuestionsRequest
import com.google.gson.internal.LinkedTreeMap

class GetQuestionsController : Service() {

    suspend fun fetchQuestions(): ArrayList<*>? {
        val request = QuestionsRequest()
        return when (val response = doSuspendRequest<ArrayList<*>?>(request)) {
            is NetworkResponse.Success<*> -> response.result as ArrayList<*>?
            else -> null
        }
    }

}