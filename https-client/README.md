# Overview

This example shows how to establish an HTTPS connection. The following requests are demonstrated:

- GET with query parameters
- GET with path parameters
- POST with url encoded parameters
- POST with form data
- POST with RAW JSON
- POST with binary data

The test serer used is https://postman-echo.com.

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

`./gradlew :https-client:runOnSimulator`

Alternative ways to run in simulation are described in the [Run on Simulator](https://docs.microej.com/en/latest/SDK6UserGuide/runOnSimulator.html) documentation.

## Run on Device

Complete the [Getting Started for NXP i.MX RT1170 Evaluation Kit](https://docs.microej.com/en/latest/SDK6UserGuide/gettingStartedIMXRT1170.html)
to make sure your environment is fully setup.

If you are using another VEE Port, make sure to properly setup the VEE Port environment
before going further. Refer to the dedicated VEE Port README or Getting Started for more information.

Run the following command in your IDE
(or click the ``Play`` button next to the line
below when opening this README in IntelliJ IDEA):

`./gradlew :https-client:runOnDevice`

Alternative ways to run on device are described in the [Run on Device](https://docs.microej.com/en/latest/SDK6UserGuide/runOnDevice.html) documentation.

## Expected Behavior

Once the application is connected to the internet,
the following traces should be observed in the console:

```
https example INFO: =========== Waiting for connectivity ===========
https example INFO: Connected
https example INFO: =========== Updating time ===========
https example INFO: Time updated
https example INFO: =========== GET REQUEST WITH QUERY PARAMETERS ===========
https example INFO: {
  "args": {
    "param1": "val1",
    "param2": "val2"
  },
  "headers": {
    "host": "postman-echo.com",
    "x-request-start": "t1740581707.994",
    "connection": "close",
    "x-forwarded-proto": "https",
    "x-forwarded-port": "443",
    "x-amzn-trace-id": "Root=1-67bf2b4b-3ea5bc9d63af4c017fea1018",
    "user-agent": "Resty/0.1 (Java)",
    "accept": "text/html,text/plain,text/*"
  },
  "url": "https://postman-echo.com/get?param1=val1&param2=val2"
}
https example INFO: =========== GET REQUEST WITH PATH PARAMETERS ===========
https example INFO: {
  "delay": "2"
}
https example INFO: =========== POST REQUEST WITH URL ENCODED PARAMETERS ===========
https example INFO: {
  "args": {},
  "data": "formParam1%3Dvalue1%26formparam2%3Dvalue2",
  "files": {},
  "form": {},
  "headers": {
    "host": "postman-echo.com",
    "x-request-start": "t1740581711.118",
    "connection": "close",
    "content-length": "41",
    "x-forwarded-proto": "https",
    "x-forwarded-port": "443",
    "x-amzn-trace-id": "Root=1-67bf2b4e-064eb1f70ee880045bb69b13",
    "user-agent": "Resty/0.1 (Java)",
    "accept": "text/html,text/plain,text/*",
    "content-type": "text/plain; charset=UTF-8"
  },
  "json": null,
  "url": "https://postman-echo.com/post"
}
https example INFO: =========== POST REQUEST WITH FORM DATA  ===========
https example INFO: {
  "args": {},
  "data": {},
  "files": {},
  "form": {
    "form-data-content": "I am the content that is being sent."
  },
  "headers": {
    "host": "postman-echo.com",
    "x-request-start": "t1740581712.886",
    "connection": "close",
    "content-length": "244",
    "x-forwarded-proto": "https",
    "x-forwarded-port": "443",
    "x-amzn-trace-id": "Root=1-67bf2b50-0521a80553f570737608baf7",
    "user-agent": "Resty/0.1 (Java)",
    "accept": "text/html,text/plain,text/*",
    "content-type": "multipart/form-data; boundary=jb3d630cd3-fd5d-42a2-b51e-4c510b16a4b1"
  },
  "json": null,
  "url": "https://postman-echo.com/post"
}
https example INFO: =========== POST REQUEST WITH RAW JSON  ===========
https example INFO: {
  "args": {},
  "data": {
    "greetings": [
      "Hi There!"
    ]
  },
  "files": {},
  "form": {},
  "headers": {
    "host": "postman-echo.com",
    "x-request-start": "t1740581714.554",
    "connection": "close",
    "content-length": "27",
    "x-forwarded-proto": "https",
    "x-forwarded-port": "443",
    "x-amzn-trace-id": "Root=1-67bf2b52-7b48203a692fb68b3ebb8d65",
    "user-agent": "Resty/0.1 (Java)",
    "accept": "text/html,text/plain,text/*",
    "content-type": "application/json; charset=UTF-8"
  },
  "json": {
    "greetings": [
      "Hi There!"
    ]
  },
  "url": "https://postman-echo.com/post"
}
https example INFO: =========== POST REQUEST WITH BINARY DATA  ===========
https example INFO: {
  "args": {},
  "data": {
    "type": "Buffer",
    "data": [
      72,
      105,
      32,
      116,
      104,
      101,
      114,
      101,
      33
    ]
  },
  "files": {},
  "form": {},
  "headers": {
    "host": "postman-echo.com",
    "x-request-start": "t1740581715.150",
    "connection": "close",
    "content-length": "9",
    "x-forwarded-proto": "https",
    "x-forwarded-port": "443",
    "x-amzn-trace-id": "Root=1-67bf2b53-7db5508f09e46d244dba0ed8",
    "user-agent": "Resty/0.1 (Java)",
    "accept": "text/html,text/plain,text/*",
    "content-type": "application/octet-stream"
  },
  "json": {
    "type": "Buffer",
    "data": [
      72,
      105,
      32,
      116,
      104,
      101,
      114,
      101,
      33
    ]
  },
  "url": "https://postman-echo.com/post"
}
https example INFO: =========== Stopping connectivity Manager ===========
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