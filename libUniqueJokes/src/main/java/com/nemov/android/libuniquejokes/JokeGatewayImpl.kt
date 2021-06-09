package com.nemov.android.libuniquejokes

import android.content.Context
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.*
import kotlin.streams.toList

class JokeGatewayImpl(private val context: Context,
                      private val dispatcher: CoroutineDispatcher) : JokeGateway {

    override suspend fun saveJoke(joke: String) {
        CoroutineScope(dispatcher).launch {
            kotlin.runCatching {
                val fos = context.openFileOutput(USER_JOKES_FILE, Context.MODE_APPEND)
                val osw = OutputStreamWriter(fos)
                osw.appendLine(joke)
                osw.close()
            }
        }
    }

    override suspend fun getJokes(): List<String> {
        return withContext(dispatcher) {
            kotlin.runCatching {
                val fis = context.openFileInput(USER_JOKES_FILE)
                val isr = InputStreamReader(fis)
                val reader = BufferedReader(isr)
                val jokes = reader.lines().toList()
                reader.close()
                return@runCatching jokes
            }.getOrElse {
                emptyList()
            }
        }
    }

    companion object {
        private const val USER_JOKES_FILE = "user_jokes"
    }
}