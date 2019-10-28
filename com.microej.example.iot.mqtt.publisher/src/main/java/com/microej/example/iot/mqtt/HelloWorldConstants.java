/*
 * Java
 *
 * Copyright 2015-2019 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 * MicroEJ Corp. PROPRIETARY. Use is subject to license terms.
 */
package com.microej.example.iot.mqtt;

import java.util.Random;

/**
 * Constants used for configuring the demo.
 */
public final class HelloWorldConstants {

	/**
	 * URL of the mqtt borker.
	 */
	public static final String BROKER = "tcp://test.mosquitto.org:1883"; //$NON-NLS-1$

	/**
	 * Id to use as a client.
	 */
	public static final String PUBLISHER_ID = "publisher_" + new Random().nextInt(); //$NON-NLS-1$

	/**
	 * Message to send.
	 */
	public static final String HELLO_WORLD_MESSAGE = "Hello World !!"; //$NON-NLS-1$

	/**
	 * Topic to send the message to.
	 */
	public static final String TOPIC = "microej"; //$NON-NLS-1$
}
