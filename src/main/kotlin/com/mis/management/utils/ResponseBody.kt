package com.mis.management.utils

import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.http.HttpStatus
import java.io.Serializable

class ResponseBody<T : Any> : Serializable {

    var code: Int = 200
    var message: String = SUCCESS

    @JsonInclude(JsonInclude.Include.NON_NULL)
    var data: T? = null


    constructor(code: Int, message: String, data: T?) {
        this.code = code
        this.message = message
        this.data = data
    }
    constructor(){

    }
    companion object {
        val SUCCESS = "success"
        val FAILURE = "failure"


        fun  success(): ResponseBody<Any> {
            return ResponseBody(HttpStatus.OK.value(), SUCCESS, null)
        }


        fun  success(message: String): ResponseBody<Any> {
            return ResponseBody(HttpStatus.OK.value(), message, null)
        }


        fun  success(data: Any): ResponseBody<Any> {
            return ResponseBody(HttpStatus.OK.value(), SUCCESS, data)
        }


        fun  fail(message: String): ResponseBody<Any> {
            return ResponseBody(HttpStatus.BAD_REQUEST.value(), message, null)
        }


        fun  fail(data: Any): ResponseBody<Any> {
            return ResponseBody(HttpStatus.BAD_REQUEST.value(), FAILURE, data)
        }


        fun  response(code: Int, message: String): ResponseBody<Any> {
            return ResponseBody(code, message, null)
        }


        fun  response(code: Int, message: String, data: Any): ResponseBody<Any> {
            return ResponseBody(code, message, data)
        }
    }
}