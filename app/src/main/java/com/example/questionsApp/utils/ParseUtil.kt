package com.example.questionsApp.utils

import com.example.questionsApp.models.Question
import com.google.gson.internal.LinkedTreeMap
import kotlin.math.roundToInt


fun ArrayList<LinkedTreeMap<String, Any>>?.convertToModel(): List<Question> {
    val list: MutableList<Question> = mutableListOf()
    this?.forEach {
        list.add(Question((it["id"] as Double).convertToString(), it["question"].toString()))
    }
    return list
}


fun Double.convertToString(): String {
    return this.roundToInt().toString()
}