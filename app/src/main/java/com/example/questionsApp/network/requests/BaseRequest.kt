package com.example.questionsApp.network.requests

import com.example.questionsApp.utils.Method
import com.github.kittinunf.fuel.core.Parameters

abstract class BaseRequest {

    val defaultHeaders: Map<String, String> = mutableMapOf("Content-Type" to "application/json",
        "charset" to "utf-8")

    val defaultUrlParams: Parameters? = null

    abstract var method: Method
    open var path: String = ""
    open var baseUrl: String = "https://powerful-peak-54206.herokuapp.com"
    open var header: Map<String, String>? = null
    open var body: String? = null
    open var queryParameter: Parameters? = null
}