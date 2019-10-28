/*
 * Java
 *
 * Copyright 2016-2019 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 * MicroEJ Corp. PROPRIETARY. Use is subject to license terms.
 */
package com.microej.example.iot.mqtt;

import java.io.IOException;
import java.util.logging.Level;

import android.net.ConnectivityManager;
import ej.components.dependencyinjection.ServiceLoaderFactory;
import ej.net.util.NtpUtil;
import ej.net.util.connectivity.ConnectivityUtil;
import ej.net.util.connectivity.SimpleNetworkCallbackAdapter;

/**
 * This example connects to a MQTT broker, subscribe toa topic and publishes any message received.
 */
public final class Main {


	public static void main(String[] args) {
		// Display all logs
		SubscriberBackgroundService.LOGGER.setLevel(Level.ALL);

		ConnectivityManager connectivityManager = ServiceLoaderFactory.getServiceLoader()
				.getService(ConnectivityManager.class);
		if (connectivityManager != null) {
			ConnectivityUtil.registerAndCall(connectivityManager, new SimpleNetworkCallbackAdapter() {

				@Override
				public void onConnectivity(boolean isConnected) {
					if(isConnected) {
						try {
							NtpUtil.updateLocalTime();
							SubscriberBackgroundService.LOGGER.info("Time updated."); //$NON-NLS-1$
						} catch (IOException e) {
							SubscriberBackgroundService.LOGGER.log(Level.WARNING, "Could not update time", e); //$NON-NLS-1$
						}
					}
					super.onConnectivity(isConnected);
				}
			});
			new SubscriberBackgroundService().onStart();
		}
	}
}
