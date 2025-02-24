/*
 * Kotlin
 *
 * Copyright 2023-2025 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */

group = "com.microej.example.iot"
version = "3.2.0"

plugins {
    id("com.microej.gradle.application") version "1.0.0"
}

dependencies {
    /*
     * Put your project dependencies here. An example of project dependency declaration is provided below:
     *
     * implementation("[org]:[otherArtifact]:[M.m.p]")
     * e.g.: implementation("ej.library.runtime:basictool:1.7.0")
     */

    //Core libraries.
    implementation("ej.api:edc:1.3.5")
    implementation("ej.api:bon:1.4.3")

    //This is used in case the application is run in standalone, in a multiapp firmware, the Connectivity Manager is provided by the firmware.
    implementation("ej.library.iot:cbor:1.1.0")

    /*
     * Put your test dependencies here. An example of test dependency declaration is provided below:
     *
     * testImplementation("[org]:[otherArtifact]:[M.m.p]")
     * e.g.: testImplementation("ej.library.test:junit:1.7.1")
     */

    /*
     * To use a VEE Port published in an artifact repository use this VEE Port dependency.
     */
    microejVee("com.microej.veeport.st.stm32f7508-dk:M5QNX_eval:2.2.0")
}

microej {
    applicationEntryPoint = "com.microej.example.iot.cbor.Main"
    skippedCheckers = "nullanalysis"
}