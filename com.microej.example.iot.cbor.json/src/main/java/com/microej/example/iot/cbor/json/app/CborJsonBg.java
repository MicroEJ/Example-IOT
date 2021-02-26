/*
 * Java
 *
 * Copyright 2016-2021 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.iot.cbor.json.app;

import com.microej.example.iot.cbor.json.CborJsonhelloWorld;

import ej.wadapps.app.BackgroundService;

/**
 * Background service using {@link CborJsonhelloWorld#main}.
 */
public class CborJsonBg implements BackgroundService {

	@Override
	public void onStart() {
		CborJsonhelloWorld.main(null);
	}

	@Override
	public void onStop() {
		// Not used.
	}

}
