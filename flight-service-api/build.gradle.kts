plugins {
    kotlin("jvm")
    id("java-library")
}

group = "ru.gorynkin"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
}