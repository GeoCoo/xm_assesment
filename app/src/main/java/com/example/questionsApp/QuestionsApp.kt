package com.example.questionsApp

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class QuestionsApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@QuestionsApp)
            modules(KoinModule(this@QuestionsApp).modules)
        }
    }
}