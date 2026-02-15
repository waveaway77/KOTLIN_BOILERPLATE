plugins {
    kotlin("jvm") version "2.2.21"
    kotlin("plugin.spring") version "2.2.21"
    id("org.springframework.boot") version "4.0.1"
    id("io.spring.dependency-management") version "1.1.7"
//    kotlin("plugin.jpa") version "2.2.21"
}

group = "org.example"
version = "0.0.1-SNAPSHOT"
description = "demo1"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

dependencies {
//    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
//    implementation("org.springframework.boot:spring-boot-starter-mongodb")
    implementation("org.springframework.boot:spring-boot-starter-webflux") // logback-classic, slf4j-api 포함
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-security")

    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("tools.jackson.module:jackson-module-kotlin")
    implementation("io.github.oshai:kotlin-logging-jvm:6.0.3")
//    implementation("jakarta.transaction:jakarta.transaction-api:2.0.1")
    implementation("org.apache.tomcat.embed:tomcat-embed-core")
    implementation("org.aspectj:aspectjweaver")
    implementation("io.micrometer:micrometer-registry-prometheus")
    implementation("io.micrometer:context-propagation")

    developmentOnly("org.springframework.boot:spring-boot-docker-compose") // 특히 docker-compose 플러그인은: bootRun 전에 composeUp을 자동으로 걸어두는 경우가 많다

//    runtimeOnly("com.mysql:mysql-connector-j")

//    testImplementation("org.springframework.boot:spring-boot-starter-data-jpa-test")
//    testImplementation("org.springframework.boot:spring-boot-starter-mongodb-test")
    testImplementation("org.springframework.boot:spring-boot-starter-security-test")
    testImplementation("org.springframework.boot:spring-boot-starter-webflux-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict", "-Xannotation-default-target=param-property")
    }
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
