/*
 * Java
 *
 * Copyright 2016 IS2T. All rights reserved.
 * IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.microej.example.iot.zwave;

import __ZWave__.generated.BackgroundServicesStandalone;

/**
 *
 */
public class Simulation {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new ZWaveDriver().onStart();
		BackgroundServicesStandalone.main(args);

	}

}
