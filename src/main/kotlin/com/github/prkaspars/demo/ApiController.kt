package com.github.prkaspars.demo

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ApiController(private val worker: Worker) {

    @GetMapping("/api/launch")
    fun launch(@RequestParam n: Int): Int {
        return worker.work(n, listOf("A", "B", "C"))
    }
}
