/*
 * Java
 *
 * Copyright 2015-2019 MicroEJ Corp. All rights reserved.
 * For demonstration purpose only.
 * MicroEJ Corp. PROPRIETARY. Use is subject to license terms.
 */
package com.microej.example.iot.mqtt;

import java.util.Random;

public interface HelloWorldConstants {

	String BROKER = "ssl://test.mosquitto.org:8883";
	// Set a random id.
	String SUBSCRIBER_ID = "subscriber_" + new Random().nextInt(); //$NON-NLS-1$
	String TOPIC = "microej"; //$NON-NLS-1$
}
