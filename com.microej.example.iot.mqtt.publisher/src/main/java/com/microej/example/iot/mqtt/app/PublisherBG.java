/*
 * Java
 *
 * Copyright 2016-2019 MicroEJ Corp. All rights reserved.
 * For demonstration purpose only.
 * MicroEJ Corp. PROPRIETARY. Use is subject to license terms.
 */
package com.microej.example.iot.mqtt.app;

import com.microej.example.iot.mqtt.HelloWorldPublisher;

import ej.wadapps.app.BackgroundService;

/**
 * Background service using the helloworld publisher.
 */
public class PublisherBG implements BackgroundService {

	private HelloWorldPublisher helloWorldPublisher;

	@Override
	public void onStart() {
		helloWorldPublisher = new HelloWorldPublisher();
		helloWorldPublisher.start();
	}

	@Override
	public void onStop() {
		helloWorldPublisher.stop();
		helloWorldPublisher = null;
	}
}
