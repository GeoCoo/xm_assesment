package com.example.myapplication.network.requests

import com.example.myapplication.network.BaseRequest


class QuestionsRequest : BaseRequest() {
    override var method: Method
        get() = Method.GET
        set(value) {}

    override var path: String
        get() = "/questions"
        set(value) {}
}
