package me.susieson.cryptocharts.common

import java.util.concurrent.Executor
import java.util.concurrent.Executors

class AppExecutors(
    private val diskIO: Executor = Executors.newSingleThreadExecutor(),
    private val networkIO: Executor = Executors.newFixedThreadPool(3)
) {

    fun diskIO(): Executor {
        return diskIO
    }

    fun networkIO(): Executor {
        return networkIO
    }
}