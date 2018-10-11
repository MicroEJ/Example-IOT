/*
 * Java
 *
 * Copyright 2015-2016 IS2T. All rights reserved.
 * Use of this source code is subject to license terms..
 */
package com.microej.example.iot.mqtt;

import java.util.Random;

public interface HelloWorldConstants {

	String BROKER = "ssl://test.mosquitto.org:8883";
	// Set a random id.
	String PUBLISHER_ID = "publisher_" + new Random().nextInt(); //$NON-NLS-1$
	String HELLO_WORLD_MESSAGE = "Hello World !!"; //$NON-NLS-1$
	String TOPIC = "microej"; //$NON-NLS-1$
}
