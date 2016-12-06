/*
 * Java
 *
 * Copyright 2016 IS2T. All rights reserved.
 * IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package ej.examples.iot.zwave;

import ej.basedriver.DryContact;
import ej.basedriver.event.DryContactEvent;
import ej.basedriver.event.EventHandler;
import ej.ecom.RegistrationEvent;
import ej.ecom.RegistrationListener;

/**
 * Prints a message when a drycontact event occurs.
 */
public class DryContactManager
implements RegistrationListener<DryContact>, EventHandler<DryContact, DryContactEvent> {

	@Override
	public void deviceRegistered(RegistrationEvent<DryContact> event) {
		System.out.println("New DryContact device registered " + event.getDevice());

	}

	@Override
	public void deviceUnregistered(RegistrationEvent<DryContact> event) {
		System.out.println("DryContact device unregistered " + event.getDevice());

	}

	@Override
	public void handleEvent(DryContactEvent event) {
		System.out.println(event.getDevice() + " handleEvent " + event.getState());

	}

	@Override
	public void handleError(DryContactEvent error) {
		System.out.println(error.getDevice() + " handleError " + error.getState());

	}

}
