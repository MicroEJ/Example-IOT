/*
 * Java
 *
 * Copyright 2018-2019 MicroEJ Corp. All rights reserved.
 * For demonstration purpose only.
 * MicroEJ Corp. PROPRIETARY. Use is subject to license terms.
 */
package com.microej.example.iot.androidconnectivity;

import java.util.logging.Logger;

import android.net.ConnectivityManager;
import ej.components.dependencyinjection.ServiceLoaderFactory;
import ej.net.util.connectivity.ConnectivityUtil;

/**
 * Entry point.
 */
public class Main {

	/**
	 * Logger use for the application.
	 */
	public static final Logger LOGGER = Logger.getLogger("Android connectivity example"); //$NON-NLS-1$

	/**
	 * Entry point.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		ConnectivityManager connectivityManager = ServiceLoaderFactory.getServiceLoader()
				.getService(ConnectivityManager.class);
		if (connectivityManager != null) {
			MyConnectivityExample connectivityExample = new MyConnectivityExample();
			ConnectivityUtil.registerAndCall(connectivityManager, connectivityExample);
		} else {
			LOGGER.severe("No connectivity manager found."); //$NON-NLS-1$
		}
	}

}
