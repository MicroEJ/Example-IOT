/*
 * Java
 *
 * Copyright 2016 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/
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
