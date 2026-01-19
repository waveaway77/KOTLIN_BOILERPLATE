package com.example.demo.aspect

import org.aspectj.lang.annotation.Aspect
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.multipart.MultipartFile
import kotlin.reflect.jvm.kotlinFunction

@Aspect
@Component
@Order(Ordered.LOWEST_PRECEDENCE)
class LoggingAspect {
    private val log = KotlinLogging.logger {}

    @Pointcut("bean(*Controller)")
    fun controllerLayer() {}

    @Pointcut("bean(*Service)")
    fun serviceLayer() {}

    @Around("controllerLayer() || serviceLayer()")
    fun controller(pjp: ProceedingJoinPoint): Any? {

        val sig = pjp.signature as MethodSignature
        val method = sig.method
        val className = method.declaringClass.simpleName
        val methodName = method.name
        val kfun = method.kotlinFunction

        val argString = buildArgsString(
            sig.parameterNames,
            pjp.args
        )

        if (log.isInfoEnabled()) { log.info { "$className.$methodName args=$argString" } } // logmessage here

        val start = System.currentTimeMillis()
        val result = pjp.proceed()
        val took = System.currentTimeMillis() - start
        if (log.isInfoEnabled()) { log.info { "$className.$methodName took=${took}ms result=${stringify(result)}" } } // logmessage here

        return result
    }

    private fun buildArgsString(names: Array<String>?, args: Array<Any?>): String {
        if (args.isEmpty()) return ""

        val sb = StringBuilder()
        for (i in args.indices) {
            val name = names?.getOrNull(i) ?: "arg$i"
            val v  = args[i]
            if (shouldSkip(v)) continue

//            val masked = MaskingUtils.toMaksedLog(v)
            val masked = ""

            if (sb.isNotEmpty()) sb.append(", ")
            sb.append("$name=").append(masked)
        }
        return sb.toString()
    }

    private fun shouldSkip(v: Any?): Boolean = when (v) {
        null -> false
        is HttpServletRequest, is HttpServletResponse,
        is MultipartFile, is Array<*>,
        is BindingResult -> true

        else -> false
    }

    private fun stringify(v: Any?): String = when (v) {
        null -> "null"
        is String -> v
        is Number, is Boolean -> v.toString()
        is ResponseEntity<*> -> "ResponseEntity(status=${v.statusCode.value()}, body=${stringify(v.body)})"
        is Collection<*> -> v.joinToString(prefix = "[", postfix = "]") { stringify(it) }
        is Map<*,*> -> v.entries.joinToString(prefix = "{", postfix = "}") { "${it.key}=${stringify(it.value)}" }
        else -> runCatching { v.toString() }.getOrElse { v::class.simpleName ?: "obj" }
    }

}