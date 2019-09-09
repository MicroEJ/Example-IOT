/*
 * Java
 *
 * Copyright 2016-2019 MicroEJ Corp. All rights reserved.
 * For demonstration purpose only.
 * MicroEJ Corp. PROPRIETARY. Use is subject to license terms.
 */
package com.microej.example.iot.zwave;

import com.microej.example.iot.zwave.app.ZWaveBG;

import ej.basedriver.DryContact;
import ej.basedriver.event.DryContactEvent;
import ej.basedriver.event.EventHandler;
import ej.ecom.RegistrationEvent;
import ej.ecom.RegistrationListener;

/**
 * Prints a message when a drycontact event occurs.
 */
public class DryContactManager implements RegistrationListener<DryContact>, EventHandler<DryContact, DryContactEvent> {

	@Override
	public void deviceRegistered(RegistrationEvent<DryContact> event) {
		ZWaveBG.LOGGER.info("New DryContact device registered " + event.getDevice()); //$NON-NLS-1$
	}

	@Override
	public void deviceUnregistered(RegistrationEvent<DryContact> event) {
		ZWaveBG.LOGGER.info("DryContact device unregistered " + event.getDevice()); //$NON-NLS-1$
	}

	@Override
	public void handleEvent(DryContactEvent event) {
		ZWaveBG.LOGGER.info(event.getDevice() + " handleEvent " + event.getState()); //$NON-NLS-1$
	}

	@Override
	public void handleError(DryContactEvent error) {
		ZWaveBG.LOGGER.info(error.getDevice() + " handleError " + error.getState()); //$NON-NLS-1$
	}

}
