/*
 * Java
 *
 * Copyright 2015-2019 MicroEJ Corp. All rights reserved.
 * For demonstration purpose only.
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
	public static String BROKER = "tcp://test.mosquitto.org:1883"; //$NON-NLS-1$

	/**
	 * Id to use as a client.
	 */
	public static String SUBSCRIBER_ID = "subscriber_" + new Random().nextInt(); //$NON-NLS-1$

	/**
	 * Topic to subscribe to..
	 */
	public static String TOPIC = "microej"; //$NON-NLS-1$
}