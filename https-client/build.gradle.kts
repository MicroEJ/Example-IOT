/*
 * Kotlin
 *
 * Copyright 2023-2025 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */

group = "com.microej.example.iot"
version = "1.2.0"

plugins {
    id("com.microej.gradle.application") version libs.versions.microej.sdk
}


microej {
    applicationEntryPoint = "com.microej.example.iot.https.Main"
    skippedCheckers = "nullanalysis"
}

dependencies {
    /*
     * Put your project dependencies here. An example of project dependency declaration is provided below:
     *
     * implementation("[org]:[otherArtifact]:[M.m.p]")
     * e.g.: implementation(libs.library.basictool)
     */

    //Core libraries.
    implementation(libs.api.edc)
    implementation(libs.api.bon)
    implementation(libs.api.net)
    implementation(libs.api.ssl)
    implementation(libs.library.service)

    //Libraries used for the example.
    implementation(libs.library.httpsclient)
    implementation(libs.library.restclient)
    implementation(libs.library.logging)

    //This is used in case the application is run in standalone, in a multiapp firmware, the Connectivity Manager is provided by the firmware.
    implementation(libs.library.connectivity)

    /*
     * To use a VEE Port published in an artifact repository use this VEE Port dependency.
     */
   microejVee(libs.vee.port.nxp.mimxrt1170)
}