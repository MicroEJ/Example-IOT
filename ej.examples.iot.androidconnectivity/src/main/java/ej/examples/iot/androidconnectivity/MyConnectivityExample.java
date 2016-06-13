/*
 * Java
 *
 * Copyright 2016 IS2T. All rights reserved.
 * IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package ej.examples.iot.androidconnectivity;

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
