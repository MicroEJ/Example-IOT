<!--
	Markdown
	
	Copyright 2016 IS2T. All rights reserved.
	Modification and distribution is permitted under certain conditions.
	IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
-->
# Overview
A MicroEJ sandboxed application subscribing to a standard https MQTT broker.

# Usage
## Change the SSL Broker server 
To change the ssl broker server:
1. update the url at [HelloWorldConstants.java](ej.examples.iot.ssl.mqtt.subscriber/src/main/java/ej/examples/iot/mqtt/HelloWorldConstants.java)
1. Download the broker server's certificate (eg broker.com.crt).
1. Drop it into the folder [certificates](j.examples.iot.ssl.mqtt.subscriber/src/main/resources/certificates)
1. Add a new line in [mqtt.resources.list](ej.examples.iot.ssl.mqtt.subscriber/src/main/resources/ej/examples/iot/mqtt/mqtt.resources.list) with the path to the certificate (eg certificates/broker.com.crt)
1. Add a new line in [paho.certificates.list](ej.examples.iot.ssl.mqtt.subscriber/src/main/resources/certificates/paho.certificates.list) with the path to the certificate (eg /certificates/broker.com.crt)

## Run on MicroEJ Simulator
1. Right Click on the project
1. Select **Run as -> MicroEJ Application**
1. Select **BackgroundServicesStandalone**
1. Select your platform 
1. Press **Ok**

## Run on a device
### Local deploy
1. Right Click on [SubscriberEntryPoint.java](/ej.examples.iot.ssl.mqtt.subscriber/src/.generated~/.java/__MQTTSSLSubscriber__/generated/MQTTSSLSubscriberEntryPoint.java)
1. Select **Run as -> Run Configuration** 
1. Select **MicroEJ Application** configuration kind
1. Click on **New launch configuration** icon
1. In **Execution** tab
	1. In **Target** frame, in **Platform** field, select a relevant platform
	1. In **Execution** frame
		1. Select **Execute on Device**
		2. In **Settings** field, select **Build & Deploy**
1. In **Configuration** tab
	1. In **Board** frame
		1. Set **Host** field to your board IP address
1. Press **Apply**
1. Press **Run**

# Requirements
* MicroEJ Studio or SDK 4.0 or later
* A platform with at least:
	* EDC-1.2 or higher
	* NET-1.0 or higher
	* BON-1.0 or higher
	* SSL-2.0 or higher
	* KF-1.4 or higher

## Dependencies
_All dependencies are retrieved transitively by Ivy resolver_.