package com.nemov.android.hellopepper.greeting.goodbye

import kotlinx.coroutines.Deferred

interface GoodbyeController {
    suspend fun sayGoodbyeAsync(): Deferred<Unit>
    suspend fun doBowActionAsync(): Deferred<Unit>
}