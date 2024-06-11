# Overview

This example contains the Client code to show a mutual TLS authentication.

Refer to the [ssl-mutual-server](../ssl-mutual-server/) example to run the Server code.

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

## Configuration

The Server [ssl-mutual-server](../ssl-mutual-server/) is required to be started before running this sample.

Edit the ``HOST`` constant in [Main.java](src/main/java/com/microej/example/iot/ssl/mutual/Main.java) to set server IP address.

This sample is pre-configured with certificates.
To change the certificates, follow the ``Changing Certificates`` section.

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

## Changing Certificates

### Changing the Server Certificate

To change the server certificate:

1. Download server's certificate (e.g. ``server-crt.pem``).
2. Drop it into the folder [certificates/](src/main/resources/certificates).
3. In [com.microej.example.iot.ssl.mutual.resources.list](src/main/resources/com/microej/example/iot/ssl/mutual/com.microej.example.iot.ssl.mutual.resources.list):
    * Change the existing path ``/certificates/server-crt.pem`` to the path of your certificate (e.g. ``/certificates/server.com.crt``).
    * Remove the root certificate (if the client certificate is changed as well): ``/certificates/ca-crt.pem``.
    * Add any other certificates.
4. In [Main.java](src/main/java/com/microej/example/iot/ssl/mutual/Main.java):
    * Set the variable ``SERVER_CERT_FILENAME`` to your certificate names (e.g. ``[server.com.crt, root.com.crt]``).
    * Set the variable ``HOST`` to your server host.
    * Set the variable ``PORT`` to your server port.

### Changing the Client Certificate

To change the client certificate:

1. Get the client certificate and key.
2. Drop it into the folder [certificates/](src/main/resources/certificates).
3. In [com.microej.example.iot.ssl.mutual.resources.list](src/main/resources/com/microej/example/iot/ssl/mutual/com.microej.example.iot.ssl.mutual.resources.list):
    * Change the existing path ``/certificates/clientA-crt.pem`` to the path of your certificate (e.g. ``/certificates/client.pem``).
    * Change the existing path ``/certificates/clientA-key.der`` to the path of your key in DER format (e.g. ``/certificates/key.der``).
    * Remove the root certificate (if the client certificate is changed as well) ``/certificates/ca-crt.pem``.
4. In [Main.java](src/main/java/com/microej/example/iot/ssl/mutual/Main.java):
    * Set the variable ``CLIENT_CERT_CHAIN`` to your certificate chain, it must be ordered (e.g. ``[client.pem, root.com.crt"]``).
    * Set the variable ``DEVICE_KEY`` to your device key (e.g. ``/certificates/key.der``).
    * Set the variable ``KEY_PASSWORD`` to your key's password.

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