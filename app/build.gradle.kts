/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Kotlin application project to get you started.
 * For more details on building Java & JVM projects, please refer to https://docs.gradle.org/8.3/userguide/building_java_projects.html in the Gradle documentation.
 */
import org.jetbrains.compose.desktop.application.dsl.TargetFormat


plugins {
    // Apply the org.jetbrains.kotlin.jvm Plugin to add support for Kotlin.
    id("org.jetbrains.kotlin.jvm") version "1.9.0"

    // Kotlin Annotation Processing Tool
    id("org.jetbrains.kotlin.kapt") version "1.9.0"

    // Compose
    id("org.jetbrains.compose") version "1.5.10-rc01"

    id("app.cash.sqldelight") version "2.0.0"

    // Serialization
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.10"

    // Ktor Serialization
//    id("io.ktor.plugin") version "2.3.5"
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

sqldelight {
    databases {
        create("Database") {
            packageName.set("cs346.sqldelight")
        }
    }
}

dependencies {
    implementation(kotlin("test-junit"))
    implementation(kotlin("test-common"))
    implementation("org.jetbrains.compose.ui:ui-test-junit4:1.5.10-rc01")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.1")
    implementation("io.kotest:kotest-assertions-core:5.2.3")

    // Ktor
    implementation("io.ktor:ktor-client-core:2.3.5")
    implementation("io.ktor:ktor-client-cio:2.3.5")
    implementation("io.ktor:ktor-server-core:2.3.5")
    implementation("io.ktor:ktor-server-netty:2.3.5")
    implementation("io.ktor:ktor-server-content-negotiation:2.3.5")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.5")
    implementation("ch.qos.logback:logback-classic:1.4.11")
    testImplementation("io.ktor:ktor-server-test-host:2.3.5")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")

    // JSON Serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")


    // These dependencies are used by the application.
    implementation(compose.desktop.currentOs)
    implementation("com.halilibo.compose-richtext:richtext-commonmark:0.17.0")
    implementation("org.jetbrains.compose.material3:material3-desktop:1.2.1")

    implementation("app.cash.sqldelight:sqlite-driver:2.0.0")
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.1")
}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(19))
    }
}

tasks.named<Test>("test") {
    // Use JUnit Platform for unit tests.
//    useJUnitPlatform()
}

compose.desktop {
    application {
        mainClass = "cs346.AppKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "Coursify"
            description = "Course Notes App"
            packageVersion = "2.0.0"
            macOS {
                iconFile.set(project.file("src/main/resources/images/icon.icns"))
                infoPlist {
                    extraKeysRawXml = "  <key>NSDesktopFolderUsageDescription</key>\n  <string>We need access to files to read and write files.</string>"
                }
            }
            windows {
                iconFile.set(project.file("src/main/resources/images/icon.ico"))
            }
            linux {
                iconFile.set(project.file("src/main/resources/images/icon.png"))
            }
            modules("java.sql")
        }
    }
}
