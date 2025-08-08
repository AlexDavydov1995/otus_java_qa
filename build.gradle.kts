plugins {
    java
    idea
    id("com.github.spotbugs") version "6.1.7"
    checkstyle
    id("io.freefair.lombok") version "8.1.0"
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

checkstyle {
    toolVersion = "${project.property("checkstyleVersion")}"
    configFile = file("${rootDir}/config/checkstyle/checkstyle.xml") // Путь к конфигу
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("org.junit:junit-bom:${project.property("junitVersion")}"))
    implementation("org.junit.jupiter:junit-jupiter")
    implementation("org.seleniumhq.selenium:selenium-java:${project.property("seleniumJavaVersion")}")
    implementation("io.github.bonigarcia:webdrivermanager:${project.property("webDriverManagerVersion")}")
    implementation("com.google.inject:guice:${project.property("guiceVersion")}")
    implementation("org.assertj:assertj-core:${project.property("assertjVersion")}")
    implementation("org.jsoup:jsoup:${project.property("jsoupVersion")}")
    implementation("org.apache.logging.log4j:log4j-core:2.23.1")
    implementation("org.apache.logging.log4j:log4j-api:2.23.1")

    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")

    // WireMock
    implementation("com.github.tomakehurst:wiremock:2.27.2")

    // RestAssured
    implementation("io.rest-assured:rest-assured:4.4.0")
    implementation("io.rest-assured:json-schema-validator:4.4.0")

    // Для SOAP-хелпера
    implementation("javax.xml.soap:javax.xml.soap-api:1.4.0")
    implementation("com.sun.xml.messaging.saaj:saaj-impl:1.5.2")
}

sourceSets {
    test {
        resources.srcDirs("src/test/resources")
    }
    main {
        resources.srcDirs("src/main/resources")
    }
}

tasks {
    processResources {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }
    processTestResources {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }
}

tasks.test {
    useJUnitPlatform()
    systemProperties = mapOf(
        "some.prop" to "value",
        "log4j2.debug" to "true"
    )
    ignoreFailures = false
    testLogging {
        events("passed", "failed", "skipped")
        showStandardStreams = true
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
    }
}

tasks.withType<Test> {
    project.findProperty("stub.url")?.let { baseUrlValue ->
        systemProperty("stub.url", baseUrlValue)
    }
    project.findProperty("cb.url")?.let { baseUrlValue ->
        systemProperty("cb.url", baseUrlValue)
    }
    project.findProperty("ps.url")?.let { baseUrlValue ->
        systemProperty("ps.url", baseUrlValue)
    }
}