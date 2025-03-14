# Overview

This example shows how to use WebSockets connecting to a WebSocket server over TLS.

# Requirements

* [MICROEJ SDK 6](https://docs.microej.com/en/latest/SDK6UserGuide/index.html).
* A [VEE Port](https://github.com/MicroEJ/?q=VEEPort&type=all&language=&sort=) that contains:
    * EDC-1.3 or higher
    * BON-1.4 or higher
    * NET-1.1 or higher
    * SSL-2.2 or higher

This example has been tested on:

- [STM32F7508-DK VEE Port 2.3.0](https://github.com/MicroEJ/VEEPort-STMicroelectronics-STM32F7508-DK/tree/2.3.0).
- IntelliJ IDEA with MicroEJ plugin for IntelliJ IDEA ``1.1.0``.

# Usage

Follow [MICROEJ SDK 6 Installation Guide](https://docs.microej.com/en/latest/SDK6UserGuide/install.html) to setup the SDK.

By default, the sample will use the
[STM32F7508-DK VEE Port 2.3.0](https://github.com/MicroEJ/VEEPort-STMicroelectronics-STM32F7508-DK/tree/2.3.0).
The sample retrieves the VEE Port as a [module](https://docs.microej.com/en/latest/SDK6UserGuide/selectVeePort.html#using-a-module-dependency).

Refer to the [Select a VEE Port](https://docs.microej.com/en/latest/SDK6UserGuide/selectVeePort.html)
documentation to use another VEE Port in your project.

## Run on Simulator

Run the following command in your IDE
(or click the ``Play`` button next to the line
below when opening this README in IntelliJ IDEA):

`./gradlew :ssl-websocket:runOnSimulator`

Alternative ways to run in simulation are described in the [Run on Simulator](https://docs.microej.com/en/latest/SDK6UserGuide/runOnSimulator.html) documentation.

## Run on Device

Complete the [Getting Started for STM32F7508-DK Evaluation Kit](https://docs.microej.com/en/latest/SDK6UserGuide/gettingStartedSTM32F7508.html)
to make sure your environment is fully setup.

If you are using another VEE Port, make sure to properly setup the VEE Port environment
before going further. Refer to the dedicated VEE Port README or Getting Started for more information.

Run the following command in your IDE
(or click the ``Play`` button next to the line
below when opening this README in IntelliJ IDEA):

`./gradlew :ssl-websocket:runOnDevice`

Alternative ways to run on device are described in the [Run on Device](https://docs.microej.com/en/latest/SDK6UserGuide/runOnDevice.html) documentation.

## Expected Behavior

Once the application is connected to the internet,
the following traces should be observed in the console:

```
websocket INFO: =========== Waiting for connectivity ===========
websocket INFO: Connected
websocket INFO: =========== Updating time ===========
SntpClient:F=0 20 174
websocket INFO: Time updated
websocket INFO: =========== Initialize SSLContext ===========
websocket INFO: Initialization done
websocket INFO: =========== Secure Websocket USAGE ===========
websocket INFO: onOpen
websocket INFO: Message received: Request served by 6e82931b755587
websocket INFO: Sending message Hello World!
websocket INFO: Message received: Hello World!
websocket INFO: Answering with Goodbye World!
websocket INFO: Message received: Goodbye World!
websocket INFO: Sending binary message length 12
websocket INFO: Binary message receive length = 12
websocket INFO: Answering with binary message length 14
websocket INFO: Binary message receive length = 14
websocket INFO: =========== Stopping connectivity Manager ===========
websocket INFO: Close: (1000)
```
# Dependencies

The dependencies defined in [build.gradle.kts](build.gradle.kts)
are configured in [libs.versions.toml](../gradle/libs.versions.toml).

_All dependencies are retrieved transitively by Gradle._

# Source

N/A

# Restrictions

None.

---  
_Markdown_   
_Copyright 2019-2025 MicroEJ Corp. All rights reserved._   
_Use of this source code is governed by a BSD-style license that can be found with this software._  