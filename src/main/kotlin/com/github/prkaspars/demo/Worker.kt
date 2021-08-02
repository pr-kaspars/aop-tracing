package com.github.prkaspars.demo

import org.springframework.stereotype.Component

@Component
class Worker {

    @ReportTrace(true)
    fun work(n: Int, l: Iterable<String>): Int {
        println("Doing some work $n $l")
        return 100 / n
    }
}
