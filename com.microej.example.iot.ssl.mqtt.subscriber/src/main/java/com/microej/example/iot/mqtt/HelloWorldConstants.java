/*
 * Java
 *
 * Copyright 2015-2018 IS2T. All rights reserved.
 * For demonstration purpose only.
 * IS2T PROPRIETARY. Use is subject to license terms.
 */
package com.microej.example.iot.mqtt;

import java.util.Random;

public interface HelloWorldConstants {

	String BROKER = "ssl://test.mosquitto.org:8883";
	// Set a random id.
	String SUBSCRIBER_ID = "subscriber_" + new Random().nextInt(); //$NON-NLS-1$
	String TOPIC = "microej"; //$NON-NLS-1$
}
