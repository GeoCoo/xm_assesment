package com.example.myapplication.network.requests

import com.example.myapplication.models.QuestionSubmit
import com.example.myapplication.network.BaseRequest
import com.google.gson.JsonObject

class SubmitAnswerRequest(private var questionSubmit: QuestionSubmit) : BaseRequest() {
    override var method: Method
        get() = Method.POST
        set(value) {}

    override var path: String
        get() = "/question/submit"
        set(value) {}

    override var body: String?
        get() {
            val json = JsonObject()
            json.addProperty("id", questionSubmit.id)
            json.addProperty("answer", questionSubmit.answer)

            return json.toString()
        }
        set(value) {}
}
