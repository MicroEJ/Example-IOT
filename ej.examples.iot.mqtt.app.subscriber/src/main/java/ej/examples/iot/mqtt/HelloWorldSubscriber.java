/*
 * Java
 *
 * Copyright 2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package ej.examples.iot.mqtt;

import static ej.examples.iot.mqtt.HelloWorldConstants.BROKER;
import static ej.examples.iot.mqtt.HelloWorldConstants.SUBSCRIBER_ID;
import static ej.examples.iot.mqtt.HelloWorldConstants.TOPIC;

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

	/**
	 * Application logger.
	 */
	private static final Logger LOGGER = Logger.getLogger(HelloWorldSubscriber.class.getName());
	private MqttClient client;

	public static void main(String[] args) {
		// Display all logs
		LOGGER.setLevel(Level.ALL);

		new HelloWorldSubscriber();
	}

	@Override
	public void onAvailable() {
		LOGGER.info("[Subsriber] Network available");
		client = null;
		try {
			client = new MqttClient(BROKER, SUBSCRIBER_ID);
			client.setCallback(new MqttCallback() {

				@Override
				public void messageArrived(String topic, MqttMessage message) throws Exception {
					LOGGER.info(new String(message.getPayload()) + " received from topic " + topic);
				}

				@Override
				public void deliveryComplete(IMqttDeliveryToken token) {
					LOGGER.info("[Subsriber] delivery complete: " + token);
				}

				@Override
				public void connectionLost(Throwable cause) {
					LOGGER.info("[Subsriber] connection lost: " + cause);
				}
			});
			LOGGER.info("[Subsriber] Try to connect to " + HelloWorldConstants.BROKER);
			client.connect();
			LOGGER.info("[Subsriber] Client connected");
			client.subscribe(TOPIC);
			LOGGER.info("[Subsriber] Client subscribed to " + TOPIC);
		} catch (MqttException e) {
			LOGGER.info("[Subsriber] Unable to connect to " + BROKER + " and subscribe to topic " + TOPIC);
			disconnect();
		}
	}

	private void disconnect() {
		if (client != null) {
			try {
				client.disconnect();
				LOGGER.info("[Subsriber] Client disconnected");
			} catch (MqttException e2) {
				// Ignored.
			}
		}
	}

	@Override
	public void onLost() {
		LOGGER.info("[Subsriber] Network Lost");
		disconnect();
	}
}
