/*
 * Java
 *
 * Copyright 2016 IS2T. All rights reserved.
 * Use of this source code is subject to license terms.
 */
package com.microej.example.iot.zwave.app;

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
