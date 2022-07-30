package com.example.myapplication.models

data class QuestionsResponse(
    @JvmField var questions: MutableList<Question> = mutableListOf()
)
