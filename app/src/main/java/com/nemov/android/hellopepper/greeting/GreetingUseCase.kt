package com.nemov.android.hellopepper.greeting

interface GreetingUseCase {
    suspend fun makeGreeting()
    fun finishGreeting()
}