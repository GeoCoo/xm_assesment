package com.example.questionsApp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

 class QuestionViewModel(application: Application) : AndroidViewModel(application) {

    private var clickCount: Int = 1
    private var counterMutable = MutableLiveData<Int>()

     fun getCountTotal(): MutableLiveData<Int> {
         counterMutable.value = clickCount
        return counterMutable
    }

     fun getNextCount() {
        clickCount += 1
         counterMutable.value = clickCount
    }

     fun getPreviousCount() {
        clickCount -= 1
         counterMutable.value = clickCount
    }
}