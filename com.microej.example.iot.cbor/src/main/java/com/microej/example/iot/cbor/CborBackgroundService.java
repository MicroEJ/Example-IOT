/*
 * Java
 *
 * Copyright 2021 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.iot.cbor;

import ej.wadapps.app.BackgroundService;

/**
 * Background service using {@link Main#main}.
 */
public class CborBackgroundService implements BackgroundService {

	@Override
	public void onStart() {
		Main.main(null);
	}

	@Override
	public void onStop() {
		// Not in use.
	}

}
