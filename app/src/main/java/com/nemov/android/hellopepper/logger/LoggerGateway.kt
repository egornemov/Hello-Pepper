package com.nemov.android.hellopepper.logger

interface LoggerGateway {
    fun log(tag: String, message: String)
}