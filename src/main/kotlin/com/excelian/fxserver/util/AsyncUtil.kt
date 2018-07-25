package com.excelian.fxserver.util

import java.util.concurrent.CompletableFuture

/**
 * Created by dtsimbal on 7/25/18.
 */
fun <T> withCompletableFuture(block: (promise: CompletableFuture<T>) -> Unit): CompletableFuture<T> {
    val future = CompletableFuture<T>()
    block.invoke(future)
    return future
}
