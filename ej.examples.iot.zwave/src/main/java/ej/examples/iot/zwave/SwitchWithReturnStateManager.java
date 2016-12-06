/*
 * Java
 *
 * Copyright 2016 IS2T. All rights reserved.
 * IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package ej.examples.iot.zwave;

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
		System.out.println("New SwitchWithReturnState device registered " + event.getDevice());

	}

	@Override
	public void deviceUnregistered(RegistrationEvent<SwitchWithReturnState> event) {
		System.out.println("SwitchWithReturnState device unregistered " + event.getDevice());

	}

	@Override
	public void handleEvent(SwitchStateEvent event) {
		System.out.println(event.getDevice() + " handleEvent " + event.getState());

	}

	@Override
	public void handleError(SwitchStateEvent error) {
		System.out.println(error.getDevice() + " handleError " + error.getState());

	}

}
