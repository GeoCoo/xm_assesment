package com.example.questionsApp.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.example.questionsApp.models.Question
import com.google.gson.internal.LinkedTreeMap
import kotlin.math.roundToInt


fun ArrayList<LinkedTreeMap<String, Any>>?.convertToModel(): List<Question> {
    val list: MutableList<Question> = mutableListOf()
    this?.forEach {
        list.add(Question((it["id"] as Double).convertToInt(), it["question"].toString()))
    }
    return list
}


fun Double.convertToInt(): Int {
    return this.roundToInt()
}


fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }
    })
}