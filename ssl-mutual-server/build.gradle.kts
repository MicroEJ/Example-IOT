/*
 * Kotlin
 *
 * Copyright 2023-2024 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */

group = "com.microej.example.iot"
version = "3.0.0"

plugins {
    java
    application
}

application {
    mainClass.set("com.microej.example.iot.ssl.mutual.server.Server")
}

dependencies {
}