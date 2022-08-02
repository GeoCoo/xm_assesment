package com.example.questionsApp.network.controllers

import NetworkResponse
import Service
import com.example.questionsApp.models.EmptyResponse
import com.example.questionsApp.models.QuestionSubmit
import com.example.questionsApp.network.requests.SubmitAnswerRequest

class SubmitAnswerController:Service() {

    suspend fun submitAnswer(questionSubmit: QuestionSubmit): EmptyResponse? {
        val request = SubmitAnswerRequest(questionSubmit)
        return when (val response = doSuspendRequest<EmptyResponse>(request)) {
            is NetworkResponse.Success<*> -> response.result as EmptyResponse?
            else -> null
        }
    }

}