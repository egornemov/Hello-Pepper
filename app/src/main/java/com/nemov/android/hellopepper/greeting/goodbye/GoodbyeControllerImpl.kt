package com.nemov.android.hellopepper.greeting.goodbye

import com.aldebaran.qi.sdk.QiContext
import com.aldebaran.qi.sdk.`object`.actuation.Animate
import com.aldebaran.qi.sdk.`object`.conversation.AutonomousReactionImportance
import com.aldebaran.qi.sdk.`object`.conversation.AutonomousReactionValidity
import com.aldebaran.qi.sdk.`object`.conversation.Bookmark
import com.aldebaran.qi.sdk.`object`.conversation.Chat
import com.aldebaran.qi.sdk.builder.*
import com.nemov.android.hellopepper.R
import kotlinx.coroutines.*

class GoodbyeControllerImpl(qiContext: QiContext,
                            dispatcher: CoroutineDispatcher) : GoodbyeController {

    private lateinit var goodbyeChat: Chat

    private val scope = CoroutineScope(dispatcher)

    init {
        scope.launch {
            val goodbyeTopic = TopicBuilder.with(qiContext)
                .withResource(R.raw.goodbye)
                .build()
            val goodbyeChatbot = QiChatbotBuilder.with(qiContext)
                .withTopic(goodbyeTopic)
                .build()
            goodbyeChat = ChatBuilder.with(qiContext)
                .withChatbot(goodbyeChatbot)
                .build()
            val bookmarks: Map<String, Bookmark> = goodbyeTopic.bookmarks
            val proposalBookmark = bookmarks[PROPOSAL_BOOKMARK]
            goodbyeChat.addOnStartedListener {
                goodbyeChatbot.goToBookmark(
                    proposalBookmark,
                    AutonomousReactionImportance.HIGH,
                    AutonomousReactionValidity.IMMEDIATE
                )
            }
        }
    }

    private lateinit var animateBow: Animate

    init {
        scope.launch {
            val animation = AnimationBuilder.with(qiContext)
                .withResources(R.raw.bow)
                .build()
            animateBow = AnimateBuilder.with(qiContext)
                .withAnimation(animation)
                .build()
        }
    }

    override suspend fun sayGoodbye() {
        scope.launch {
            if (this@GoodbyeControllerImpl::goodbyeChat.isInitialized) {
                goodbyeChat.run()
            }
        }
    }

    override suspend fun doBowAction(): Job =
        scope.launch {
            if (this@GoodbyeControllerImpl::animateBow.isInitialized) {
                animateBow.run()
            }
        }

    companion object {
        const val PROPOSAL_BOOKMARK = "goodbye_proposal"
    }
}