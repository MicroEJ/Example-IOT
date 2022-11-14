/*
 * Java
 *
 * Copyright 2021-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.iot.cbor;

import ej.kf.FeatureEntryPoint;

/**
 * Background service using {@link Main#main}.
 */
public class CborEntryPoint implements FeatureEntryPoint {

	@Override
	public void start() {
		Main.main(null);
	}

	@Override
	public void stop() {
		// Not in use.
	}

}