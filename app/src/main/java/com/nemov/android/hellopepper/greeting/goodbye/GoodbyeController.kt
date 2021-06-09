package com.nemov.android.hellopepper.greeting.goodbye

import kotlinx.coroutines.Job

interface GoodbyeController {
    suspend fun sayGoodbye()
    suspend fun doBowAction(): Job
}