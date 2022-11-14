/*
 * Java
 *
 * Copyright 2016-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.iot.mqtt;

import java.util.logging.Level;

/**
 * This example connects to a MQTT broker, publishes the "Hello World!" message
 * to the topic "MqttHelloWorld" and disconnects from the MQTT broker.
 */
public final class Main {


	public static void main(String[] args) {
		// Display all logs
		PublisherEntryPoint.LOGGER.setLevel(Level.ALL);

		new PublisherEntryPoint().start();
	}

}
