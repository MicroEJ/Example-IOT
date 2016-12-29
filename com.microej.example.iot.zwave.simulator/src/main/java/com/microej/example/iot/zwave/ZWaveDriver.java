/*
 * Java
 *
 * Copyright 2016 IS2T. All rights reserved.
 * IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.microej.example.iot.zwave;

import java.io.IOException;

import ej.basedriver.Controller;
import ej.basedriver.zwave.ZwaveController;
import ej.ecom.io.CommPort;
import ej.ecom.io.Connector;
import ej.wadapps.basedriver.util.AbstractDriverBackgroundService;

/**
 *
 */
public class ZWaveDriver extends AbstractDriverBackgroundService {

	static {
		try {
			// Initiate Connector, only required in simulator.
			Connector.open("comm://aaa");
		} catch (IOException e) {
			// e.printStackTrace();
		}
	}

	@Override
	protected Controller create(CommPort port) {
		System.out.println("Starts ZWave Driver.");
		try {
			// Creates the controller.
			ZwaveController zwaveController = new ZwaveController(port,
					new ej.basedriver.util.CommPortConnection(port, 115200));
			return zwaveController;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
