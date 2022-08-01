package com.example.questionsApp.models

import com.google.gson.annotations.SerializedName

data class Question(
    @SerializedName("id") val id : Int,
    @SerializedName("question") val question : String
)
