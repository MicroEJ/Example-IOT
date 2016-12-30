/*
 * Java
 *
 * Copyright 2016 IS2T. All rights reserved.
 * Use of this source code is subject to license terms.
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

	}

	@Override
	public void onStop() {
		connectivityExample.unregisiterConnectivityManager();

	}

}
