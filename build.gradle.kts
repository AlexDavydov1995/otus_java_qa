plugins {
    java
    idea
    id("com.github.spotbugs") version "6.1.7"
    checkstyle
}

spotbugs {
    excludeFilter = file("$projectDir/config/spotbugs/findbugs-exclude.xml")
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

    implementation("org.slf4j:slf4j-api:2.0.12")
    implementation("ch.qos.logback:logback-classic:1.4.14")

    implementation("io.cucumber:cucumber-junit-platform-engine:7.17.0")
    implementation("io.cucumber:cucumber-java:7.17.0")
    implementation("io.cucumber:cucumber-guice:7.17.0")

    testImplementation("org.seleniumhq.selenium:selenium-chrome-driver:${project.property("seleniumJavaVersion")}")
    testImplementation("org.seleniumhq.selenium:selenium-edge-driver:${project.property("seleniumJavaVersion")}")
    testImplementation("org.seleniumhq.selenium:selenium-firefox-driver:${project.property("seleniumJavaVersion")}")

    testImplementation("ch.qos.logback:logback-classic:1.4.14")

    implementation("javax.annotation:javax.annotation-api:1.3.2")
    implementation("com.google.code.findbugs:jsr305:3.0.2")
    implementation("net.jcip:jcip-annotations:1.0")
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
    }
}