package com.nemov.android.libuniquejokes

interface JokeController {
    suspend fun getJoke(): String?
}