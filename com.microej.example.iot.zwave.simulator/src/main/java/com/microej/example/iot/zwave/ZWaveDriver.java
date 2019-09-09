/*
 * Java
 *
 * Copyright 2016-2019 MicroEJ Corp. All rights reserved.
 * For demonstration purpose only.
 * MicroEJ Corp. PROPRIETARY. Use is subject to license terms.
 */
package com.microej.example.iot.zwave;

import java.io.IOException;

import ej.basedriver.Controller;
import ej.basedriver.util.AbstractDriverService;
import ej.basedriver.zwave.ZwaveController;
import ej.ecom.io.CommPort;
import ej.ecom.io.Connector;

/**
 *
 */
public class ZWaveDriver extends AbstractDriverService {

	static {
		try {
			// Initiate Connector, only required in simulator.
			Connector.open("comm://aaa"); //$NON-NLS-1$
		} catch (@SuppressWarnings("unused") IOException e) {
			// e.printStackTrace();
		}
	}

	@Override
	protected Controller create(CommPort port) {
		System.out.println("Starts ZWave Driver."); //$NON-NLS-1$
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
