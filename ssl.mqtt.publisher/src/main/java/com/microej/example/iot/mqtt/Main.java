/*
 * Java
 *
 * Copyright 2016-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.iot.mqtt;

import java.io.IOException;
import java.util.logging.Level;

import android.net.ConnectivityManager;
import ej.net.util.NtpUtil;
import ej.net.util.connectivity.ConnectivityUtil;
import ej.net.util.connectivity.SimpleNetworkCallbackAdapter;
import ej.service.ServiceFactory;

/**
 * This example connects to a MQTT broker, publishes the "Hello World!" message
 * to the topic "MqttHelloWorld" and disconnects from the MQTT broker.
 */
public final class Main {


	public static void main(String[] args) {
		// Display all logs
		PublisherEntryPoint.LOGGER.setLevel(Level.ALL);

		ConnectivityManager connectivityManager = ServiceFactory.getService(ConnectivityManager.class);
		if (connectivityManager != null) {
			ConnectivityUtil.registerAndCall(connectivityManager, new SimpleNetworkCallbackAdapter() {

				@Override
				public void onConnectivity(boolean isConnected) {
					if(isConnected) {
						try {
							NtpUtil.updateLocalTime();
							PublisherEntryPoint.LOGGER.info("Time updated."); //$NON-NLS-1$
						} catch (IOException e) {
							PublisherEntryPoint.LOGGER.log(Level.WARNING, "Could not update time", e); //$NON-NLS-1$
						}
					}
					super.onConnectivity(isConnected);
				}
			});
			new PublisherEntryPoint().start();
		}
	}
}