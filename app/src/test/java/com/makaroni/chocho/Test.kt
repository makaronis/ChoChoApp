package com.makaroni.chocho

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.junit.Test
import java.util.concurrent.Executors

/**
 * You can edit, run, and share this code.
 * play.kotlinlang.org
 */


class Counter() {
    var value: Long = 0L

    fun increment() {
        var temp = value
        temp += 1
        value = temp
    }

    fun decrement() {
        value--
    }
}

class Test() {
    val counter = Counter()
    var incrementRunnable = Runnable{
        for(i in 1..1000) {
            counter.increment()
            println("${counter.value} i=$i")
        }
    }
    var decrementRunnable = Runnable{
        for(i in 1..1000) {
            counter.decrement()
            println("${counter.value} i=$i")
        }
    }

    private val scope = CoroutineScope(Dispatchers.Unconfined)
    @Test
    fun start() {
//        IntArray
        val cachedPool = Executors.newCachedThreadPool()
        cachedPool.execute(incrementRunnable)
        cachedPool.execute(decrementRunnable)
        scope.launch {  delay(10_000)
        println(counter.value)
        }
    }
}

