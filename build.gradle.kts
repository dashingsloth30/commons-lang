import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `java-library`
    `maven-publish`
    kotlin("jvm") version "1.8.10" // Kotlin version to use
}

repositories {
    mavenLocal()
    maven {
        url = uri("https://repository.apache.org/snapshots")
    }

    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.1")
    testImplementation("org.junit-pioneer:junit-pioneer:1.9.1")
    testImplementation("org.hamcrest:hamcrest:2.2")
    testImplementation("org.easymock:easymock:5.1.0")
    testImplementation("org.openjdk.jmh:jmh-core:1.36")
    testImplementation("org.openjdk.jmh:jmh-generator-annprocess:1.36")
    testImplementation("com.google.code.findbugs:jsr305:3.0.2")
    compileOnly("org.apache.commons:commons-text:1.10.0")

    testImplementation ("io.kotest:kotest-runner-junit5:5.5.5")
    testImplementation ("io.kotest:kotest-assertions-core:5.5.5")
    testImplementation ("io.kotest:kotest-property:5.5.5")
    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect:1.3.41"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.2.2")
    implementation("com.beust:klaxon:5.4")
    testImplementation("io.mockk:mockk:1.9")
}

group = "org.apache.commons"
version = "3.13.0-SNAPSHOT"
description = "Apache Commons Lang"
java.sourceCompatibility = JavaVersion.VERSION_1_8

val testsJar by tasks.registering(Jar::class) {
    archiveClassifier.set("tests")
    from(sourceSets["test"].output)
}

java {
    withSourcesJar()
    withJavadocJar()
}

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
        artifact(testsJar)
    }
}

tasks.withType<JavaCompile>() {
    options.encoding = "ISO-8859-1"
}

tasks.withType<Javadoc>() {
    options.encoding = "ISO-8859-1"
}



kotlin {
    jvmToolchain(8)
}