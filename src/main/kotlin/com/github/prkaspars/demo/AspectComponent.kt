package com.github.prkaspars.demo

import io.opentracing.Tracer
import io.opentracing.log.Fields
import io.opentracing.tag.Tags
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.getBeanProvider
import org.springframework.context.ApplicationContext
import org.springframework.core.annotation.AnnotationUtils
import org.springframework.stereotype.Component

@Aspect
@Component
class AspectComponent(
    private val tracer: Tracer,
    private val context: ApplicationContext
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    private class MethodData(
        val sendArgs: Boolean,
        val params: List<Pair<String, Class<Any>>>
    )

    init {
        context.beanDefinitionNames.map { beanName ->
            context.getBean(beanName)
        }
    }

    @Around("@annotation(ReportTrace)")
    fun reportTrace(jointPoint: ProceedingJoinPoint): Any? {
        val signature = jointPoint.signature as MethodSignature
        val method = signature.method
        MethodData(
            method.getAnnotation(ReportTrace::class.java)?.reportArguments ?: false,
            signature.parameterNames.zip(signature.parameterTypes)
        )

        val span = tracer.buildSpan(method.name).start()

        return try {
            jointPoint.proceed()
        } catch (ex: Exception) {
            val fields = mapOf(
                Fields.EVENT to "error",
                Fields.ERROR_OBJECT to ex,
                Fields.MESSAGE to ex.message
            )
            Tags.ERROR.set(span, true)
            span.log(fields)
            throw ex
        } finally {
            span.finish()
        }
    }
}
