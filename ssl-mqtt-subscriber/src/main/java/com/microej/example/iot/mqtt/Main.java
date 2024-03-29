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
import org.eclipse.paho.client.mqttv3.MqttCallback;
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
 * This example connects to a MQTT broker, subscribe to a topic and publishes
 * any message received.
 */
public class Main implements SimpleNetworkCallback {

	/**
	 * Application logger.
	 */
	public static final Logger LOGGER = Logger.getLogger("[Subscriber]"); //$NON-NLS-1$
	private MqttClient client;
	private Thread thread;
	private boolean subscribe;
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

	@Override
	public void onInternet(boolean connected) {
		if (connected) {
			subscribe();
		} else {
			disconnect();
		}
	}

	@Override
	public void onConnectivity(boolean isConnected) {
		if (isConnected) {
			LOGGER.info("Network available"); //$NON-NLS-1$
		} else {
			LOGGER.info("Network Lost"); //$NON-NLS-1$
			disconnect();
		}
	}

	/**
	 * Starts the listening.
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

	private synchronized void subscribe() {
		if (!subscribe) {
			subscribe = true;
			thread = new Thread(new Runnable() {

				@Override
				public void run() {
					doSubscribe();
				}
			});
			thread.start();
		}
	}

	private void doSubscribe() {
		thread = null;
		if (client == null && subscribe) {
			try {
				client = new MqttClient(HelloWorldConstants.BROKER, HelloWorldConstants.SUBSCRIBER_ID);
				client.setCallback(new MqttCallback() {

					@Override
					public void messageArrived(String topic, MqttMessage message) throws Exception {
						LOGGER.info(new String(message.getPayload()) + " received from topic " + topic + " using SSL"); //$NON-NLS-1$
					}

					@Override
					public void connectionLost(Throwable cause) {
						LOGGER.info("connection lost: " + cause); //$NON-NLS-1$
					}
				});
				LOGGER.info("Try to connect to " + HelloWorldConstants.BROKER); //$NON-NLS-1$
				MqttConnectOptions option = new MqttConnectOptions();
				// Workaround to fix ssl timeout being raised during handshake.

				option.setConnectionTimeout(0);
				option.setSocketFactory(SSLContextBuilder.getSslContext().getSocketFactory());

				client.connect(option);
				LOGGER.info("Client connected using SSL"); //$NON-NLS-1$
				client.subscribe(HelloWorldConstants.TOPIC);
				LOGGER.info("Client subscribed to " + HelloWorldConstants.TOPIC); //$NON-NLS-1$
			} catch (MqttException e) {
				LOGGER.log(Level.INFO, "Unable to connect to " + HelloWorldConstants.BROKER //$NON-NLS-1$
						+ " and subscribe to topic " + HelloWorldConstants.TOPIC, e); //$NON-NLS-1$
				disconnect();
			}
		}
	}

	private synchronized void disconnect() {
		MqttClient client = this.client;
		this.client = null;
		if (client != null) {
			try {
				client.disconnect();
				LOGGER.info("Client disconnected"); //$NON-NLS-1$
			} catch (MqttException e) {
				LOGGER.fine(e.getMessage());
			}
		}
	}
}
