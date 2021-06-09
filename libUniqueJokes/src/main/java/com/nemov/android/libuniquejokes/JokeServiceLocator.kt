package com.nemov.android.libuniquejokes

import android.content.Context
import kotlinx.coroutines.CoroutineDispatcher

interface JokeServiceLocator {
    fun provideUseCaseDispatcher(): CoroutineDispatcher
    fun provideJokeUseCase(): JokeUseCase
    fun provideJokeView(): JokePresenter.JokeView
    fun provideJokePresenter(): JokePresenter
    fun provideJokeController(): JokeController
    fun provideContext(): Context
    fun provideDispatcher(): CoroutineDispatcher
    fun provideJokeGateway(): JokeGateway
}