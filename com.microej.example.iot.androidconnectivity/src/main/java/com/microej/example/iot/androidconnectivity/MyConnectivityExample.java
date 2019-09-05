/*
 * Java
 *
 * Copyright 2016-2019 MicroEJ Corp. All rights reserved.
 * For demonstration purpose only.
 * MicroEJ Corp. PROPRIETARY. Use is subject to license terms.
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
		Main.LOGGER.info("Network is available."); //$NON-NLS-1$

	}

	@Override
	public void onLost(Network network) {
		Main.LOGGER.info("Network is lost."); //$NON-NLS-1$

	}

	@Override
	public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
		boolean hasInternet = networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
		Main.LOGGER.info("Connected to the internet = " //$NON-NLS-1$
				+ hasInternet);
	}

}
