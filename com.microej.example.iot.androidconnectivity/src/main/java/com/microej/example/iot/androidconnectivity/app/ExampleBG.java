/*
 * Java
 *
 * Copyright 2016-2018 IS2T. All rights reserved.
 * For demonstration purpose only.
 * IS2T PROPRIETARY. Use is subject to license terms.
 */
package com.microej.example.iot.androidconnectivity.app;

import com.microej.example.iot.androidconnectivity.MyConnectivityExample;

import ej.wadapps.app.BackgroundService;
/**
 *
 */
public class ExampleBG implements BackgroundService {

	private MyConnectivityExample connectivityExample;

	@Override
	public void onStart() {
		connectivityExample = new MyConnectivityExample();
		connectivityExample.registerConnectivityManager();
	}

	@Override
	public void onStop() {
		connectivityExample.unregisterConnectivityManager();
	}

}
