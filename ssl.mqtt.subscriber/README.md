# Overview

A MicroEJ sandboxed application subscribing to a MQTT broker via SSL.

# Usage

## MQTT broker setup

A running MQTT broker is required in order to run this example. You shall ensure that the broker is reachable from your development machine (if running the sample on MicroEJ simulator) or from your target device
1. Update the MQTT URL in [HelloWorldConstants.java](src/main/java/com/microej/example/iot/mqtt/HelloWorldConstants.java)
2. Download the broker server's certificate (eg broker.com.crt).
3. Drop it into the folder [certificates](src/main/resources/certificates)
4. Add a new line in [mqtt.resources.list](src/main/resources/com/microej/example/iot/mqtt/mqtt.resources.list) with the path to the certificate (eg certificates/broker.com.crt)
5. Add a new line in [paho.certificates.list](src/main/resources/certificates/paho.certificates.list) with the path to the certificate (eg /certificates/broker.com.crt)

## Run on MicroEJ Simulator

1. Right Click on the project
2. Select **Run as -> MicroEJ Application**
3. Select **BackgroundServicesStandalone**
4. Select your platform 
5. Press **Ok**

## Run on device

### Local deploy

1. Right Click on the project
2. Select **Run as -> Run Configurations...** 
3. Select **MicroEJ Application** configuration kind
4. Click on **New launch configuration** icon
5. In **Execution** tab
	1. In **Target** frame, in **Platform** field, select a relevant platform
	2. In **Execution** frame
		1. Select **Execute on Device**
		2. In **Settings** field, select **Build & Deploy**
6. In **Configuration** tab
	1. In **Board** frame
		1. Set **Host** field to your board IP address
7. Press **Apply**
8. Press **Run**

# Requirements

This example has been tested on:

* MicroEJ SDK 5.1
* With a platform that contains:
    * EDC-1.2 or higher
    * BON-1.2 or higher
    * NET-1.1 or higher
	* SSL-2.0 or higher
	* KF-1.5 or higher

## Dependencies

_All dependencies are retrieved transitively by MicroEJ Module Manager_.


---
_Copyright 2016-2022 MicroEJ Corp. All rights reserved._  
_Use of this source code is governed by a BSD-style license that can be found with this software._