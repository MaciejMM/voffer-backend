plugins {
    java
    id("org.springframework.boot") version "3.3.5"
    id("io.spring.dependency-management") version "1.1.6"
    id("io.swagger.core.v3.swagger-gradle-plugin") version "2.2.9"

}

tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    mainClass.set("com.example.freight.FreightApplication")
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

object Versions {
    const val JAVA_VERSION = "21"
    const val JUNIT_JUPITER = "5.10.0"
    const val OKTA_SPRING_BOOT_STARTER = "3.0.5"
    const val JAVA_JWT = "4.4.0"
    const val AUTH0_CLIENT = "2.5.0"
    const val SWAGGER_VERSION = "2.1.0"
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(Versions.JAVA_VERSION)
    }
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(group = "org.springframework.boot", name = "spring-boot-starter-data-jpa")
    implementation(group = "org.springframework.boot", name = "spring-boot-starter-web")
    implementation(group = "org.springframework.boot", name = "spring-boot-starter-webflux")
    implementation(group = "org.springframework.boot", name = "spring-boot-starter-logging")
    implementation(group = "ch.qos.logback", name = "logback-classic")
    implementation(group = "com.auth0", name = "java-jwt", version = Versions.JAVA_JWT)
    implementation(group = "com.auth0", name = "auth0", version = Versions.AUTH0_CLIENT)
    implementation(
        group = "com.okta.spring",
        name = "okta-spring-boot-starter",
        version = Versions.OKTA_SPRING_BOOT_STARTER
    )
    implementation(
        group = "org.springdoc",
        name = "springdoc-openapi-starter-webmvc-ui",
        version = Versions.SWAGGER_VERSION
    )



    compileOnly(group = "org.projectlombok", name = "lombok")
    runtimeOnly(group = "org.postgresql", name = "postgresql")
    annotationProcessor(group = "org.projectlombok", name = "lombok")

    testImplementation(group = "org.junit.jupiter", name = "junit-jupiter", version = Versions.JUNIT_JUPITER)
    testImplementation(group = "org.springframework.boot", name = "spring-boot-starter-test")
    testImplementation(group = "org.springframework.security", name = "spring-security-test")
    testImplementation(group = "io.projectreactor", name = "reactor-test")
    testImplementation(group = "org.junit.platform", name = "junit-platform-launcher")
    testImplementation(group = "com.h2database", name = "h2")

}

tasks.withType<Test> {
    useJUnitPlatform()
}
tasks.withType<JavaCompile> {
    options.forkOptions.memoryMaximumSize = "512m"
}

tasks.withType<JavaExec> {
    jvmArgs = listOf("-Xmx512m")
}