# Overview
A MicroEJ sandboxed application subscribing to a regular MQTT broker.

# Usage
## MQTT broker setup
A running MQTT broker is required in order to run this example. You shall ensure that the broker is reachable from your development machine (if running the sample on MicroEJ simulator) or from your target device
1. Update the MQTT URL in [HelloWorldConstants.java](src/main/java/com/microej/example/iot/mqtt/HelloWorldConstants.java)

## Run on MicroEJ Simulator
1. Right Click on the project
2. Select **Run as -> MicroEJ Application**
3. Select **BackgroundServicesStandalone**
4. Select your platform 
5. Press **Ok**

## Run on device
### Local deploy
1. Right Click on [SubscriberEntryPoint.java](src/.generated~/.java/__MQTTSubscriber__/generated/MQTTSubscriberEntryPoint.java)
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
* MicroEJ Studio or SDK 4.0 or later
* A platform with at least:
	* EDC-1.2 or higher
	* NET-1.0 or higher

## Dependencies
_All dependencies are retrieved transitively by Ivy resolver_.