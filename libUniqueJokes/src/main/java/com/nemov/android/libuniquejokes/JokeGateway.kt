package com.nemov.android.libuniquejokes

interface JokeGateway {
    suspend fun saveJoke(joke: String)
    suspend fun getJokes(): List<String>
}