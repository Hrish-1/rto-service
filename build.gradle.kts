plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    kotlin("kapt") version "2.0.20"

    id("org.springframework.boot") version "3.3.4"
    id("io.spring.dependency-management") version "1.1.6"
    id("org.flywaydb.flyway") version "9.3.1"
    id("org.jooq.jooq-codegen-gradle") version "3.19.11"
}

group = "com.gmotors"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

kapt {
    keepJavacAnnotationProcessors = true
}

configurations {
    all {
        // Exclude default logging starter with Logback so we can use spring-boot-starter-log4j2:
        exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
    }

    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-jooq")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.springframework.boot:spring-boot-starter-mustache:3.3.4")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // Database
    runtimeOnly("org.postgresql:postgresql")
    jooqCodegen("org.postgresql:postgresql")
    implementation("org.flywaydb:flyway-core")
    runtimeOnly("org.flywaydb:flyway-database-postgresql")

    // Object mapping, validation, and other helpers
    val mapstructVersion = "1.5.2.Final"
    implementation("org.mapstruct:mapstruct:$mapstructVersion")
    kapt("org.mapstruct:mapstruct-processor:$mapstructVersion")
    implementation("org.apache.commons:commons-lang3")

    // API documentation
    implementation("org.springdoc:springdoc-openapi-ui:1.8.0")

    // Observability, logging, and metrics
    implementation("org.apache.logging.log4j:log4j-slf4j-impl")
    runtimeOnly("org.springframework.boot:spring-boot-starter-log4j2")
    runtimeOnly("com.lmax:disruptor:3.4.4") // needed for log4j2 async loggers

    // Development and Testing
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
    implementation("com.github.librepdf:openpdf:2.0.3")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:postgresql")
    testImplementation("io.rest-assured:rest-assured")
    implementation("io.github.serpro69:kotlin-faker:1.16.0")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict", "-Xjvm-default=all")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

// ------------
// DB, flyway, jooq code generation config.
// See https://www.jooq.org/doc/3.19/manual-single-page/#code-generation
flyway {
    url = System.getenv("FLYWAY_URL") ?: "jdbc:postgresql://localhost:5433/rto"
    user = System.getenv("FLYWAY_USER") ?: "postgres"
    password = System.getenv("FLYWAY_PASSWORD") ?: "postgres"
    cleanDisabled = false
    locations = System.getenv("FLYWAY_LOCATIONS")?.split(",")?.toTypedArray()
        ?: arrayOf(
            "filesystem:src/main/resources/db/migration", // from gradle, app may not be built yet, so load from filesystem
            "classpath:db/migration", // but also load from classpath as per usual in case app is built
            "filesystem:local-migrations",
        )
}

jooq {
    configuration {
        logging = org.jooq.meta.jaxb.Logging.WARN
        jdbc {
            driver = "org.postgresql.Driver"
            url = flyway.url
            user = flyway.user
            password = flyway.password
        }
        generator {
            name = "org.jooq.codegen.KotlinGenerator"
            database {
                name = "org.jooq.meta.postgres.PostgresDatabase"
                includes = ".*"
                inputSchema = "public"
            }
            generate {
                isDeprecated = false
                isRecords = true
                isDaos = false
                isPojos = false
                isFluentSetters = true
            }
            target {
                packageName = "com.gmotors.infra.jooq"
            }
        }
    }
}

buildscript {
    // Configure jooq plugin to use correct jooq version that matches spring boot managed jooq version.
    // See https://github.com/etiennestuder/gradle-jooq-plugin#configuring-the-jooq-generation-tool
    // and https://docs.spring.io/spring-boot/docs/current/reference/html/dependency-versions.html
    val jooqVersion = "3.19.11"
    configurations["classpath"].resolutionStrategy.eachDependency {
        if (requested.group == "org.jooq") {
            useVersion(jooqVersion)
        }
    }
}

tasks.compileKotlin {
    mustRunAfter(tasks.jooqCodegen)
}

// ------------
// Other helper tasks
tasks.register("initDb") {
    group = "db"
    description = "Runs flywayMigrate and jooqCodegen so regular build will have db dependencies"
    dependsOn(tasks.flywayMigrate, tasks.jooqCodegen)
}