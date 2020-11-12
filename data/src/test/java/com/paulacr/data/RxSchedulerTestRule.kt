package com.paulacr.data

import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.internal.schedulers.TrampolineScheduler
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.TestScheduler
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class RxSchedulerTestRule(val onEvaluate: (() -> Unit)? = null) : TestRule {
    val computationScheduler = TestScheduler()
    override fun apply(base: Statement?, description: Description?): Statement {
        return object : Statement() {
            override fun evaluate() {
                RxJavaPlugins.reset()
                RxAndroidPlugins.reset()
                onEvaluate?.invoke()
                RxJavaPlugins.setErrorHandler { it.printStackTrace() }
                RxJavaPlugins.setInitIoSchedulerHandler { TrampolineScheduler.instance() }
                RxJavaPlugins.setInitNewThreadSchedulerHandler { TrampolineScheduler.instance() }
                RxJavaPlugins.setComputationSchedulerHandler { computationScheduler }
                RxAndroidPlugins.setInitMainThreadSchedulerHandler { TrampolineScheduler.instance() }
                base?.evaluate()
                RxAndroidPlugins.reset()
                RxJavaPlugins.reset()
            }
        }
    }
}