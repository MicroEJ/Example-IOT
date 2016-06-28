<!--
	Markdown
	
	Copyright 2016 IS2T. All rights reserved.
	Modification and distribution is permitted under certain conditions.
	IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
-->
# Overview
A MicroEJ sandboxed application subscribing to a standard http MQTT broker.

# Usage
## Run on MicroEJ Simulator
1. Right Click on the project
1. Select **Run as -> MicroEJ Application**
1. Select **BackgroundServicesStandalone**
1. Select your platform 
1. Press **Ok**

## Run on device
### Local deploy
1. Right Click on [SubscriberEntryPoint.java](/ej.examples.iot.mqtt.subscriber/src/.generated~/.java/__MQTTSubscriber__/generated/MQTTSubscriberEntryPoint.java)
1. Select **Run as -> Run Configurations...** 
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

## Dependencies
_All dependencies are retrieved transitively by Ivy resolver_.