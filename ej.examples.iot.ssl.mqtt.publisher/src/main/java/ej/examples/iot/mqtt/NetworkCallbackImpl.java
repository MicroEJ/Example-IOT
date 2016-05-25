/*
 * Java
 *
 * Copyright 2016 IS2T. All rights reserved.
 * IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package ej.examples.iot.mqtt;

import android.net.ConnectivityManager;
import android.net.ConnectivityManager.NetworkCallback;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import ej.bon.Timer;
import ej.bon.TimerTask;
import ej.components.dependencyinjection.ServiceLoaderFactory;

/**
 *
 */
public abstract class NetworkCallbackImpl extends NetworkCallback {

	private static final int DELAY = 500;
	private static TimerTask registerTask;

	/**
	 *
	 */
	public NetworkCallbackImpl() {
		super();
		regisiterConnectivityManager();
	}

	private void regisiterConnectivityManager() {
		registerTask = null;

		ConnectivityManager connectivityManager = ServiceLoaderFactory.getServiceLoader()
				.getService(ConnectivityManager.class);
		if (connectivityManager != null) {
			NetworkRequest request = new NetworkRequest.Builder().build();
			connectivityManager.registerNetworkCallback(request, this);
			NetworkInfo info = connectivityManager.getActiveNetworkInfo();
			if (info.isConnected()) {
				onAvailable();
			} else {
				onLost();
			}
		} else {
			Timer timer = ServiceLoaderFactory.getServiceLoader().getService(Timer.class);
			registerTask = new TimerTask() {
				@Override
				public void run() {
					regisiterConnectivityManager();
				}
			};

			timer.schedule(registerTask, DELAY);
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

	protected void unregisiterConnectivityManager() {
		if (registerTask != null) {
			registerTask.cancel();
		}

		ConnectivityManager connectivityManager = ServiceLoaderFactory.getServiceLoader()
				.getService(ConnectivityManager.class);
		if (connectivityManager != null) {
			connectivityManager.unregisterNetworkCallback(this);
		}
	}
}
