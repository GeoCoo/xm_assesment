package com.example.questionsApp

import com.example.questionsApp.viewmodels.MainViewModel
import com.example.questionsApp.viewmodels.QuestionViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

class KoinModule(application: QuestionsApp) {
    val modules = module {
        viewModel { MainViewModel(application) }
        viewModel { QuestionViewModel(application) }
    }
}