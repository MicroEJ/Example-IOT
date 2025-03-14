/*
 * Java
 *
 * Copyright 2016-2025 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.iot.mqtt;

import java.util.logging.Level;
import java.util.logging.Logger;

import android.net.Network;
import android.net.NetworkCapabilities;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import android.net.ConnectivityManager;
import ej.service.ServiceFactory;

/**
 * This example connects to a MQTT broker, creates a callback and subscribes to the topic "MqttHelloWorld".
 */
public class Main {

	/**
	 * Application logger.
	 */
	public static final Logger LOGGER = Logger.getLogger("[Subscriber]"); //$NON-NLS-1$
	private MqttClient client;
	private Thread thread;
	private boolean subscribe;

	public static void main(String[] args) {
		// Display all logs
		Main.LOGGER.setLevel(Level.ALL);
		try {
			waitForConnectivity();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

		new Main().mqttSubscribe();
	}

	public static void waitForConnectivity() throws InterruptedException {
		LOGGER.info("=========== Waiting for connectivity ==========="); //$NON-NLS-1$
		final Object mutex = new Object();
		final ConnectivityManager service = ServiceFactory.getServiceLoader().getService(ConnectivityManager.class);
		if (service != null) {
			ConnectivityManager.NetworkCallback callback = new ConnectivityManager.NetworkCallback() {
				@Override
				public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
					if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
						synchronized (mutex) {
							mutex.notifyAll();
						}
					}
				}
			};
			service.registerDefaultNetworkCallback(callback);
			NetworkCapabilities capabilities = service.getNetworkCapabilities(service.getActiveNetwork());
			if (capabilities == null || !capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
				synchronized (mutex) {
					try {
						mutex.wait();
					} catch (InterruptedException e) {
						throw new InterruptedException(e.getMessage());
					}
				}
			}
			service.unregisterNetworkCallback(callback);
			LOGGER.info("Connected"); //$NON-NLS-1$
		} else {
			LOGGER.info("No connectivity manager found."); //$NON-NLS-1$
		}
	}

	private synchronized void mqttSubscribe() {
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
						LOGGER.info(new String(message.getPayload()) + " received from topic " + topic); //$NON-NLS-1$
					}

					@Override
					public void connectionLost(Throwable cause) {
						LOGGER.info("connection lost: " + cause); //$NON-NLS-1$
					}
				});
				LOGGER.info("Try to connect to " + HelloWorldConstants.BROKER); //$NON-NLS-1$
				client.connect();
				LOGGER.info("Client connected"); //$NON-NLS-1$
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
