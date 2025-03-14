# Overview

This example shows how to subscribe to a MQTT broker over TLS.

The default broker used is: ``test.mosquitto.org``.

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

`./gradlew :ssl-mqtt-subscriber:runOnSimulator`

Alternative ways to run in simulation are described in the [Run on Simulator](https://docs.microej.com/en/latest/SDK6UserGuide/runOnSimulator.html) documentation.

## Run on Device

Complete the [Getting Started for NXP i.MX RT1170 Evaluation Kit](https://docs.microej.com/en/latest/SDK6UserGuide/gettingStartedIMXRT1170.html)
to make sure your environment is fully setup.

If you are using another VEE Port, make sure to properly setup the VEE Port environment
before going further. Refer to the dedicated VEE Port README or Getting Started for more information.

Run the following command in your IDE
(or click the ``Play`` button next to the line
below when opening this README in IntelliJ IDEA):

`./gradlew :ssl-mqtt-subscriber:runOnDevice`

Alternative ways to run on device are described in the [Run on Device](https://docs.microej.com/en/latest/SDK6UserGuide/runOnDevice.html) documentation.

## Expected Behavior

Once the application is connected to the internet,
the following traces should be observed in the console:

```
[subscriber] INFO: Network Lost
[subscriber] INFO: Time updated.
[subscriber] INFO: Network available
[subscriber] INFO: Try to connect to ssl://test.mosquitto.org:8883
[subscriber] INFO: Client connected using SSL
[subscriber] INFO: Client subscribed to microej
```

## Multi-Sandbox Considerations

In a Multi-Sandbox context, the network callback registered at the application startup should be unregistered when the application stops.

Find below an implementation example:
```java
	public static final Logger LOGGER = Logger.getLogger("[Subscriber]");
	private MqttClient client;
	private Thread thread;
	private boolean subscribe;
	private SimpleNetworkCallbackAdapter simpleNetworkCallbackAdapter;

	/**
	 * Stops the publishing and unregister the network state listener.
	 */
	@Override
	public synchronized void stop() {
		subscribe = false;
		disconnect();
		ConnectivityManager connectivityManager = ServiceFactory.getService(ConnectivityManager.class);
		if (connectivityManager != null && simpleNetworkCallbackAdapter != null) {
			connectivityManager.unregisterNetworkCallback(simpleNetworkCallbackAdapter);
		}
		simpleNetworkCallbackAdapter = null;
	}

	private synchronized void disconnect() {
		MqttClient client = this.client;
		this.client = null;
		if (client != null) {
			try {
				client.disconnect();
				LOGGER.info("Client disconnected");
			} catch (MqttException e) {
				LOGGER.fine(e.getMessage());
			}
		}
	}
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