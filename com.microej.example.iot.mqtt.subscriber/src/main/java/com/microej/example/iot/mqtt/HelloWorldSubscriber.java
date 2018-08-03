/*
 * Java
 *
 * Copyright 2015-2016 IS2T. All rights reserved.
 * Use of this source code is subject to license terms..
 */
package com.microej.example.iot.mqtt;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * This example connects to a MQTT broker, creates a callback and subscribes to the topic "MqttHelloWorld".
 */
public final class HelloWorldSubscriber extends NetworkCallbackImpl {

	private static final String SUBSCRIBER = "[Subscriber] ";
	/**
	 * Application logger.
	 */
	private static final Logger LOGGER = Logger.getLogger(HelloWorldSubscriber.class.getName());
	private MqttClient client;
	private Thread thread;
	private boolean subscribe;

	public static void main(String[] args) {
		// Display all logs
		LOGGER.setLevel(Level.ALL);

		new HelloWorldSubscriber();
	}

	@Override
	public void onAvailable() {
		LOGGER.info(SUBSCRIBER + "Network available");
	}

	@Override
	public void onLost() {
		LOGGER.info(SUBSCRIBER + "Network Lost");
		disconnect();
	}

	@Override
	public void onInternet(boolean connected) {
		if (connected) {
			subscribe();
		} else {
			disconnect();
		}
	}

	/**
	 * Stops the publishing and unregister the network state listener.
	 */
	public synchronized void stop() {
		subscribe = false;
		Thread thread = this.thread;
		this.thread = null;
		if (thread != null) {
			thread.interrupt();
		}
		disconnect();
		unregisiterConnectivityManager();
	}

	private synchronized void subscribe() {
		subscribe = true;
		thread = new Thread(new Runnable() {

			@Override
			public void run() {
				doSubscribe();
			}
		});
		thread.start();
	}

	/**
	 *
	 */
	private void doSubscribe() {
		thread = null;
		if (client == null) {
			try {
				client = new MqttClient(HelloWorldConstants.BROKER, HelloWorldConstants.SUBSCRIBER_ID);
				client.setCallback(new MqttCallback() {

					@Override
					public void messageArrived(String topic, MqttMessage message) throws Exception {
						LOGGER.info(SUBSCRIBER + new String(message.getPayload()) + " received from topic " + topic);
					}

					@Override
					public void deliveryComplete(IMqttDeliveryToken token) {
						LOGGER.info(SUBSCRIBER + "delivery complete: " + token);
					}

					@Override
					public void connectionLost(Throwable cause) {
						LOGGER.info(SUBSCRIBER + "connection lost: " + cause);
					}
				});
				LOGGER.info(SUBSCRIBER + "Try to connect to " + HelloWorldConstants.BROKER);
				client.connect();
				LOGGER.info(SUBSCRIBER + "Client connected");
				client.subscribe(HelloWorldConstants.TOPIC);
				LOGGER.info(SUBSCRIBER + "Client subscribed to " + HelloWorldConstants.TOPIC);
			} catch (MqttException e) {
				LOGGER.info(SUBSCRIBER + "Unable to connect to " + HelloWorldConstants.BROKER
						+ " and subscribe to topic " + HelloWorldConstants.TOPIC);
				disconnect();
				try {
					Thread.sleep(1000);
					synchronized (HelloWorldSubscriber.this) {
						if (subscribe) {
							subscribe();
						}
					}
				} catch (InterruptedException e2) {
					subscribe = false;
				}
			}
		}
	}

	private synchronized void disconnect() {
		MqttClient client = this.client;
		this.client = null;
		if (client != null) {
			try {
				client.disconnect();
				LOGGER.info(SUBSCRIBER + "Client disconnected");
			} catch (MqttException e2) {
				// Ignored.
			}
		}
	}

}
