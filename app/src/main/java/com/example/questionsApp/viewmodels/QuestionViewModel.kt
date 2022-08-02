package com.example.questionsApp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class QuestionViewModel(application: Application) : AndroidViewModel(application) {

    private var clickCount: Int = 1
    private var countLiveData = MutableLiveData<Int>()

    open fun getCountTotal(): MutableLiveData<Int> {
        countLiveData.value = clickCount
        return countLiveData
    }

    open fun getNextCount() {
        clickCount += 1
        countLiveData.value = clickCount
    }

    open fun getPreviousCount() {
        clickCount -= 1
        countLiveData.value = clickCount
    }


}