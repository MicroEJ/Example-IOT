/*
 * Java
 *
 * Copyright 2016 IS2T. All rights reserved.
 * IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package ej.examples.iot.mqtt.app;

import ej.examples.iot.mqtt.HelloWorldSubscriber;
import ej.wadapps.app.BackgroundService;

/**
 *
 */
public class SubscriberBG implements BackgroundService {

	@Override
	public void onStart() {
		HelloWorldSubscriber.main(new String[0]);

	}

	@Override
	public void onStop() {

	}

}
