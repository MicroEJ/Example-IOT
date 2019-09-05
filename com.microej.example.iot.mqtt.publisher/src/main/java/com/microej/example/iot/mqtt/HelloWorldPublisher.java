/*
 * Java
 *
 * Copyright 2016-2019 MicroEJ Corp. All rights reserved.
 * For demonstration purpose only.
 * MicroEJ Corp. PROPRIETARY. Use is subject to license terms.
 */
package com.microej.example.iot.mqtt;

import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import com.microej.profiling.AutomaticProfiler;
import com.microej.profiling.profiler.HeapProfiler;
import com.microej.profiling.profiler.ImmortalsProfiler;
import com.microej.profiling.profiler.InstantProfiler;
import com.microej.profiling.profiler.ThreadsProfiler;

/**
 * This example connects to a MQTT broker, publishes the "Hello World!" message
 * to the topic "MqttHelloWorld" and disconnects from the MQTT broker.
 */
public final class HelloWorldPublisher extends NetworkCallbackImpl implements Runnable {

	private static final String PUBLISHER = "[Publisher] ";
	/**
	 * Application logger.
	 */
	private static final Logger LOGGER = Logger.getLogger(HelloWorldPublisher.class.getName());
	private static final long PUBLISH_DELAY = 1000;

	private boolean sendMessage = false;
	private Thread thread;

	public static void main(String[] args) throws URISyntaxException {
		// Display all logs
		LOGGER.setLevel(Level.ALL);

		InstantProfiler[] profilers = new InstantProfiler[] { new HeapProfiler(), new ThreadsProfiler(),
				new ImmortalsProfiler() };
		for (InstantProfiler instantProfiler : profilers) {
			AutomaticProfiler automaticProfiler = new AutomaticProfiler(instantProfiler);
			automaticProfiler.watchIntervalRange(true);
			automaticProfiler.start();
		}

		new HelloWorldPublisher().registerConnectivityManager();
	}

	@Override
	public void onAvailable() {
		LOGGER.info(PUBLISHER + "Network available");
	}

	@Override
	public void onLost() {
		stopSending();
		LOGGER.info(PUBLISHER + "Network Lost");
	}

	@Override
	public void onInternet(boolean connected) {
		LOGGER.info(PUBLISHER + "Internet access=" + connected);
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
			LOGGER.info(PUBLISHER + "Try to connect to " + HelloWorldConstants.BROKER);
			client.connect();

			LOGGER.info(PUBLISHER + "Client connected");
			while (sendMessage) {
				MqttMessage message = new MqttMessage();
				String text = HelloWorldConstants.HELLO_WORLD_MESSAGE;
				message.setPayload(text.getBytes());
				client.publish(HelloWorldConstants.TOPIC, message);
				LOGGER.info(PUBLISHER + message + " published to " + HelloWorldConstants.TOPIC);
				sleep();
			}
		} catch (MqttException e) {
			LOGGER.log(Level.INFO, PUBLISHER + "Unable to connect to " + HelloWorldConstants.BROKER
					+ " and publish to topic " + HelloWorldConstants.TOPIC, e);
		} finally {
			if (client != null) {
				try {
					client.disconnect();
					LOGGER.info(PUBLISHER + "Client disconnected");
				} catch (MqttException e) {
					// Ignored.
				}
			}
		}
		thread = null;
		if (sendMessage) {
			sleep();
			synchronized (HelloWorldPublisher.this) {
				if (sendMessage) {
					startSending();
				}
			}
		}
	}

	/**
	 * Starts the publishing.
	 */
	public void start() {
		registerConnectivityManager();
	}

	/**
	 * Stops the publishing and unregister the network state listener.
	 */
	public void stop() {
		stopSending();
		unregisterConnectivityManager();
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
			synchronized (this) {
				sendMessage = false;
			}
		}
	}
}
