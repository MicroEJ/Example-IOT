/*
 * Java
 *
 * Copyright 2016 IS2T. All rights reserved.
 * Use of this source code is subject to license terms.
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

}
