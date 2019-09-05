/*
 * Java
 *
 * Copyright 2019 IS2T. All rights reserved.
 * IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.microej.example.iot.ssl.rest;

import ej.wadapps.app.BackgroundService;

/**
 *
 */
public class RestBG implements BackgroundService {

	@Override
	public void onStart() {

		try {
			ExampleRestyHttps.main(null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub

	}

}
