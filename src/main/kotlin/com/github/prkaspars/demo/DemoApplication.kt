package com.github.prkaspars.demo

import io.opentracing.Tracer
import io.opentracing.noop.NoopTracerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class DemoApplication {

    @Bean
    fun tracer(): Tracer = NoopTracerFactory.create()
}

fun main(args: Array<String>) {
    runApplication<DemoApplication>(*args)
}
