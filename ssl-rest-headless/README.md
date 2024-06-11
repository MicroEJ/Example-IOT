# Overview

This example is a simple rest client connecting over TLS.

# Requirements

* [MICROEJ SDK 6](https://docs.microej.com/en/latest/SDK6UserGuide/index.html).
* A [VEE Port](https://github.com/MicroEJ/?q=VEEPort&type=all&language=&sort=) that contains:
    * EDC-1.3 or higher
    * BON-1.4 or higher
    * NET-1.1 or higher
    * SSL-2.2 or higher

This example has been tested on:

* Android Studio with MicroEJ plugin for Android Studio 0.3.0.
* [STM32F7508-DK VEE Port 2.2.0.](https://github.com/MicroEJ/VEEPort-STMicroelectronics-STM32F7508-DK/tree/2.2.0)

# Usage

Follow [MICROEJ SDK 6 Installation Guide](https://docs.microej.com/en/latest/SDK6UserGuide/install.html) to setup the SDK.

By default, the sample will use the STM32F7508-DK VEE Port.

Refer to the [Select a VEE Port](https://docs.microej.com/en/latest/SDK6UserGuide/selectVeePort.html) documentation for more information.

This sample will connect to ``https://postman-echo.com`` by default.
To use another server, follow the ``Changing the Server`` section.

## Run on Simulator

In Android Studio:
- Open the Gradle tool window by clicking on the elephant icon on the right side,
- Expand the `Tasks` list,
- From the `Tasks` list, expand the `microej` list,
- Double-click on `runOnSimulator`,
- The application starts, the traces are visible in the Run view.

Alternative ways to run in simulation are described in the [Run on Simulator](https://docs.microej.com/en/latest/SDK6UserGuide/runOnSimulator.html) documentation.

## Run on Device

Make sure to properly setup the VEE Port environment before going further.
Refer to the VEE Port README for more information.

In Android Studio:
- Open the Gradle tool window by clicking on the elephant on the right side,
- Expand the `Tasks` list,
- From the `Tasks` list, expand the `microej` list,
- Double-Click on `runOnDevice`.
- The device is flashed. Use the appropriate tool to retrieve the execution traces.

Alternative ways to run on device are described in the [Run on Device](https://docs.microej.com/en/latest/SDK6UserGuide/runOnDevice.html) documentation.

## Changing the Server

To change the server:

1. Download the server's certificate (e.g. ``server.com.crt``).
2. Drop it into the folder [certificates/](src/main/resources/certificates).
3. In [com.microej.example.iot.ssl.rest.resources.list](src/main/resources/com/microej/example/iot/ssl/rest/com.microej.example.iot.ssl.rest.resources.list):
    * Change the existing path ``/certificates/httpbin.org.crt`` to the path of your certificate (e.g. ``/certificates/server.com.crt``).
4. In [Main.java](src/main/java/com/microej/example/iot/ssl/rest/Main.java):
    * Set the variable ``SERVER_CERT_FILENAME`` to your certificate name (e.g. ``server.com.crt``).
    * Set the variable ``SERVER_URL`` to your server URL.

# Dependencies

_All dependencies are retrieved transitively by Gradle._

# Source

N/A

# Restrictions

None.

---  
_Markdown_   
_Copyright 2019-2024 MicroEJ Corp. All rights reserved._   
_Use of this source code is governed by a BSD-style license that can be found with this software._  