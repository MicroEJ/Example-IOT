/*
 * Java
 *
 * Copyright 2016 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/
 */
package ej.examples.iot.zwave.app;

import ej.basedriver.Controller;
import ej.basedriver.DryContact;
import ej.basedriver.SwitchWithReturnState;
import ej.ecom.DeviceManager;
import ej.examples.iot.zwave.ControllerManager;
import ej.examples.iot.zwave.DryContactManager;
import ej.examples.iot.zwave.SwitchWithReturnStateManager;
import ej.wadapps.app.BackgroundService;

/**
 * Background Service starting the ZWave managment.
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
