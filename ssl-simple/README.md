# Overview

This example demonstrates a simple TLS handshake with [example.org](https://example.org/).

# Requirements

* [MICROEJ SDK 6](https://docs.microej.com/en/latest/SDK6UserGuide/index.html).
* A [VEE Port](https://github.com/MicroEJ/?q=VEEPort&type=all&language=&sort=) that contains:
    * EDC-1.3 or higher
    * BON-1.4 or higher
    * NET-1.1 or higher
    * SSL-2.2 or higher

This example has been tested on:

- IntelliJ IDEA with MicroEJ plugin for IntelliJ IDEA ``1.1.0``.
- [STM32F7508-DK VEE Port 2.3.0](https://github.com/MicroEJ/VEEPort-STMicroelectronics-STM32F7508-DK/tree/2.3.0).

# Usage

Follow [MICROEJ SDK 6 Installation Guide](https://docs.microej.com/en/latest/SDK6UserGuide/install.html) to set up the SDK.

By default, the sample will use the
[STM32F7508-DK VEE Port 2.3.0](https://github.com/MicroEJ/VEEPort-STMicroelectronics-STM32F7508-DK/tree/2.3.0).
The sample retrieves the VEE Port as a [module](https://docs.microej.com/en/latest/SDK6UserGuide/selectVeePort.html#using-a-module-dependency).

Refer to the [Select a VEE Port](https://docs.microej.com/en/latest/SDK6UserGuide/selectVeePort.html)
documentation to use another VEE Port in your project.

## Configuration

This sample connects to [example.org](https://example.org/) to establish a secure connection.
No local server setup is required.
To change the certificates, follow the ``Changing Certificates`` section.

## Run on Simulator

Run the following command in your IDE
(or click the ``Play`` button next to the line
below when opening this README in IntelliJ IDEA):

`./gradlew :ssl-simple:runOnSimulator`

Alternative ways to run in simulation are described in the [Run on Simulator](https://docs.microej.com/en/latest/SDK6UserGuide/runOnSimulator.html) documentation.

## Run on Device

Complete the [Getting Started for STM32F7508-DK Evaluation Kit](https://docs.microej.com/en/latest/SDK6UserGuide/gettingStartedSTM32F7508.html)
to make sure your environment is fully setup.

If you are using another VEE Port, make sure to properly setup the VEE Port environment
before going further. Refer to the dedicated VEE Port README or Getting Started for more information.

Run the following command in your IDE
(or click the ``Play`` button next to the line
below when opening this README in IntelliJ IDEA):

`./gradlew :ssl-simple:runOnDevice`

Alternative ways to run on device are described in the [Run on Device](https://docs.microej.com/en/latest/SDK6UserGuide/runOnDevice.html) documentation.

## Expected Behavior

Once the application is connected to the internet,
the following traces should be observed in the console:

```
tls example INFO: =========== Waiting for connectivity ===========
tls example INFO: Connected.
tls example INFO: =========== Updating time ===========
SntpClient:F=0 16 -720
tls example INFO: Time updated.
tls example INFO: =========== REQUEST ===========
tls example INFO: Start TLS handshake.
tls example INFO: Handshake successfully done.
tls example INFO: Connection timeout, received: 
tls example INFO: =========== Stopping connectivity Manager ===========
```

**Note:** The ```SntpClient:F=0 ...``` indicates the SNTP client time offset correction applied during synchronization with the NTP server.

## Certificates

### Using pre-configured Server Certificate 
This sample is pre-configured with the required certificates.  
There is no need to modify or replace any certificate files — the example works directly with `example.org`.

### Changing the Server Certificate

To change the server certificate:

1. Download server's certificate (e.g. ``server-crt.pem``).
2. Drop it into the folder [certificates/](src/main/resources/certificates).
3. In [com.microej.example.iot.ssl.simple.resources.list](src/main/resources/com/microej/example/iot/ssl/simple/com.microej.example.iot.ssl.simple.resources.list):
	* Change the existing path ``/certificates/server-crt.pem`` to the path of your certificate (e.g. ``/certificates/server.com.crt``).
	* Remove the root certificate (if the client certificate is changed as well): ``/certificates/ca-crt.pem``.
	* Add any other certificates.
4. In [SimpleTLSHandshake.java](src/main/java/com/microej/example/iot/ssl/SimpleTLSHandshake.java):
	* Set the variable ``SERVER_CERT_FILENAME`` to your certificate names (e.g. ``[server.com.crt, root.com.crt]``).
	* Set the variable ``HOST`` to your server host.
	* Set the variable ``PORT`` to your server port.


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
_Copyright 2025 MicroEJ Corp. All rights reserved._   
_Use of this source code is governed by a BSD-style license that can be found with this software._  