package com.example.questionsApp.network.controllers

import NetworkResponse
import Service
import com.example.questionsApp.network.requests.QuestionsRequest

open class GetQuestionsController : Service() {

    open suspend fun fetchQuestions(): ArrayList<Any>? {
        val request = QuestionsRequest()
        return when (val response = doSuspendRequest<ArrayList<Any>?>(request)) {
            is NetworkResponse.Success<*> -> response.result as ArrayList<Any>?
            else -> {null}
        }
    }

}