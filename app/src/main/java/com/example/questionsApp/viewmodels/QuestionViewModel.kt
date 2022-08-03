package com.example.questionsApp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.questionsApp.ui.viewUtils.ButtonView

class QuestionViewModel(application: Application) : AndroidViewModel(application) {

    private var clickCount: Int = 1
    private val counterMutable: MutableLiveData<Int> by lazy { MutableLiveData<Int>() }
    private var submittedIdsMutable: MutableLiveData<List<Int>?> = MutableLiveData()
    private val list: ArrayList<Int> = arrayListOf()

    fun getCountTotal(): MutableLiveData<Int> {
        counterMutable.postValue(clickCount)
        return counterMutable
    }

    fun getNextCount() {
        clickCount += 1
        counterMutable.postValue(clickCount)
    }

    fun getPreviousCount() {
        clickCount -= 1
        counterMutable.postValue(clickCount)
    }

    fun postSubmittedId(id: Int) {
        list.add(id)
        submittedIdsMutable.postValue(list)
    }


    fun observeSubmittedIds(lifecycleOwner: LifecycleOwner, observer: Observer<List<Int>?>) {
        submittedIdsMutable.observe(lifecycleOwner, observer)

    }

    fun checkIfSubmitted(id: Int?): ButtonView.ButtonStates {
        return if (list.contains(id)) ButtonView.ButtonStates.SUBMITTED else ButtonView.ButtonStates.SUBMIT
    }


}