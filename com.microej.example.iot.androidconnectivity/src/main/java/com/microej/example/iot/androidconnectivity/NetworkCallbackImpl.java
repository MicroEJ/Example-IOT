/*
 * Java
 *
 * Copyright 2016 IS2T. All rights reserved.
 * Use of this source code is subject to license terms.
 */
package com.microej.example.iot.androidconnectivity;

import android.net.ConnectivityManager;
import android.net.ConnectivityManager.NetworkCallback;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import ej.components.dependencyinjection.ServiceLoaderFactory;

/**
 *
 */
public abstract class NetworkCallbackImpl extends NetworkCallback {

	/**
	 *
	 */
	public NetworkCallbackImpl() {
		super();
		regisiterConnectivityManager();
	}

	protected void regisiterConnectivityManager() {
		// Load connectivityManager service.
		ConnectivityManager connectivityManager = ServiceLoaderFactory.getServiceLoader()
				.getService(ConnectivityManager.class);
		if (connectivityManager != null) {
			NetworkRequest request = new NetworkRequest.Builder().build();

			connectivityManager.registerNetworkCallback(request, this);
			System.out.println("NetworkCallback is registered.");
			NetworkInfo info = connectivityManager.getActiveNetworkInfo();
			System.out.println("Initial state isConnected:" + info.isConnected());
			if (info.isConnected()) {
				onAvailable();
			} else {
				onLost();
			}
		} else {
			System.out.println("No connectivity Manager found.");
		}
	}

	@Override
	public void onAvailable(Network network) {
		super.onAvailable(network);
		onAvailable();
	}

	@Override
	public void onLost(Network network) {
		super.onLost(network);
		onLost();
	}

	public abstract void onAvailable();

	public abstract void onLost();

	public void unregisiterConnectivityManager() {
		ConnectivityManager connectivityManager = ServiceLoaderFactory.getServiceLoader()
				.getService(ConnectivityManager.class);
		if (connectivityManager != null) {
			System.out.println("NetworkCallback is unregistered.");
			connectivityManager.unregisterNetworkCallback(this);
		}
	}
}
