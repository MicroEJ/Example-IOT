/*
 * Java
 *
 * Copyright 2016-2019 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 * MicroEJ Corp. PROPRIETARY. Use is subject to license terms.
 */
package com.microej.example.iot.mqtt;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import android.net.ConnectivityManager;
import ej.components.dependencyinjection.ServiceLoaderFactory;
import ej.net.util.connectivity.ConnectivityUtil;
import ej.net.util.connectivity.SimpleNetworkCallback;
import ej.net.util.connectivity.SimpleNetworkCallbackAdapter;
import ej.wadapps.app.BackgroundService;

/**
 * This example connects to a MQTT broker, publishes the "Hello World!" message to the topic "MqttHelloWorld" and
 * disconnects from the MQTT broker.
 */
public class PublisherBackgroundService implements BackgroundService, Runnable, SimpleNetworkCallback {

	/**
	 * Application logger.
	 */
	public static final Logger LOGGER = Logger.getLogger("[Publisher]"); //$NON-NLS-1$
	private static final long PUBLISH_DELAY = 1000;

	private boolean sendMessage = false;
	private Thread thread;
	private SimpleNetworkCallbackAdapter simpleNetworkCallbackAdapter;

	/**
	 * Starts the publishing.
	 */
	@Override
	public synchronized void onStart() {
		if (simpleNetworkCallbackAdapter == null) {
			ConnectivityManager connectivityManager = ServiceLoaderFactory.getServiceLoader()
					.getService(ConnectivityManager.class);
			if (connectivityManager != null) {
				simpleNetworkCallbackAdapter = new SimpleNetworkCallbackAdapter(this);
				ConnectivityUtil.registerAndCall(connectivityManager, simpleNetworkCallbackAdapter);
			} else {
				LOGGER.severe("No connectivity manager found"); //$NON-NLS-1$
			}
		}
	}

	/**
	 * Stops the publishing and unregister the network state listener.
	 */
	@Override
	public synchronized void onStop() {
		stopSending();
		ConnectivityManager connectivityManager = ServiceLoaderFactory.getServiceLoader()
				.getService(ConnectivityManager.class);
		if (connectivityManager != null && simpleNetworkCallbackAdapter != null) {
			connectivityManager.unregisterNetworkCallback(simpleNetworkCallbackAdapter);
		}
		simpleNetworkCallbackAdapter = null;
	}

	@Override
	public void onConnectivity(boolean isConnected) {
		if (isConnected) {
			LOGGER.info("Network available"); //$NON-NLS-1$
		} else {
			stopSending();
			LOGGER.info("Network Lost"); //$NON-NLS-1$
		}
	}

	@Override
	public void onInternet(boolean connected) {
		LOGGER.info("Internet access=" + connected); //$NON-NLS-1$
		if (connected) {
			startSending();
		} else {
			stopSending();
		}
	}

	@Override
	public void run() {
		MqttClient client = null;
		try {
			client = new MqttClient(HelloWorldConstants.BROKER, HelloWorldConstants.PUBLISHER_ID);
			LOGGER.info("Try to connect to " + HelloWorldConstants.BROKER); //$NON-NLS-1$
			client.connect();

			LOGGER.info("Client connected"); //$NON-NLS-1$
			while (sendMessage) {
				MqttMessage message = new MqttMessage(HelloWorldConstants.HELLO_WORLD_MESSAGE.getBytes());
				client.publish(HelloWorldConstants.TOPIC, message);
				LOGGER.info(message + " published to " + HelloWorldConstants.TOPIC); //$NON-NLS-1$
				sleep();
			}
		} catch (MqttException e) {
			LOGGER.log(Level.INFO, "Unable to connect to " + HelloWorldConstants.BROKER //$NON-NLS-1$
					+ " and publish to topic " + HelloWorldConstants.TOPIC, e); //$NON-NLS-1$
			sleep();
		} finally {
			if (client != null) {
				try {
					client.disconnect();
					LOGGER.info("Client disconnected"); //$NON-NLS-1$
				} catch (MqttException e) {
					LOGGER.log(Level.INFO, e.getMessage(), e);
				}
			}
		}

		// Try again.
		synchronized (PublisherBackgroundService.this) {
			thread = null;
			if (sendMessage) {
				startSending();
			}
		}
	}

	private synchronized void stopSending() {
		sendMessage = false;
		Thread thread = this.thread;
		this.thread = null;
		if (thread != null) {
			thread.interrupt();
		}
	}

	private synchronized void startSending() {
		sendMessage = true;
		if (thread == null) {
			thread = new Thread(this, getClass().getSimpleName());
			thread.start();
		}
	}

	private void sleep() {
		try {
			Thread.sleep(PUBLISH_DELAY);
		} catch (InterruptedException e) {
			LOGGER.fine(e.getMessage());
			stopSending();
		}
	}
}
