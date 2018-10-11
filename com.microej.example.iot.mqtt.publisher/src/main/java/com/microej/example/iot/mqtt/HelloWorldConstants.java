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

	String BROKER = "tcp://test.mosquitto.org:1883"; //$NON-NLS-1$
	// Set a random id.
	String PUBLISHER_ID = "publisher_" + new Random().nextInt(); //$NON-NLS-1$
	String HELLO_WORLD_MESSAGE = "Hello World !!"; //$NON-NLS-1$
	String TOPIC = "microej"; //$NON-NLS-1$
}
