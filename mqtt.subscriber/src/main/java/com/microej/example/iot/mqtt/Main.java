/*
 * Java
 *
 * Copyright 2016-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.iot.mqtt;

import java.util.logging.Level;

/**
 * This example connects to a MQTT broker, creates a callback and subscribes to the topic "MqttHelloWorld".
 */
public final class Main {


	public static void main(String[] args) {
		// Display all logs
		SubscriberEntryPoint.LOGGER.setLevel(Level.ALL);

		new SubscriberEntryPoint().start();
	}



}
