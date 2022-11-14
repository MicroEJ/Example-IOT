/*
 * Java
 *
 * Copyright 2016-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.iot.androidconnectivity;

import android.net.ConnectivityManager.NetworkCallback;
import android.net.Network;
import android.net.NetworkCapabilities;

/**
 * {@link NetworkCallback} printing its state.
 */
public class MyConnectivityExample extends NetworkCallback {

	@Override
	public void onAvailable(Network network) {
		ExampleEntryPoint.LOGGER.info("Network is available."); //$NON-NLS-1$

	}

	@Override
	public void onLost(Network network) {
		ExampleEntryPoint.LOGGER.info("Network is lost."); //$NON-NLS-1$

	}

	@Override
	public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
		boolean hasInternet = networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
		ExampleEntryPoint.LOGGER.info("Connected to the internet = " //$NON-NLS-1$
				+ hasInternet);
	}

}
