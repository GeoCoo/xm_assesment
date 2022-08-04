package com.example.questionsApp.viewmodels

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.questionsApp.utils.ButtonStates
import com.example.questionsApp.utils.QuestionNavBtnState

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

    fun checkIfSubmitted(id: Int?): ButtonStates {
        return if (list.contains(id)) ButtonStates.ALREADY_SUBMITTED else ButtonStates.SUBMIT
    }

    fun checkSubmittedSuccess():ButtonStates{
        return ButtonStates.SUBMITTED_SUCCESS
    }

    fun setNavButtonVisibility(count: Int, size: Int?, state: QuestionNavBtnState): Int {
        return when (state) {
            QuestionNavBtnState.PREVIOUS -> {
                if (count in 2..size!!) View.VISIBLE else View.GONE
            }
            QuestionNavBtnState.NEXT -> {
                if (count in 1 until size!!) View.VISIBLE else View.GONE
            }
        }
    }
}