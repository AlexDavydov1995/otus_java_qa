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
    configFile = file("${rootDir}/config/checkstyle/checkstyle.xml")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("org.junit:junit-bom:${project.property("junitVersion")}"))
    implementation("org.junit.jupiter:junit-jupiter")
    implementation("com.google.inject:guice:${project.property("guiceVersion")}")
    implementation("io.rest-assured:rest-assured:4.3.0")
    implementation("org.codehaus.jackson:jackson-mapper-asl:1.9.13")
    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")
    implementation("org.slf4j:slf4j-api:2.0.7")
    implementation("ch.qos.logback:logback-classic:1.4.7")
    implementation("io.rest-assured:json-schema-validator:4.3.1")
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
        events("failed")
        showStandardStreams = true
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
    }
}

tasks.withType<Test> {
    project.findProperty("baseUrl")?.let { baseUrlValue ->
        systemProperty("baseUrl", baseUrlValue)
    }
}