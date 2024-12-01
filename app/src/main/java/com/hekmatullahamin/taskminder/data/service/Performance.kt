package com.hekmatullahamin.taskminder.data.service

import com.google.firebase.perf.metrics.Trace
import com.google.firebase.perf.trace

/**
 * Executes a block of code within a named trace, allowing for performance monitoring or debugging.
 *
 * This function creates a [Trace] instance with the specified name, runs the provided block within
 * the context of the trace, and automatically handles the lifecycle of the trace (starting and stopping it).
 *
 * @param T The return type of the block.
 * @param name The name of the trace, typically used for identifying and monitoring the operation.
 * @param block The code block to execute within the trace. The block has access to the [Trace] instance.
 * @return The result of the executed block.
 *
 * @sample
 * ```
 * val result = trace("sampleTrace") {
 *     // Perform some operation here
 *     someFunction()
 * }
 * ```
 */
inline fun <T> trace(name: String, block: Trace.() -> T): T = Trace.create(name).trace(block)