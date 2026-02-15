package com.example.demo.config

import jakarta.annotation.PostConstruct
import org.springframework.context.annotation.Configuration
import reactor.core.publisher.Hooks

// 자동 Context Propagation을 명시적으로 활성화
// Reactor Context가 Scheduler 이동 중에 끊겨서 MDC가 비어 있는 상태를 방지
@Configuration
class ReactorContextConfig {

    @PostConstruct
    fun enableContextPropagation() {
        Hooks.enableAutomaticContextPropagation()
    }
}
