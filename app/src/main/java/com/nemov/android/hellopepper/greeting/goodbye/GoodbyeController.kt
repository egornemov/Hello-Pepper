package com.nemov.android.hellopepper.greeting.goodbye

import kotlinx.coroutines.Deferred

interface GoodbyeController {
    suspend fun sayGoodbye()
    suspend fun doBowActionAsync(): Deferred<Unit>
}