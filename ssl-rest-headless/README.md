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

- IntelliJ IDEA with MicroEJ plugin for IntelliJ IDEA ``1.1.0``.
- [NXP i.MX RT1170 VEE Port 3.0.0](https://github.com/MicroEJ/nxp-vee-imxrt1170-evk/tree/NXPVEE-MIMXRT1170-EVK-3.0.0).

# Usage

Follow [MICROEJ SDK 6 Installation Guide](https://docs.microej.com/en/latest/SDK6UserGuide/install.html) to setup the SDK.

By default, the sample will use the
[NXP i.MX RT1170 VEE Port 3.0.0](https://github.com/MicroEJ/nxp-vee-imxrt1170-evk/tree/NXPVEE-MIMXRT1170-EVK-3.0.0).
The sample retrieves the VEE Port as a [module](https://docs.microej.com/en/latest/SDK6UserGuide/selectVeePort.html#using-a-module-dependency).

Refer to the [Select a VEE Port](https://docs.microej.com/en/latest/SDK6UserGuide/selectVeePort.html)
documentation to use another VEE Port in your project.

## Run on Simulator

Run the following command in your IDE
(or click the ``Play`` button next to the line
below when opening this README in IntelliJ IDEA):

`./gradlew :ssl-rest-headless:runOnSimulator`

Alternative ways to run in simulation are described in the [Run on Simulator](https://docs.microej.com/en/latest/SDK6UserGuide/runOnSimulator.html) documentation.

## Run on Device

Complete the [Getting Started for NXP i.MX RT1170 Evaluation Kit](https://docs.microej.com/en/latest/SDK6UserGuide/gettingStartedIMXRT1170.html)
to make sure your environment is fully setup.

If you are using another VEE Port, make sure to properly setup the VEE Port environment
before going further. Refer to the dedicated VEE Port README or Getting Started for more information.

Run the following command in your IDE
(or click the ``Play`` button next to the line
below when opening this README in IntelliJ IDEA):

`./gradlew :ssl-rest-headless:runOnDevice`

Alternative ways to run on device are described in the [Run on Device](https://docs.microej.com/en/latest/SDK6UserGuide/runOnDevice.html) documentation.

## Expected Behavior

Once the application is connected to the internet,
the following traces should be observed in the console:

```
https example INFO: =========== Waiting for connectivity ===========
https example INFO: Connected
https example INFO: =========== Updating time ===========
SntpClient:F=0 20 1740585259864
https example INFO: Time updated
https example INFO: =========== GET REQUEST ===========
https example INFO: {"headers":{"x-request-start":"t1740585265.029","connection":"close","user-agent":"Resty/0.1 (Java)","x-amzn-trace-id":"Root=1-67bf3931-7fcd15270123f02c6d9b8df1","accept":"application/json","host":"postman-echo.com","x-forwarded-proto":"https","x-forwarded-port":"443"},"args":{},"url":"https://postman-echo.com/get"}
https example INFO: user-agent: Resty/0.1 (Java)
host: postman-echo.com

https example INFO: =========== POST REQUEST ===========
https example INFO: {"form":{},"headers":{"x-request-start":"t1740585265.715","connection":"close","user-agent":"Resty/0.1 (Java)","x-amzn-trace-id":"Root=1-67bf3931-5eb32d5c4ee640a857af13db","content-length":"20","accept":"application/json","host":"postman-echo.com","x-forwarded-proto":"https","content-type":"text/plain; charset=UTF-8","x-forwarded-port":"443"},"files":{},"json":null,"data":"My POST request data","args":{},"url":"https://postman-echo.com/post"}
https example INFO: Data sent for POST request: My POST request data
https example INFO: =========== PUT REQUEST ===========
https example INFO: {"form":{},"headers":{"x-request-start":"t1740585267.191","connection":"close","user-agent":"Resty/0.1 (Java)","x-amzn-trace-id":"Root=1-67bf3933-3215ec702157e4e51e2dd15e","content-length":"19","accept":"application/json","host":"postman-echo.com","x-forwarded-proto":"https","content-type":"text/plain; charset=UTF-8","x-forwarded-port":"443"},"files":{},"json":null,"data":"My PUT request data","args":{},"url":"https://postman-echo.com/put"}
https example INFO: Data sent for PUT request: My PUT request data
https example INFO: =========== DELETE REQUEST ===========
https example INFO: {"form":{},"headers":{"x-request-start":"t1740585268.602","connection":"close","user-agent":"Resty/0.1 (Java)","x-amzn-trace-id":"Root=1-67bf3934-094577cb516ce1fa54959065","accept":"application/json","host":"postman-echo.com","x-forwarded-proto":"https","content-type":"application/json","x-forwarded-port":"443"},"files":{},"json":null,"data":{},"args":{},"url":"https://postman-echo.com/delete"}
https example INFO: =========== Stopping connectivity Manager ===========
```

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