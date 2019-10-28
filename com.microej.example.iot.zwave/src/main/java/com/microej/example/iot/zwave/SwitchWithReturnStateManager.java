/*
 * Java
 *
 * Copyright 2016-2019 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 * MicroEJ Corp. PROPRIETARY. Use is subject to license terms.
 */
package com.microej.example.iot.zwave;

import com.microej.example.iot.zwave.app.ZWaveBG;

import ej.basedriver.SwitchWithReturnState;
import ej.basedriver.event.EventHandler;
import ej.basedriver.event.SwitchStateEvent;
import ej.ecom.RegistrationEvent;
import ej.ecom.RegistrationListener;

/**
 * Prints when a switch event occurs.
 */
public class SwitchWithReturnStateManager
		implements RegistrationListener<SwitchWithReturnState>, EventHandler<SwitchWithReturnState, SwitchStateEvent> {

	@Override
	public void deviceRegistered(RegistrationEvent<SwitchWithReturnState> event) {
		ZWaveBG.LOGGER.info("New SwitchWithReturnState device registered " + event.getDevice()); //$NON-NLS-1$
	}

	@Override
	public void deviceUnregistered(RegistrationEvent<SwitchWithReturnState> event) {
		ZWaveBG.LOGGER.info("SwitchWithReturnState device unregistered " + event.getDevice()); //$NON-NLS-1$
	}

	@Override
	public void handleEvent(SwitchStateEvent event) {
		ZWaveBG.LOGGER.info(event.getDevice() + " handleEvent " + event.getState()); //$NON-NLS-1$
	}

	@Override
	public void handleError(SwitchStateEvent error) {
		ZWaveBG.LOGGER.info(error.getDevice() + " handleError " + error.getState()); //$NON-NLS-1$
	}

}
