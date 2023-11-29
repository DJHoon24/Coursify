/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Kotlin application project to get you started.
 * For more details on building Java & JVM projects, please refer to https://docs.gradle.org/8.3/userguide/building_java_projects.html in the Gradle documentation.
 */
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

kotlin {
    jvmToolchain(17)
}



plugins {
    // Kotlin Annotation Processing Tool
    kotlin("jvm")
    id("org.jetbrains.kotlin.kapt") version "1.9.0"

    // Compose
    id("org.jetbrains.compose") version "1.5.10-rc01"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.10"
}

dependencies {
    implementation(kotlin("test-junit"))
    implementation(kotlin("test-common"))
    testImplementation("io.mockk:mockk:1.12.0")
    implementation("io.ktor:ktor-client-mock:2.0.1")
    implementation("org.jetbrains.compose.ui:ui-test-junit4:1.5.10-rc01")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.1")
    implementation("io.kotest:kotest-assertions-core:5.5.5")

    // Ktor
    implementation("io.ktor:ktor-client-core:2.3.5")
    implementation("io.ktor:ktor-client-cio:2.3.5")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.5")
    implementation("io.ktor:ktor-client-content-negotiation:2.3.5")
    implementation("ch.qos.logback:logback-classic:1.4.11")
    testImplementation("io.ktor:ktor-server-test-host:2.3.5")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")

    // JSON Serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")


    implementation(compose.desktop.currentOs)
    implementation("com.halilibo.compose-richtext:richtext-commonmark:0.17.0")
    implementation("org.jetbrains.compose.material3:material3-desktop:1.2.1")

    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.1")

    // Animated GIF Support

}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(20))
    }
}

tasks.named<Test>("test") {

}

compose.desktop {
    application {
        mainClass = "cs346.AppKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "Coursify"
            description = "Course Notes App"
            packageVersion = "3.0.0"
            macOS {
                iconFile.set(project.file("src/main/resources/images/icon.icns"))
                infoPlist {
                    extraKeysRawXml =
                        "  <key>NSDocumentsFolderUsageDescription</key>\n  <string>We need access to files to read and write files.</string>"
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
