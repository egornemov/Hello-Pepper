package com.nemov.android.hellopepper.greeting.goodbye

class GoodbyeUseCaseImpl(serviceLocator: GoodbyeServiceLocator): GoodbyeUseCase {

    private val presenter: GoodbyePresenter by lazy { serviceLocator.provideGoodbyePresenter() }
    private val controller: GoodbyeController by lazy { serviceLocator.provideGoodbyeController() }
    private val loggerGateway = serviceLocator.provideLoggerGateway()

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