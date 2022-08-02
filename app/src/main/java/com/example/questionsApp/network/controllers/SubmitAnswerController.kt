package com.example.questionsApp.network.controllers

import NetworkResponse
import Service
import com.example.questionsApp.models.AnswerToSubmit
import com.example.questionsApp.network.requests.SubmitAnswerRequest

class SubmitAnswerController : Service() {

    suspend fun submitAnswer(questionSubmit: AnswerToSubmit?): Any? {
        val request = SubmitAnswerRequest(questionSubmit)
        return when (val response = doSuspendRequest<Any?>(request)) {
            is NetworkResponse.Success<*> -> response.result
            else -> null
        }
    }

}