package com.nemov.android.hellopepper.greeting.goodbye

import com.aldebaran.qi.sdk.QiContext
import com.aldebaran.qi.sdk.`object`.conversation.AutonomousReactionImportance
import com.aldebaran.qi.sdk.`object`.conversation.AutonomousReactionValidity
import com.aldebaran.qi.sdk.`object`.conversation.Bookmark
import com.aldebaran.qi.sdk.`object`.conversation.Chat
import com.aldebaran.qi.sdk.builder.ChatBuilder
import com.aldebaran.qi.sdk.builder.QiChatbotBuilder
import com.aldebaran.qi.sdk.builder.TopicBuilder
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
                goodbyeChatbot.goToBookmark(proposalBookmark, AutonomousReactionImportance.HIGH, AutonomousReactionValidity.IMMEDIATE)
            }
        }
    }

    override suspend fun sayGoodbyeAsync(): Deferred<Unit> =
        scope.async {
            if (this@GoodbyeControllerImpl::goodbyeChat.isInitialized) {
                goodbyeChat.run()
            }
        }

    override suspend fun doBowActionAsync(): Deferred<Unit> =
        scope.async {
            // TODO: do BOW ANIMATION HERE
        }

    companion object {
        const val PROPOSAL_BOOKMARK = "goodbye_proposal"
    }
}