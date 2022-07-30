package com.example.myapplication.network

import com.github.kittinunf.fuel.core.Parameters

abstract class BaseRequest {

    // ===========================================================
    // Constants
    // ===========================================================

    enum class Method {
        GET,
        POST
    }


    // ===========================================================
    // Fields
    // ===========================================================

    val defaultHeaders: Map<String, String> = mutableMapOf("Content-Type" to "application/json",
        "charset" to "utf-8")

    val defaultUrlParams: Parameters? = null

    abstract var method: Method
    open var path: String = ""
    open var baseUrl: String = "https://powerful-peak-54206.herokuapp.com/"
    open var header: Map<String, String>? = null
    open var body: String? = null
    open var queryParameter: Parameters? = null
    // ===========================================================
    // Constructors
    // ===========================================================

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    // ===========================================================
    // Methods
    // ===========================================================

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================

}