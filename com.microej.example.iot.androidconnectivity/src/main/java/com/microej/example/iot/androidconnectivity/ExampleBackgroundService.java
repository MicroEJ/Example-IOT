/*
 * Java
 *
 * Copyright 2016-2019 MicroEJ Corp. All rights reserved.
 * For demonstration purpose only.
 * MicroEJ Corp. PROPRIETARY. Use is subject to license terms.
 */
package com.microej.example.iot.androidconnectivity;

import java.util.logging.Logger;

import android.net.ConnectivityManager;
import ej.components.dependencyinjection.ServiceLoaderFactory;
import ej.net.util.connectivity.ConnectivityUtil;
import ej.wadapps.app.BackgroundService;

/**
 * Background service registering a new {@link MyConnectivityExample}.
 */
public class ExampleBackgroundService implements BackgroundService {

	/**
	 * Logger use for the application.
	 */
	public static final Logger LOGGER = Logger.getLogger("Android connectivity example"); //$NON-NLS-1$

	private MyConnectivityExample connectivityExample;

	@Override
	public void onStart() {
		ConnectivityManager connectivityManager = ServiceLoaderFactory.getServiceLoader()
				.getService(ConnectivityManager.class);
		if (connectivityManager != null) {
			connectivityExample = new MyConnectivityExample();
			ConnectivityUtil.registerAndCall(connectivityManager, connectivityExample);
		} else {
			LOGGER.severe("No connectivity manager found."); //$NON-NLS-1$
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
