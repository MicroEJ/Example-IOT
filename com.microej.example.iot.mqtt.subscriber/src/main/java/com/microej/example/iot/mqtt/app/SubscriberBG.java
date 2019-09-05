/*
 * Java
 *
 * Copyright 2016-2019 MicroEJ Corp. All rights reserved.
 * For demonstration purpose only.
 * MicroEJ Corp. PROPRIETARY. Use is subject to license terms.
 */
package com.microej.example.iot.mqtt.app;

import com.microej.example.iot.mqtt.HelloWorldSubscriber;

import ej.wadapps.app.BackgroundService;

/**
 *
 */
public class SubscriberBG implements BackgroundService {

	private HelloWorldSubscriber helloWorldSubscriber;

	@Override
	public void onStart() {
		helloWorldSubscriber = new HelloWorldSubscriber();
		helloWorldSubscriber.start();
	}

	@Override
	public void onStop() {
		helloWorldSubscriber.stop();
		helloWorldSubscriber = null;
	}

}
