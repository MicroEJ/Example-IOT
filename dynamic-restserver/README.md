# Overview

This example shows how to setup a Rest Server.

This server provides a simple HTML page which shows some simulated information about the device (uptime, java heap usage, thread).

# Requirements

* [MICROEJ SDK 6](https://docs.microej.com/en/latest/SDK6UserGuide/index.html).
* A [VEE Port](https://github.com/MicroEJ/?q=VEEPort&type=all&language=&sort=) that contains:
    * EDC-1.3 or higher
    * BON-1.4 or higher
    * NET-1.1 or higher

This example has been tested on:

* Android Studio with MicroEJ plugin for Android Studio 0.3.0.
* [STM32F7508-DK VEE Port 2.2.0.](https://github.com/MicroEJ/VEEPort-STMicroelectronics-STM32F7508-DK/tree/2.2.0)

# Usage

Follow [MICROEJ SDK 6 Installation Guide](https://docs.microej.com/en/latest/SDK6UserGuide/install.html) to setup the SDK.

By default, the sample will use the STM32F7508-DK VEE Port.

Refer to the [Select a VEE Port](https://docs.microej.com/en/latest/SDK6UserGuide/selectVeePort.html) documentation for more information.

## Run on Simulator

In Android Studio:
- Open the Gradle tool window by clicking on the elephant icon on the right side,
- Expand the `Tasks` list,
- From the `Tasks` list, expand the `microej` list,
- Double-click on `runOnSimulator`,
- The application starts, the traces are visible in the Run view.
- The server is accessible through a web browser using the url http://localhost:8080.

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
- The server is accessible through a web browser using device's local ip address: http://**board-ip**:8080.


Alternative ways to run on device are described in the [Run on Device](https://docs.microej.com/en/feature-microej-sdk-6/SDK6UserGuide/runOnDevice.html) documentation.

## Expected output

Once the REST Server is running:
- If you are running the application in simulation you should be able to access it through a web browser using the url http://localhost:8080.
- If you are running the application on a board you should be able to access it through a web browser using the board's local ip address: http://**board-ip**:8080.
  You should get on a simple html page which shows some simulated information about the board (uptime, java heap usage, thread).

## Going further

The source code is provided as is for educative purpose, to reduce the embedded constraint, it is recommended to:
- Minify then compress the resources with gz and use a GzipResourceEndpoint.
- Unify the resources in a single HTML page to avoid 3 connections.

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