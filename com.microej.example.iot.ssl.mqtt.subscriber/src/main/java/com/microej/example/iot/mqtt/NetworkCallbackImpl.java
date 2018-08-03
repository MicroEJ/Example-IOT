/*
 * Java
 *
 * Copyright 2016-2018 IS2T. All rights reserved.
 * For demonstration purpose only.
 * IS2T PROPRIETARY. Use is subject to license terms.
 */
package com.microej.example.iot.mqtt;

import android.net.ConnectivityManager;
import android.net.ConnectivityManager.NetworkCallback;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import ej.bon.Timer;
import ej.bon.TimerTask;
import ej.components.dependencyinjection.ServiceLoaderFactory;

/**
 * Network callback waiting for a {@link ConnectivityManager} to register itself. Once registered, checks the state of
 * the current network state to call the callbacks a first time.
 */
public abstract class NetworkCallbackImpl extends NetworkCallback {

	private static final int DELAY = 500;
	private TimerTask registerTask;
	private boolean register;

	/**
	 * Instantiates a {@link NetworkCallbackImpl}.
	 */
	public NetworkCallbackImpl() {
		super();
		registerConnectivityManager();
	}

	@Override
	public void onAvailable(Network network) {
		onAvailable();
	}

	@Override
	public void onLost(Network network) {
		onLost();
	}

	@Override
	public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
		boolean internet = networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
		onInternet(internet);
	}

	/**
	 * Called when a network is available.
	 */
	public abstract void onAvailable();

	/**
	 * Called when a network is lost.
	 */
	public abstract void onLost();


	/**
	 * Called when Internet state changes.
	 *
	 * @param connected
	 *            the Internet connectivity state.
	 */
	public abstract void onInternet(boolean connected);

	protected void registerConnectivityManager() {
		register = true;
		doRegister();
	}

	/**
	 * Unregister itself as a callback.
	 */
	protected void unregisiterConnectivityManager() {
		register = false;
		unregisterTask();

		ConnectivityManager connectivityManager = ServiceLoaderFactory.getServiceLoader()
				.getService(ConnectivityManager.class);
		if (connectivityManager != null) {
			connectivityManager.unregisterNetworkCallback(this);
		}
	}

	private void unregisterTask() {
		TimerTask registerTask = this.registerTask;
		this.registerTask = null;
		if (registerTask != null) {
			registerTask.cancel();
		}
	}

	private void doRegister() {
		unregisterTask();
		if (register) {
			ConnectivityManager connectivityManager = ServiceLoaderFactory.getServiceLoader()
					.getService(ConnectivityManager.class);

			if (connectivityManager != null) {
				connectivityManager.registerDefaultNetworkCallback(this);
				NetworkInfo info = connectivityManager.getActiveNetworkInfo();
				if (info != null && info.isConnected()) {
					onAvailable();
				} else {
					onLost();
				}
				NetworkCapabilities capabilities = connectivityManager
						.getNetworkCapabilities(connectivityManager.getActiveNetwork());
				onInternet(capabilities != null
						&& capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET));
			} else {
				Timer timer = ServiceLoaderFactory.getServiceLoader().getService(Timer.class);
				registerTask = new TimerTask() {
					@Override
					public void run() {
						doRegister();
					}
				};

				timer.schedule(registerTask, DELAY);
			}
		}
	}
}
