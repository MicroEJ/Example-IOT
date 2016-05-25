/*
 * Java
 *
 * Copyright 2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package ej.examples.iot.mqtt;

import static ej.examples.iot.mqtt.HelloWorldConstants.BROKER;
import static ej.examples.iot.mqtt.HelloWorldConstants.TOPIC;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * This example connects to a MQTT broker, publishes the "Hello World!" message to the topic "MqttHelloWorld" and
 * disconnects from the MQTT broker.
 */
public final class HelloWorldPublisher extends NetworkCallbackImpl {

	/**
	 * Application logger.
	 */
	private static final Logger LOGGER = Logger.getLogger(HelloWorldPublisher.class.getName());
	private static final long PUBLISH_DELAY = 1000;

	private boolean sendMessage = false;

	public static void main(String[] args) {
		// Display all logs
		LOGGER.setLevel(Level.ALL);

		new HelloWorldPublisher();
	}

	@Override
	public void onAvailable() {
		sendMessage = true;
		LOGGER.info("[Publisher] Network available");

		MqttClient client = null;
		try {
			client = new MqttClient(HelloWorldConstants.BROKER, HelloWorldConstants.PUBLISHER_ID);
			LOGGER.info("[Publisher] Try to connect to " + HelloWorldConstants.BROKER);
			client.connect();

			LOGGER.info("[Publisher] Client connected");
			while (sendMessage) {
				MqttMessage message = new MqttMessage();
				String text = HelloWorldConstants.HELLO_WORLD_MESSAGE;
				message.setPayload(text.getBytes());
				client.publish(HelloWorldConstants.TOPIC, message);
				LOGGER.info(message + " published to " + HelloWorldConstants.TOPIC);
				sleep();
			}
		} catch (MqttException e) {
			e.printStackTrace();
			LOGGER.info("[Publisher] Unable to connect to " + BROKER + " and publish to topic " + TOPIC);
		} finally {
			if (client != null) {
				try {
					client.disconnect();
					LOGGER.info("[Publisher] Client disconnected");
				} catch (MqttException e) {
					// Ignored.
				}
			}
		}
		unregisiterConnectivityManager();
	}

	private void sleep() {
		try {
			Thread.sleep(PUBLISH_DELAY);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onLost() {
		sendMessage = false;
		LOGGER.info("[Publisher] Network Lost");
		unregisiterConnectivityManager();

	}
}
