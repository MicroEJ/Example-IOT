/*
 * Java
 *
 * Copyright 2016-2019 MicroEJ Corp. All rights reserved.
 * For demonstration purpose only.
 * MicroEJ Corp. PROPRIETARY. Use is subject to license terms.
 */
package com.microej.example.iot.mqtt;

import java.util.logging.Level;

/**
 * This example connects to a MQTT broker, creates a callback and subscribes to the topic "MqttHelloWorld".
 */
public final class Main {


	public static void main(String[] args) {
		// Display all logs
		SubscriberBackgroundService.LOGGER.setLevel(Level.ALL);

		new SubscriberBackgroundService().onStart();
	}



}
