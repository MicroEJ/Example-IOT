/*
 * Java
 *
 * Copyright 2016-2019 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 * MicroEJ Corp. PROPRIETARY. Use is subject to license terms.
 */
package com.microej.example.iot.zwave.app;

import java.util.logging.Logger;

import com.microej.example.iot.zwave.ControllerManager;
import com.microej.example.iot.zwave.DryContactManager;
import com.microej.example.iot.zwave.SwitchWithReturnStateManager;

import ej.basedriver.Controller;
import ej.basedriver.DryContact;
import ej.basedriver.SwitchWithReturnState;
import ej.ecom.DeviceManager;
import ej.wadapps.app.BackgroundService;

/**
 * Background Service starting the ZWave management.
 */
public class ZWaveBG implements BackgroundService {

	/**
	 * Logger used.
	 */
	public static final Logger LOGGER = Logger.getLogger("ControllerManager"); //$NON-NLS-1$

	private SwitchWithReturnStateManager switchStateManager;
	private DryContactManager dryContactManager;
	private ControllerManager controllerManager;

	@Override
	public void onStart() {
		switchStateManager = new SwitchWithReturnStateManager();
		dryContactManager = new DryContactManager();
		controllerManager = new ControllerManager(switchStateManager, dryContactManager);

		DeviceManager.addRegistrationListener(switchStateManager, SwitchWithReturnState.class);
		DeviceManager.addRegistrationListener(dryContactManager, DryContact.class);
		DeviceManager.addRegistrationListener(controllerManager, Controller.class);
	}

	@Override
	public void onStop() {
		controllerManager.stop();
		DeviceManager.removeRegistrationListener(switchStateManager);
		DeviceManager.removeRegistrationListener(dryContactManager);
		DeviceManager.removeRegistrationListener(controllerManager);
	}
}
