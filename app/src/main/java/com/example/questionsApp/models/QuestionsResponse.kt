package com.example.questionsApp.models

data class QuestionsResponse(
    @JvmField var questions: ArrayList<Question>? = arrayListOf()

)
