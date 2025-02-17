/*
 * Copyright (c) 2021 Proton Technologies AG
 * This file is part of Proton Technologies AG and ProtonCore.
 *
 * ProtonCore is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * ProtonCore is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with ProtonCore.  If not, see <https://www.gnu.org/licenses/>.
 */

plugins {
    `kotlin-dsl`
    kotlin("jvm")
    kotlin("plugin.serialization")
    `java-gradle-plugin`
}

publishOption.shouldBePublishedAsPlugin = true

gradlePlugin {
    plugins {
        create("plugin") {
            id = "me.proton.core.gradle-plugins.detekt"
            displayName = "Proton detekt plugin"
            description = "Plugin to apply Proton detekt configuration"
            implementationClass = "ProtonDetektPlugin"
        }
    }
}

repositories {
    google()
    mavenCentral()
    maven("https://plugins.gradle.org/m2/")
}

dependencies {
    implementation(gradleApi())
    implementation(libs.arturbosch.detekt)
    implementation(libs.kotlin.serialization.json) {
        // Pick a version that is compatible with `embeddedKotlinVersion`:
        // https://docs.gradle.org/current/userguide/compatibility.html#kotlin
        version { require("1.3.1") }
    }
    implementation(libs.easyGradle.dsl)
}
