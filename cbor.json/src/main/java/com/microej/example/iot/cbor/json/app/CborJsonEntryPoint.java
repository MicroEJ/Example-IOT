/*
 * Java
 *
 * Copyright 2016-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.iot.cbor.json.app;

import com.microej.example.iot.cbor.json.CborJsonhelloWorld;

import ej.kf.FeatureEntryPoint;

/**
 * FeatureEntryPoint using {@link CborJsonhelloWorld#main}.
 */
public class CborJsonEntryPoint implements FeatureEntryPoint {

	@Override
	public void start() {
		CborJsonhelloWorld.main(null);
	}

	@Override
	public void stop() {
		// Not used.
	}

}
