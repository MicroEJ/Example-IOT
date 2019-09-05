/*
 * Java
 *
 * Copyright 2016-2019 MicroEJ Corp. All rights reserved.
 * For demonstration purpose only.
 * MicroEJ Corp. PROPRIETARY. Use is subject to license terms.
 */
package com.microej.example.iot.androidconnectivity.app;

import com.microej.example.iot.androidconnectivity.Main;
import com.microej.example.iot.androidconnectivity.MyConnectivityExample;

import android.net.ConnectivityManager;
import ej.components.dependencyinjection.ServiceLoaderFactory;
import ej.net.util.connectivity.ConnectivityUtil;
import ej.wadapps.app.BackgroundService;

/**
 * Background service registering a new {@link MyConnectivityExample}.
 */
public class ExampleBG implements BackgroundService {

	private MyConnectivityExample connectivityExample;

	@Override
	public void onStart() {
		ConnectivityManager connectivityManager = ServiceLoaderFactory.getServiceLoader()
				.getService(ConnectivityManager.class);
		if (connectivityManager != null) {
			connectivityExample = new MyConnectivityExample();
			ConnectivityUtil.registerAndCall(connectivityManager, connectivityExample);
		} else {
			Main.LOGGER.severe("No connectivity manager found."); //$NON-NLS-1$
		}
	}

	@Override
	public void onStop() {
		MyConnectivityExample connectivityExample = this.connectivityExample;
		this.connectivityExample = null;
		ConnectivityManager connectivityManager = ServiceLoaderFactory.getServiceLoader()
				.getService(ConnectivityManager.class);
		if (connectivityExample != null && connectivityManager != null) {
			connectivityManager.unregisterNetworkCallback(connectivityExample);
		}
	}

}
