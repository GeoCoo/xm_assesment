package com.example.questionsApp.network.requests

import com.example.questionsApp.utils.Method


class QuestionsRequest : BaseRequest() {
    override var method: Method
        get() = Method.GET
        set(value) {}

    override var path: String
        get() = "/questions"
        set(value) {}
}
