/*
 * Java
 *
 * Copyright 2016 IS2T. All rights reserved.
 * Use of this source code is subject to license terms.
 */
package com.microej.example.iot.mqtt.app;

import com.microej.example.iot.mqtt.HelloWorldPublisher;
import ej.wadapps.app.BackgroundService;
/**
 *
 */
public class PublisherBG extends Thread implements BackgroundService {

	private static final int DELAY = 5000; // 5s
	private boolean running = false;

	@Override
	public void onStart() {
		running = true;
		this.start();

	}

	@Override
	public void onStop() {
		running = false;
		this.interrupt();

	}

	@Override
	public void run() {
		while (running) {
			HelloWorldPublisher.main(new String[0]);
			try {
				Thread.sleep(DELAY);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}


}
