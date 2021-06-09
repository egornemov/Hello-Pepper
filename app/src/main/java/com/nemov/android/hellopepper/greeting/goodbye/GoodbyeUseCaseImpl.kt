package com.nemov.android.hellopepper.greeting.goodbye

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class GoodbyeUseCaseImpl(serviceLocator: GoodbyeServiceLocator): GoodbyeUseCase {

    private val dispatcher = serviceLocator.provideUseCaseDispatcher()
    private val presenter: GoodbyePresenter by lazy { serviceLocator.provideGoodbyePresenter() }
    private val controller: GoodbyeController by lazy { serviceLocator.provideGoodbyeController() }
    private val loggerGateway = serviceLocator.provideLoggerGateway()

    override suspend fun doGoodbyeAction() {
        CoroutineScope(dispatcher).launch {
            val bowJob = controller.doBowAction()
            controller.sayGoodbye()
            bowJob.join()
            loggerGateway.log(TAG, "Greeting is finished")
            presenter.finishGoodbyeAction()
        }
    }

    companion object {
        private const val TAG = "Hello Pepper"
    }
}