package com.example.questionsApp.network

import com.example.questionsApp.network.requests.BaseRequest
import com.example.questionsApp.utils.Method
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.ResponseResultOf
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

sealed class ServiceException : Exception() {
    data class JsonDesiriazion(val messageDesc: String, val e: Exception? = null) : Exception()
    class NotOkException : Exception()
    class TimeOutException : Exception()
}

sealed class NetworkResponse {
    data class Success<out T>(val result: T) : NetworkResponse()
    data class Error(val error: java.lang.Exception) : NetworkResponse()

}

enum class ResponseStatus(val code: Int) {
    OK(200),
    NOT_OK(400),

}

abstract class Service {

    val timeOutMilisTime: Int = 20 * 1000
    private var gSon: Gson = Gson()

    @Throws(Exception::class)
    suspend inline fun <reified T> doSuspendRequest(request: BaseRequest): NetworkResponse {
        return withContext(Dispatchers.IO) {
            var localResponse: ResponseResultOf<String>? = null
            localResponse = if (request.method == Method.GET) {
                Fuel.get(request.baseUrl + request.path, request.defaultUrlParams?.toList())
                    .header(request.header ?: request.defaultHeaders)
                    .timeoutRead(timeOutMilisTime)
                    .responseString()

            } else {
                Fuel.post(request.baseUrl + request.path, request.defaultUrlParams?.toList())
                    .header(request.header ?: request.defaultHeaders)
                    .timeoutRead(timeOutMilisTime)
                    .responseString()
            }

            val statusCode = localResponse.second.statusCode
            if (statusCode == ResponseStatus.NOT_OK.code) {
                return@withContext NetworkResponse.Error(ServiceException.NotOkException())
            }

            val (payload, error) = localResponse.third
            if (error?.isTimeOut() == true) {
                return@withContext NetworkResponse.Error(ServiceException.TimeOutException())
            }
            try {
                val modelDesiriazed = Gson().fromJson(payload, T::class.java)
                when {
                    modelDesiriazed != null -> {
                        return@withContext NetworkResponse.Success(modelDesiriazed)
                    }
                    modelDesiriazed == null && statusCode == ResponseStatus.OK.code -> {
                        return@withContext NetworkResponse.Success(ResponseStatus.OK.code)
                    }
                    else -> {
                        val exception = ServiceException.JsonDesiriazion("Deserialzed error at ${T::class.java.name}")
                        return@withContext NetworkResponse.Error(exception)
                    }
                }
            } catch (e: Exception) {
                val exception = ServiceException.JsonDesiriazion("Deserialzed error at ${T::class.java.name}", e)
                return@withContext NetworkResponse.Error(exception)
            }
        }
    }
}

fun FuelError.isTimeOut(): Boolean {
    return this.exception.message == "timeout"
}
