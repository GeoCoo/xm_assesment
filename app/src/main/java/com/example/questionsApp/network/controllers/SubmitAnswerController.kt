package com.example.questionsApp.network.controllers

import NetworkResponse
import Service
import com.example.questionsApp.models.AnswerToSubmit
import com.example.questionsApp.network.requests.SubmitAnswerRequest

class SubmitAnswerController : Service() {

    suspend fun submitAnswer(questionSubmit: AnswerToSubmit?): String? {
        val request = SubmitAnswerRequest(questionSubmit)
        return when (val response = doSuspendRequest<String?>(request)) {
            is NetworkResponse.Success<*> -> response.result.toString()
            is NetworkResponse.Error -> response.error.toString()
            else -> null
        }
    }

}