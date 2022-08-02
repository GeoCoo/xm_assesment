package com.example.questionsApp.utils

import android.util.Log
import com.example.questionsApp.models.Question
import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap
import java.util.*


fun ArrayList<LinkedTreeMap<String,Any>>?.convertToModel():List<Question>{
    val list:MutableList<Question> = mutableListOf()
    this?.forEach{
        list.add(Question(it["id"].toString(), it["question"].toString()))
    }
    return list
}