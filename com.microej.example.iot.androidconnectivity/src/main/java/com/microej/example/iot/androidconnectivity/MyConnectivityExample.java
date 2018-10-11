/*
 * Java
 *
 * Copyright 2016-2018 IS2T. All rights reserved.
 * For demonstration purpose only.
 * IS2T PROPRIETARY. Use is subject to license terms.
 */
package com.microej.example.iot.androidconnectivity;

/**
 *
 */
public class MyConnectivityExample extends NetworkCallbackImpl {

	@Override
	public void onAvailable() {
		System.out.println("Network is available.");

	}

	@Override
	public void onLost() {
		System.out.println("Network is lost.");

	}

	@Override
	public void onInternet(boolean connected) {
		System.out.println("Connected to the internet = " + connected);
	}

}
