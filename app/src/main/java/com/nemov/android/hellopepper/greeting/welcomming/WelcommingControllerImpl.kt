package com.nemov.android.hellopepper.greeting.welcomming

import com.aldebaran.qi.sdk.QiContext
import com.aldebaran.qi.sdk.`object`.conversation.Chat
import com.aldebaran.qi.sdk.builder.ChatBuilder
import com.aldebaran.qi.sdk.builder.QiChatbotBuilder
import com.aldebaran.qi.sdk.builder.TopicBuilder
import com.nemov.android.hellopepper.R
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class WelcommingControllerImpl(qiContext: QiContext,
                               dispatcher: CoroutineDispatcher) : WelcommingController {

    private lateinit var welcommingChat: Chat

    private val scope = CoroutineScope(dispatcher)

    init {
        scope.launch {
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
    }

    override suspend fun startWelcommingChat() {
        scope.launch {
            if (this@WelcommingControllerImpl::welcommingChat.isInitialized) {
                welcommingChat.run()
            }
        }
    }
}