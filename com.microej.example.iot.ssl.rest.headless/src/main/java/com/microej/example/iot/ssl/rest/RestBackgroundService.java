/*
 * Java
 *
 * Copyright 2015-2019 MicroEJ Corp. All rights reserved.
 * For demonstration purpose only.
 * MicroEJ Corp. PROPRIETARY. Use is subject to license terms.
 */
package com.microej.example.iot.ssl.rest;

import ej.wadapps.app.BackgroundService;

/**
 *
 */
public class RestBackgroundService implements BackgroundService {

	@Override
	public void onStart() {

		try {
			ExampleRestyHttps.main(null);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onStop() {
		// Nothing to do.
	}

}
