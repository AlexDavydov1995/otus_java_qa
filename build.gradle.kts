plugins {
    java
    idea
    id("com.github.spotbugs") version "6.1.7"
    checkstyle
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
    testImplementation(platform("org.junit:junit-bom:${project.property("junitVersion")}"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.seleniumhq.selenium:selenium-java:${project.property("seleniumJavaVersion")}")
    testImplementation("io.github.bonigarcia:webdrivermanager:${project.property("webDriverManagerVersion")}")
    implementation("com.google.inject:guice:${project.property("guiceVersion")}")
    testImplementation("org.assertj:assertj-core:${project.property("assertjVersion")}")}

tasks.test {
    useJUnitPlatform()
    systemProperties = mapOf(
        "some.prop" to "value"
    )
    ignoreFailures = false
    testLogging {
        events("passed", "skipped", "failed")
    }
}