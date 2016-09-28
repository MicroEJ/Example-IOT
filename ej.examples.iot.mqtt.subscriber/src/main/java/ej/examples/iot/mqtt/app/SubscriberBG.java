/*
 * Java
 *
 * Copyright 2016 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/
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
