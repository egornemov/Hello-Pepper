package com.nemov.android.hellopepper.logger

import android.util.Log

class LoggerGatewayImpl : LoggerGateway {
    override fun log(tag: String, message: String) {
        Log.d(tag, message)
    }
}