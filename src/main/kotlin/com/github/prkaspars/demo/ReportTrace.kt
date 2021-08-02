package com.github.prkaspars.demo

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class ReportTrace(val reportArguments: Boolean)
