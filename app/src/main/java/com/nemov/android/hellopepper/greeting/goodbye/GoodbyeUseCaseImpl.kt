package com.nemov.android.hellopepper.greeting.goodbye

class GoodbyeUseCaseImpl(serviceProvider: GoodbyeServiceProvider): GoodbyeUseCase {

    private val presenter: GoodbyePresenter by lazy { serviceProvider.provideGoodbyePresenter() }
    private val controller: GoodbyeController by lazy { serviceProvider.provideGoodbyeController() }
    private val loggerGateway = serviceProvider.provideLoggerGateway()

    override suspend fun doGoodbyeAction() {
        val deferredBow = controller.doBowActionAsync()
        val deferredGoodbye = controller.sayGoodbyeAsync()
        deferredGoodbye.await()
        deferredBow.await()
        loggerGateway.log(TAG, "Greeting is finished")
        presenter.finishGoodbyeAction()
    }

    companion object {
        private const val TAG = "Hello Pepper"
    }
}