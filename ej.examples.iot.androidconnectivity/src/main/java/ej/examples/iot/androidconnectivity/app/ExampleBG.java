/*
 * Java
 *
 * Copyright 2016 IS2T. All rights reserved.
 * IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package ej.examples.iot.androidconnectivity.app;

import ej.examples.iot.androidconnectivity.MyConnectivityExample;
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
