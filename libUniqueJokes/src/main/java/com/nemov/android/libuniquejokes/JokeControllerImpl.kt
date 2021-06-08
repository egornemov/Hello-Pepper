package com.nemov.android.libuniquejokes

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

class JokeControllerImpl: JokeController {

    private val httpClient = HttpClient(Android) {
        install(JsonFeature) {
            serializer = GsonSerializer()
            acceptContentTypes += ContentType("application", "json")
        }
        expectSuccess = false
    }

    override suspend fun getJoke(): String? =
        httpClient.request<HttpResponse> {
            url(JOKE_PATH)
            method = HttpMethod.Get
        }.let {
            if (it.status.isSuccess()) {
                it.receive<Joke>().value.joke
            } else {
                null
            }
        }

    data class Joke(val value: JokeValue)

    data class JokeValue(val joke: String)

    companion object {
        const val JOKE_PATH = "http://api.icndb.com/jokes/random"
    }
}