/*
 * Java
 *
 * Copyright 2016-2020 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.iot.cbor.app;

import com.microej.example.iot.cbor.CborhelloWorld;

import ej.wadapps.app.BackgroundService;

/**
 * Background service using {@link CborhelloWorld#main}.
 */
public class CborBg implements BackgroundService {

	@Override
	public void onStart() {
		CborhelloWorld.main(null);
	}

	@Override
	public void onStop() {
		// Not used.
	}

}
