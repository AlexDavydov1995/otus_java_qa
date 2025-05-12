plugins {
    java
    idea
    id("com.github.spotbugs") version "6.1.7"
    checkstyle
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
        events("passed", "skipped", "failed")
        showStandardStreams = true
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
    }
}