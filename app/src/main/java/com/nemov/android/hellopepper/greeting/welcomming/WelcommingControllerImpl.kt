package com.nemov.android.hellopepper.greeting.welcomming

import com.aldebaran.qi.sdk.QiContext
import com.aldebaran.qi.sdk.`object`.conversation.Chat
import com.aldebaran.qi.sdk.builder.ChatBuilder
import com.aldebaran.qi.sdk.builder.QiChatbotBuilder
import com.aldebaran.qi.sdk.builder.TopicBuilder
import com.nemov.android.hellopepper.R
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class WelcommingControllerImpl(qiContext: QiContext,
                               private val dispatcher: CoroutineDispatcher) : WelcommingController {

    private val welcommingChat: Chat

    init {
        val goodbyeTopic = TopicBuilder.with(qiContext)
            .withResource(R.raw.welcomming)
            .build()
        val goodbyeChatbot = QiChatbotBuilder.with(qiContext)
            .withTopic(goodbyeTopic)
            .build()
        welcommingChat = ChatBuilder.with(qiContext)
            .withChatbot(goodbyeChatbot)
            .build()
    }

    override suspend fun startWelcommingChat() {
        withContext(dispatcher) {
            welcommingChat.run()
        }
    }
}