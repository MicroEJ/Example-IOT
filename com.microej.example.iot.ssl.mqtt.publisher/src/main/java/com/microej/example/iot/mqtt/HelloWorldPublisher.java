/*
 * Java
 *
 * Copyright 2016-2018 IS2T. All rights reserved.
 * For demonstration purpose only.
 * IS2T PROPRIETARY. Use is subject to license terms.
 */
package com.microej.example.iot.mqtt;

import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import android.net.ConnectivityManager;
import android.net.ConnectivityManager.NetworkCallback;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.SntpClient;
import ej.bon.Util;
import ej.components.dependencyinjection.ServiceLoaderFactory;

/**
 * This example connects to a MQTT broker, publishes the "Hello World!" message to the topic "MqttHelloWorld" and
 * disconnects from the MQTT broker.
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

	public static void main(String[] args) {
		// Display all logs
		LOGGER.setLevel(Level.ALL);

		updateTime();

		new HelloWorldPublisher();
	}

	/**
	 *
	 */
	private static void updateTime() {
		waitForConnectivity();
		SntpClient ntpClient = new SntpClient();
		while (Util.currentTimeMillis() < 1000000) {
			/**
			 * Request NTP time
			 */
			if (ntpClient.requestTime("ntp.ubuntu.com", 123, 1000)) { //$NON-NLS-1$
				long now = ntpClient.getNtpTime() + Util.platformTimeMillis() - ntpClient.getNtpTimeReference();

				Calendar.getInstance().setTimeInMillis(now);
				Util.setCurrentTimeMillis(now);
			} else {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
			}
		}
	}

	private static void waitForConnectivity() {
		final Object mutex = new Object();
		final ConnectivityManager service = ServiceLoaderFactory.getServiceLoader()
				.getService(ConnectivityManager.class);
		if (service != null) {
			NetworkCallback callback = new NetworkCallback() {
				@Override
				public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
					if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
						synchronized (mutex) {
							mutex.notify();
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
						e.printStackTrace();
					}
				}
			}
			service.unregisterNetworkCallback(callback);
		}
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
	 * Stops the publishing and unregister the network state listener.
	 */
	public void stop() {
		stopSending();
		unregisiterConnectivityManager();
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
