package com.ivangarzab.bookclub.util

import com.ivangarzab.bark.Trainer
import com.ivangarzab.bark.Level
import com.ivangarzab.bark.Pack

/**
 * iOS implementation of createTestTrainer.
 */
actual fun createTestTrainer(): Trainer {
    return object : Trainer {
        override val volume: Level = Level.DEBUG
        override val pack: Pack = Pack.CONSOLE

        override fun handle(level: Level, tag: String, message: String, throwable: Throwable?) {
            // For now, using basic println
            val logMessage = buildString {
                append("[$level] $tag: $message")
                if (throwable != null) {
                    append("\n${throwable.stackTraceToString()}")
                }
            }
            println(logMessage)
        }
    }
}
