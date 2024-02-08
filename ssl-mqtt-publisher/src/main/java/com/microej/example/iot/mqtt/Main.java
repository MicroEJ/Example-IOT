/*
 * Java
 *
 * Copyright 2016-2023 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.iot.mqtt;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import ej.net.util.NtpUtil;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import android.net.ConnectivityManager;
import ej.net.util.connectivity.ConnectivityUtil;
import ej.net.util.connectivity.SimpleNetworkCallback;
import ej.net.util.connectivity.SimpleNetworkCallbackAdapter;
import ej.service.ServiceFactory;

/**
 * This example connects to a MQTT broker, publishes the "Hello World!" message to the topic "MqttHelloWorld" and
 * disconnects from the MQTT broker.
 */
public class Main implements Runnable, SimpleNetworkCallback {

	/**
	 * Application logger.
	 */
	public static final Logger LOGGER = Logger.getLogger("[Publisher]"); //$NON-NLS-1$
	private static final long PUBLISH_DELAY = 1000;

	private boolean sendMessage = false;
	private Thread thread;
	private SimpleNetworkCallbackAdapter simpleNetworkCallbackAdapter;

	public static void main(String[] args) {
		// Display all logs
		Main.LOGGER.setLevel(Level.ALL);

		ConnectivityManager connectivityManager = ServiceFactory.getService(ConnectivityManager.class);
		if (connectivityManager != null) {
			ConnectivityUtil.registerAndCall(connectivityManager, new SimpleNetworkCallbackAdapter() {

				@Override
				public void onConnectivity(boolean isConnected) {
					if(isConnected) {
						try {
							NtpUtil.updateLocalTime();
							Main.LOGGER.info("Time updated."); //$NON-NLS-1$
						} catch (IOException e) {
							Main.LOGGER.log(Level.WARNING, "Could not update time", e); //$NON-NLS-1$
						}
					}
					super.onConnectivity(isConnected);
				}
			});
			new Main().start();
		}
	}

	/**
	 * Starts the publishing.
	 */
	public synchronized void start() {
		if (simpleNetworkCallbackAdapter == null) {
			ConnectivityManager connectivityManager = ServiceFactory.getService(ConnectivityManager.class);
			if (connectivityManager != null) {
				simpleNetworkCallbackAdapter = new SimpleNetworkCallbackAdapter(this);
				ConnectivityUtil.registerAndCall(connectivityManager, simpleNetworkCallbackAdapter);
			} else {
				LOGGER.severe("No connectivity manager found"); //$NON-NLS-1$
			}
		}
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
			MqttConnectOptions option = new MqttConnectOptions();
			// Workaround to fix ssl timeout being raised during handshake.

			option.setConnectionTimeout(0);
			option.setSocketFactory(SSLContextBuilder.getSslContext().getSocketFactory());

			LOGGER.info("Try to connect to " + HelloWorldConstants.BROKER); //$NON-NLS-1$
			client.connect(option);

			LOGGER.info("Client connected using SSL"); //$NON-NLS-1$
			while (sendMessage) {
				MqttMessage message = new MqttMessage(HelloWorldConstants.HELLO_WORLD_MESSAGE.getBytes());
				client.publish(HelloWorldConstants.TOPIC, message);
				LOGGER.info(message + " published to " + HelloWorldConstants.TOPIC + " using SSL"); //$NON-NLS-1$
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
		synchronized (Main.this) {
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
