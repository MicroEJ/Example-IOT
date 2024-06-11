# Overview

This example shows how to publish data to a MQTT broker over TLS.

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

## MQTT Broker Configuration

A running MQTT broker is required in order to run this example.
You shall ensure that the broker is reachable from your development machine (if running the sample on Simulator) or from your target device.

By default, the sample will use the following broker: ``test.mosquitto.org``.

To use another broker:
1. Update the MQTT URL in [HelloWorldConstants.java](src/main/java/com/microej/example/iot/mqtt/HelloWorldConstants.java).
2. Download the broker server's certificate (e.g. ``broker.com.crt``).
3. Drop it into the folder [certificates/](src/main/resources/certificates).
4. Add a new line in [mqtt.resources.list](src/main/resources/com/microej/example/iot/mqtt/mqtt.resources.list) with the path to the certificate (e.g. ``certificates/broker.com.crt``).
5. Add a new line in [paho.certificates.list](src/main/resources/certificates/paho.certificates.list) with the path to the certificate (e.g. ``/certificates/broker.com.crt``).

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

## Multi-Sandbox Considerations

In a Multi-Sandbox context, the network callback registered at the application startup should be unregistered when the application stops.

Find below an implementation example:
```java
	private boolean sendMessage = false;
	private Thread thread;
	private SimpleNetworkCallbackAdapter simpleNetworkCallbackAdapter;

	/**
	 * Stops the publishing and unregister the network state listener.
	 */
	public synchronized void stop() {
		stopSending();
		ConnectivityManager connectivityManager = ServiceFactory.getService(ConnectivityManager.class);
		if (connectivityManager != null && simpleNetworkCallbackAdapter != null) {
			connectivityManager.unregisterNetworkCallback(simpleNetworkCallbackAdapter);
		}
		simpleNetworkCallbackAdapter = null;
	}

	private synchronized void stopSending() {
		sendMessage = false;
		Thread thread = this.thread;
		this.thread = null;
		if (thread != null) {
			thread.interrupt();
		}
	}
```

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