/*
 * Java
 *
 * Copyright 2019-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.iot;

import java.io.IOException;

import ej.kf.FeatureEntryPoint;

/**
 * A FeatureEntryPoint using {@link Main}.
 */
public class DynamicServerEntryPoint implements FeatureEntryPoint {

	@Override
	public void start() {
		try {
			Main.main(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void stop() {
		Main.stop();
	}

}
