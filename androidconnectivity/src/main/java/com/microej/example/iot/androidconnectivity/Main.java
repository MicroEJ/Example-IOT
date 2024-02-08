/*
 * Java
 *
 * Copyright 2016-2023 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.iot.androidconnectivity;

import java.util.logging.Logger;

import android.net.ConnectivityManager;
import ej.net.util.connectivity.ConnectivityUtil;
import ej.service.ServiceFactory;

/**
 * KF Entry Point registering a new {@link MyConnectivityExample}.
 */
public class Main {

	/**
	 * Logger use for the application.
	 */
	public static final Logger LOGGER = Logger.getLogger("Android connectivity example"); //$NON-NLS-1$

	public static void main(String[] args) {
		ConnectivityManager connectivityManager = ServiceFactory
				.getService(ConnectivityManager.class);
		if (connectivityManager != null) {
			MyConnectivityExample connectivityExample = new MyConnectivityExample();
			ConnectivityUtil.registerAndCall(connectivityManager, connectivityExample);
		} else {
			LOGGER.severe("No connectivity manager found."); //$NON-NLS-1$
		}
	}
}
