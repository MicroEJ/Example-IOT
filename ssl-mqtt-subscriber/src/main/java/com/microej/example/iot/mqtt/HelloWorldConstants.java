/*
 * Java
 *
 * Copyright 2015-2023 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
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
	public static final String BROKER = "ssl://test.mosquitto.org:8883"; //$NON-NLS-1$

	/**
	 * Id to use as a client.
	 */
	public static final String SUBSCRIBER_ID = "subscriber_" + new Random().nextInt(); //$NON-NLS-1$

	/**
	 * Topic to send the message to.
	 */
	public static final String TOPIC = "microej"; //$NON-NLS-1$
}
