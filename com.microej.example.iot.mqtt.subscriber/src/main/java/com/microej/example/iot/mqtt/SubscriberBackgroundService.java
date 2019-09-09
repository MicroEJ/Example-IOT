/*
 * Java
 *
 * Copyright 2016-2019 MicroEJ Corp. All rights reserved.
 * For demonstration purpose only.
 * MicroEJ Corp. PROPRIETARY. Use is subject to license terms.
 */
package com.microej.example.iot.mqtt;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
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
 * This example connects to a MQTT broker, creates a callback and subscribes to the topic "MqttHelloWorld".
 */
public class SubscriberBackgroundService implements BackgroundService, SimpleNetworkCallback {

	/**
	 * Application logger.
	 */
	public static final Logger LOGGER = Logger.getLogger("[Subscriber]"); //$NON-NLS-1$
	private MqttClient client;
	private Thread thread;
	private boolean subscribe;
	private SimpleNetworkCallbackAdapter simpleNetworkCallbackAdapter;

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
		subscribe = false;
		disconnect();
		ConnectivityManager connectivityManager = ServiceLoaderFactory.getServiceLoader()
				.getService(ConnectivityManager.class);
		if (connectivityManager != null && simpleNetworkCallbackAdapter != null) {
			connectivityManager.unregisterNetworkCallback(simpleNetworkCallbackAdapter);
		}
		simpleNetworkCallbackAdapter = null;
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
						LOGGER.info(new String(message.getPayload()) + " received from topic " + topic); //$NON-NLS-1$
					}

					@Override
					public void deliveryComplete(IMqttDeliveryToken token) {
						LOGGER.info("delivery complete: " + token); //$NON-NLS-1$
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
